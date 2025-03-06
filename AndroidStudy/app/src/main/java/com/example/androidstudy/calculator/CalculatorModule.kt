package com.example.androidstudy.calculator

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CalculatorModule {

    @Provides
    @Singleton
    fun provideCalculationHistoryDataStore(@ApplicationContext context: Context): CalculationHistoryDataStore {
        return CalculationHistoryDataStore(context)
    }
}
