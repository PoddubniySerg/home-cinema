package com.home.cinema.data.di.modules

import com.home.cinema.data.repositories.page.HomePageRepositoryImpl
import com.home.cinema.data.repositories.MovieBeenViewedRepositoryImpl
import com.home.cinema.data.repositories.page.ListPageRepositoryImpl
import com.home.cinema.data.repositories.page.onboarding.OnBoardingSharedPrefsRepository
import com.home.cinema.domain.repositories.HomePageRepository
import com.home.cinema.domain.repositories.page.ListPageRepository
import com.home.cinema.domain.repositories.page.MovieBeenViewedRepository
import com.home.cinema.domain.repositories.page.onboarding.OnBoardingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideOnBoardingRepository(): OnBoardingRepository = OnBoardingSharedPrefsRepository()

    @Provides
    fun provideHomePageRepository(): HomePageRepository = HomePageRepositoryImpl()

    @Provides
    fun provideListPageRepository(): ListPageRepository = ListPageRepositoryImpl()

    @Provides
    fun provideMovieBeenViewedRepository(): MovieBeenViewedRepository =
        MovieBeenViewedRepositoryImpl()
}