package com.exam.gweather.dependency.modules

import com.exam.gweather.data.repository.auth.AuthRepository
import com.exam.gweather.data.repository.auth.AuthRepositoryImpl
import com.exam.gweather.data.repository.weather.WeatherRepository
import com.exam.gweather.data.repository.weather.WeatherRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository {
        return authRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository {
        return weatherRepositoryImpl
    }

}

