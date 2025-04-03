package com.example.androidstudy.lazyLists

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CardItemModule {

    @Provides
    @Singleton
    fun provideCardItemDataStore(
        @ApplicationContext context: Context
    ): CardItemDataStore {
        return CardItemDataStore(context)
    }
}
