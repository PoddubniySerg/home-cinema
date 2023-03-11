package com.home.cinema.data.di.modules

import com.home.cinema.data.repositories.HomePageRepositoryImpl
import com.home.cinema.data.repositories.MovieBeenViewedRepositoryImpl
import com.home.cinema.data.repositories.OnBoardingRepositoryImpl
import com.home.cinema.data.repositories.ListPageRepositoryImpl
import com.home.cinema.domain.repositories.HomePageRepository
import com.home.cinema.domain.repositories.ListPageRepository
import com.home.cinema.domain.repositories.MovieBeenViewedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideOnBoardingRepository(): com.home.cinema.domain.repositories.OnBoardingRepository =
        OnBoardingRepositoryImpl()

    @Provides
    fun provideHomePageRepository(): HomePageRepository = HomePageRepositoryImpl()

    @Provides
    fun provideListPageRepository(): ListPageRepository = ListPageRepositoryImpl()

    @Provides
    fun provideMovieBeenViewedRepository(): MovieBeenViewedRepository =
        MovieBeenViewedRepositoryImpl()
}