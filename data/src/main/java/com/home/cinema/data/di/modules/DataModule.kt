package com.home.cinema.data.di.modules

import com.home.cinema.data.repositories.CountriesAndGenresRepositoryImpl
import com.home.cinema.data.repositories.MovieBeenViewedRepositoryImpl
import com.home.cinema.data.repositories.page.home.*
import com.home.cinema.data.repositories.page.onboarding.OnBoardingSharedPrefsRepository
import com.home.cinema.domain.repositories.page.CountriesAndGenresRepository
import com.home.cinema.domain.repositories.page.MovieBeenViewedRepository
import com.home.cinema.domain.repositories.page.home.*
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
    fun providePremiersRepository(): PremiersRepository = HomePremiersRepository()

    @Provides
    fun providePopularRepository(): PopularRepository = HomePopularRepository()

    @Provides
    fun provideHomeTop250Repository(): HomeTop250Repository = HomeTop250RepositoryImpl()

    @Provides
    fun provideHomeTVSeriesRepository(): HomeTVSeriesRepository = HomeTVSeriesRepositoryImpl()

    @Provides
    fun provideHomeRandomCollectionRepository(): RandomCollectionRepository =
        RandomCollectionRepositoryImpl()

    @Provides
    fun provideCountriesAndGenresRepository(): CountriesAndGenresRepository =
        CountriesAndGenresRepositoryImpl()

    @Provides
    fun provideMovieBeenViewedRepository(): MovieBeenViewedRepository =
        MovieBeenViewedRepositoryImpl()
}