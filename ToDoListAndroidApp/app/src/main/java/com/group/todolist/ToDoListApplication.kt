package com.group.todolist

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ToDoListApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("ToDoListApplication","onCreate")
        loadSetting()
    }

    fun loadSetting(){
        val sp = PreferenceManager.getDefaultSharedPreferences(this)

        val theme:Boolean = sp.getBoolean("theme",false)
        Log.d("theme", "$theme")
        if (theme){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}