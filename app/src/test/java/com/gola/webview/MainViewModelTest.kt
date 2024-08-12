package com.gola.webview

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel()
    }

    @Test
    fun testInitialValues() {
        assertEquals("https://google.com/", viewModel.url.value)
        assertEquals(0, viewModel.progress.value)
        assertEquals(View.GONE, viewModel.progressVisibility.value)
    }

    @Test
    fun testOnPageStarted() {
        viewModel.onPageStarted()
        assertEquals(0, viewModel.progress.value)
        assertEquals(View.VISIBLE, viewModel.progressVisibility.value)
    }

    @Test
    fun testOnPageFinished() {
        viewModel.onPageFinished()
        assertEquals(100, viewModel.progress.value)
        assertEquals(View.GONE, viewModel.progressVisibility.value)
    }
}