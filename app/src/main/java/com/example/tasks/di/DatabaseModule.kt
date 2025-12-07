package com.example.tasks.di

import android.content.Context
import com.example.tasks.AppDatabase
import com.example.tasks.constraints.Constraints
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext constext: Context
    ) = Room.databaseBuilder(
        constext,
        AppDatabase::class.java,
        Constraints.TASK_DB
    ).build()

    @Singleton
    @Provides
    fun providerDao(database: AppDatabase) = database.taskDao()
}