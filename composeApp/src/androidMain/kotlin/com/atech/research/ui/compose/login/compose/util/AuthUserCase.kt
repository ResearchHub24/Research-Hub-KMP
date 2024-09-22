package com.atech.research.ui.compose.login.compose.util

import android.util.Log
import com.atech.research.core.ktor.model.UserModel
import com.atech.research.utils.DataState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val DB = "ResearchHub"
private const val PATH = "v1"
private const val SUB_DB = "Users"


data class HasUserUseCase(
    private val db: FirebaseFirestore
) {
    suspend operator fun invoke(
        uid: String, state: (DataState<Boolean>) -> Unit = { _ -> }
    ) {
        try {
            val doc = db.collection(DB).document(PATH).collection(SUB_DB).document(uid).get().await()
            state(DataState.Success(doc.exists()))
        } catch (e: Exception) {
            state(DataState.Error(e))
        }
    }
}


data class LogInUseCase(
    private val db: FirebaseFirestore,
    private val hasUserUseCase: HasUserUseCase
) {
    suspend operator fun invoke(
        uid: String,
        model: UserModel,
        state: (DataState<String>) -> Unit = { _ -> }
    ) {
        try {
            hasUserUseCase.invoke(uid) { state1 ->
                when (state1) {
                    is DataState.Error -> {
                        state(state1)
                    }

                    is DataState.Success -> {
                        if (!state1.data) {
                            runBlocking {
                                db.collection(DB).document(PATH).collection(SUB_DB).document(uid)
                                    .set(model).await()
                                state(DataState.Success(uid))
                            }
                        } else {
                            state(DataState.Success(uid))
                        }
                    }

                    else -> {}
                }
            }
        } catch (e: Exception) {
            state(DataState.Error(e))
        }
    }
}

data class LogInWithGoogleStudent @Inject constructor(
    private val auth: FirebaseAuth,
    private val logInUseCase: LogInUseCase,
) {
    suspend operator fun invoke(
        uid: String,
        state: (DataState<UserModel>) -> Unit = { _ -> }
    ) {
        try {
            val credential = GoogleAuthProvider.getCredential(uid, null)
            val task = auth.signInWithCredential(credential).await()
            val user = task.user
            if (user == null) {
                state((DataState.Error(Exception("User not found"))))
            }
            user?.let { logInUser ->
                val userId = logInUser.uid
                val userName = logInUser.displayName
                val userEmail = logInUser.email
                val userPhoto = logInUser.photoUrl
                val model = UserModel(
                    uid = userId,
                    email = userEmail.toString(),
                    displayName = userName,
                    photoUrl = userPhoto.toString(),
                )
                logInUseCase.invoke(userId, model) { state1 ->
                    if (state1 is DataState.Error)
                        state(state1)
                    if (state1 is DataState.Success) {
                        state.invoke(DataState.Success(model))
                    }
                }
            }
        } catch (e: Exception) {
            state(DataState.Error(e))
        }
    }
}