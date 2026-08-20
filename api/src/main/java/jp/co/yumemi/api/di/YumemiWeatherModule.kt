package jp.co.yumemi.api.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.api.YumemiWeather
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object YumemiWeatherModule {

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideYumemiWeather(context: Context): YumemiWeather = YumemiWeather(context)
}
