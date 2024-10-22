package com.atech.research.module

import com.atech.research.core.ktor.ResearchHubClient
import com.atech.research.core.ktor.ResearchHubClientImp
import com.atech.research.core.ktor.httpClientEngineFactory
import com.atech.research.core.usecase.AddTagUseCase
import com.atech.research.core.usecase.ApplyResearchUseCase
import com.atech.research.core.usecase.DeleteResearchUseCase
import com.atech.research.core.usecase.DeleteTagUseCase
import com.atech.research.core.usecase.FacultiesUseCases
import com.atech.research.core.usecase.GetAllResearch
import com.atech.research.core.usecase.GetAllSkillsUseCase
import com.atech.research.core.usecase.GetAllTagsUseCase
import com.atech.research.core.usecase.GetPostedResearchUseCase
import com.atech.research.core.usecase.GetUseDetailUseCase
import com.atech.research.core.usecase.IsAppliedToResearchUseCase
import com.atech.research.core.usecase.ResearchUseCase
import com.atech.research.core.usecase.StudentResearchUseCases
import com.atech.research.core.usecase.TagUseCase
import com.atech.research.core.usecase.UpdateOrPostResearchUseCase
import com.atech.research.core.usecase.UpdateUserDetailUseCase
import com.atech.research.core.usecase.UserUseCases
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
        DeleteResearchUseCase(get())
    }
    single {
        ResearchUseCase(get(), get(), get())
    }

    single { GetAllTagsUseCase(get()) }
    single { AddTagUseCase(get()) }
    single { DeleteTagUseCase(get()) }
    single { TagUseCase(get(), get(), get()) }

    single { GetUseDetailUseCase(get()) }
    single { UpdateUserDetailUseCase(get()) }
    single { GetAllSkillsUseCase(get()) }
    single { UserUseCases(get(), get(), get()) }

    single { GetAllResearch(get()) }
    single { ApplyResearchUseCase(get()) }
    single { IsAppliedToResearchUseCase(get()) }
    single { StudentResearchUseCases(get(), get(), get()) }
    single { FacultiesUseCases(get()) }

}