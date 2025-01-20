package com.example.patternlock.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PatternRepository private constructor(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("pattern_prefs", Context.MODE_PRIVATE)

    private val _savedPatternLiveData = MutableLiveData<List<Int>?>()
    val savedPattern: LiveData<List<Int>?> get() = _savedPatternLiveData

    companion object {
        @Volatile
        private var INSTANCE: PatternRepository? = null

        fun getInstance(context: Context): PatternRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PatternRepository(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    init {
        _savedPatternLiveData.value = getPattern()
    }

    fun savePattern(pattern: List<Int>) {
        val patternString = pattern.joinToString(",")
        sharedPreferences.edit().putString("saved_pattern", patternString).apply()
        _savedPatternLiveData.value = pattern
    }

    fun getPattern(): List<Int>? {
        val patternString = sharedPreferences.getString("saved_pattern", null) ?: return null
        return patternString.split(",").mapNotNull { it.toIntOrNull() }
    }

    fun clearPattern() {
        sharedPreferences.edit().remove("saved_pattern").apply()
        _savedPatternLiveData.value = null
    }
}
