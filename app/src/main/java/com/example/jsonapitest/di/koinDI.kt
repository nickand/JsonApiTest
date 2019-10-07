package com.example.jsonapitest.di

import android.app.Application
import com.example.data.JsonPlaceHolderRepository
import com.example.data.LocalDataSource
import com.example.data.RemoteDataSource
import com.example.jsonapitest.data.database.PostDatabase
import com.example.jsonapitest.data.database.RoomDataSource
import com.example.jsonapitest.data.server.PostDataSource
import com.example.jsonapitest.ui.favorite.FavoriteFragment
import com.example.jsonapitest.ui.favorite.FavoriteViewModel
import com.example.jsonapitest.ui.main.PostFragment
import com.example.jsonapitest.ui.main.PostViewModel
import com.example.jsonapitest.ui.detail.DetailFragment
import com.example.jsonapitest.ui.detail.DetailViewModel
import com.example.usecase.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    single { PostDatabase.build(get()) }
    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDataSource> { PostDataSource() }
    single<CoroutineDispatcher> { Dispatchers.Main }
}

val dataModule = module {
    factory { JsonPlaceHolderRepository(get(), get()) }
}

private val scopesModule = module {
    scope(named<PostFragment>()) {
        viewModel { PostViewModel(get(), get(), get(), get(), get(), get()) }
        scoped { GetPosts(get()) }
        scoped { GetUser(get()) }
        scoped { DeletePostById(get()) }
        scoped { DeleteAllPosts(get()) }
        scoped { CheckReadStatus(get()) }
    }

    scope(named<DetailFragment>()) {
        viewModel { (postId: Int) -> DetailViewModel(postId, get(), get(), get(), get()) }
        scoped { FindPostById(get()) }
        scoped { ToggleAnimeFavorite(get()) }
        scoped { CheckReadStatus(get()) }
    }

    scope(named<FavoriteFragment>()) {
        viewModel { FavoriteViewModel(get(), get()) }
        scoped { GetPosts(get()) }
    }
}