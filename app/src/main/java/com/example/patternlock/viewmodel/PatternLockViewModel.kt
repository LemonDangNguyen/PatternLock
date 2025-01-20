package com.example.patternlock.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.patternlock.repository.PatternRepository

class PatternLockViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PatternRepository.getInstance(application)

    val savedPattern: LiveData<List<Int>?> = repository.savedPattern

    private val _unlockStatus = MutableLiveData<Boolean>()
    val unlockStatus: LiveData<Boolean> get() = _unlockStatus

    fun savePattern(pattern: List<Int>) {
        repository.savePattern(pattern)
    }

    fun validatePattern(inputPattern: List<Int>) {
        val saved = repository.getPattern()
        _unlockStatus.value = saved?.size == inputPattern.size && saved.zip(inputPattern).all { it.first == it.second }
    }

    fun clearPattern() {
        repository.clearPattern()
    }
}
