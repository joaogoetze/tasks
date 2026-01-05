package com.example.tasks.di

import android.content.Context
import com.example.tasks.AppDatabase
import com.example.tasks.constraints.Constraints
import androidx.room.Room
import com.example.tasks.db.MIGRATION_1_2
import com.example.tasks.db.MIGRATION_2_3
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
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        Constraints.TASK_DB
    )
        .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
        .build()

    @Singleton
    @Provides
    fun providerDao(database: AppDatabase) = database.taskDao()
}