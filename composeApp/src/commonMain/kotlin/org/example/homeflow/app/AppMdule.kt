package org.example.homeflow.app

import org.example.homeflow.core.data.di.dataModule
import org.example.homeflow.feature.viewModelModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import org.koin.dsl.module


fun initKoin(configuration: KoinAppDeclaration? = null) {
    startKoin {
        includes(configuration)
        modules(appModule)
    }

    /*val platformInfo = KoinPlatform.getKoin().get<NativeComponent>().getInfo()
    println("Running on: $platformInfo")*/
}

val appModule = module {
    includes(dataModule, viewModelModule,)
}