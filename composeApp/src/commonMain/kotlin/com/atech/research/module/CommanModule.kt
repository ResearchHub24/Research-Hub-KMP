package com.atech.research.module

import com.atech.research.core.ktor.ResearchHubClient
import com.atech.research.core.ktor.ResearchHubClientImp
import com.atech.research.core.ktor.httpClientEngineFactory
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModule = module {
    single {
        ResearchHubClientImp(httpClientEngineFactory().createEngine() )
    }.bind(ResearchHubClient::class)
}