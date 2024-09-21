package com.atech.research.module

import com.atech.research.core.ktor.ResearchHubClient
import com.atech.research.core.ktor.ResearchHubClientImp
import com.atech.research.core.ktor.httpClientEngineFactory
import com.atech.research.core.usecase.GetPostedResearchUseCase
import com.atech.research.core.usecase.ResearchUseCase
import com.atech.research.core.usecase.UpdateOrPostResearchUseCase
import com.atech.research.utils.PreferenceUtils
import com.atech.research.utils.Prefs
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModule = module {
    single {
        ResearchHubClientImp(httpClientEngineFactory().createEngine())
    }.bind(ResearchHubClient::class)
    single {
        GetPostedResearchUseCase(get())
    }
    single {
        UpdateOrPostResearchUseCase(get())
    }
    single {
        ResearchUseCase(get(), get())
    }
    single<String> {
        PreferenceUtils
            .builder(get())
            .build()
            .getStringPref(Prefs.USER_ID.name)
    }

}