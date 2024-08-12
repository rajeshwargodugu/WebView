package com.gola.webview

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    companion object {
        private const val URL = "https://google.com/"
        private const val PROGRESS_HUNDRED = 100
    }

    private val _url = MutableLiveData<String>()
    val url: LiveData<String> get() = _url

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> get() = _progress

    private val _progressVisibility = MutableLiveData<Int>()
    val progressVisibility: LiveData<Int> get() = _progressVisibility

    init {
        _url.value = URL
        _progress.value = 0
        _progressVisibility.value = View.GONE
    }

    fun onPageStarted() {
        _progressVisibility.value = View.VISIBLE
        _progress.value = 0
    }

    fun onPageFinished() {
        _progress.value = PROGRESS_HUNDRED
        _progressVisibility.value = View.GONE
    }
}