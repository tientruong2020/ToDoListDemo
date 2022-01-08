package com.group.todolist.di

import android.app.Application
import android.content.Context
import com.group.todolist.data.database.DAO
import com.group.todolist.data.database.LocalDatabase
import com.group.todolist.data.database.RoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @InternalCoroutinesApi
    @Singleton
    @Provides
    fun provideDatabase(context: Application):LocalDatabase{
        return LocalDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideDao(localDatabase: LocalDatabase):DAO{
        return localDatabase.dao()
    }

    @Singleton
    @Provides
    fun provideRepository(dao: DAO):RoomRepository{
        return RoomRepository(dao)
    }
}