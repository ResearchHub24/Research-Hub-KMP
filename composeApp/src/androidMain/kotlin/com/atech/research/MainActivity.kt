package com.atech.research

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.atech.research.utils.PrefManager
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    private lateinit var navControllerM: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val pref = koinInject<PrefManager>()
            val navController = rememberNavController()
            navControllerM = navController
            App(
                pref = pref,
                navHostController = navController
            )
            intent?.extras?.let {
                Log.d("AAA", "${it.getBoolean("from_notification", false)}")
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("AAA", "${intent.extras?.getBoolean("from_notification", false)}")
    }
}
