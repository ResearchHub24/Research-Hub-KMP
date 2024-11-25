package com.atech.research.module

import com.atech.research.core.ktor.ResearchHubClient
import com.atech.research.core.ktor.ResearchHubClientImp
import com.atech.research.core.ktor.httpClientEngineFactory
import com.atech.research.core.usecase.AddTagUseCase
import com.atech.research.core.usecase.AllApplicationsUseCases
import com.atech.research.core.usecase.ApplicationUseCases
import com.atech.research.core.usecase.ApplyResearchUseCase
import com.atech.research.core.usecase.ChangeStatusUseCases
import com.atech.research.core.usecase.CreateNewForum
import com.atech.research.core.usecase.DeleteResearchUseCase
import com.atech.research.core.usecase.DeleteTagUseCase
import com.atech.research.core.usecase.FacultiesUseCases
import com.atech.research.core.usecase.ForumUseCases
import com.atech.research.core.usecase.GetALlApplicationsUseCase
import com.atech.research.core.usecase.GetAllForum
import com.atech.research.core.usecase.GetAllMessage
import com.atech.research.core.usecase.GetAllResearch
import com.atech.research.core.usecase.GetAllSkillsUseCase
import com.atech.research.core.usecase.GetAllTagsUseCase
import com.atech.research.core.usecase.GetPostedResearchUseCase
import com.atech.research.core.usecase.GetResearchById
import com.atech.research.core.usecase.GetUseDetailUseCase
import com.atech.research.core.usecase.IsAppliedToResearchUseCase
import com.atech.research.core.usecase.IsUserVerifiedUseCase
import com.atech.research.core.usecase.ResearchUseCase
import com.atech.research.core.usecase.SendMessage
import com.atech.research.core.usecase.SendNotificationUseCase
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
        ResearchUseCase(get(), get(), get(), get(), get())
    }

    single { GetAllTagsUseCase(get()) }
    single { AddTagUseCase(get()) }
    single { DeleteTagUseCase(get()) }
    single { TagUseCase(get(), get(), get()) }

    single { GetUseDetailUseCase(get()) }
    single { UpdateUserDetailUseCase(get()) }
    single { GetAllSkillsUseCase(get()) }
    single { IsUserVerifiedUseCase(get()) }
    single { UserUseCases(get(), get(), get(), get()) }

    single { GetAllResearch(get()) }
    single { ApplyResearchUseCase(get()) }
    single { IsAppliedToResearchUseCase(get()) }
    single { StudentResearchUseCases(get(), get(), get()) }
    single { FacultiesUseCases(get()) }
    single { AllApplicationsUseCases(get()) }
    single { GetALlApplicationsUseCase(get()) }
    single { ApplicationUseCases(get(), get()) }
    single { ChangeStatusUseCases(get()) }
    single { SendNotificationUseCase(get()) }
    single { GetResearchById(get()) }
    single { GetAllForum(get()) }
    single { CreateNewForum(get()) }
    single { GetAllMessage(get()) }
    single { SendMessage(get()) }
    single { ForumUseCases(get(), get(), get(), get()) }


}