package com.example.searchapplication

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
    class MainCoroutineRule(
    ) : TestWatcher() {
        val  dispatcher = StandardTestDispatcher()

        override fun starting(description: Description) {
            // Sets the main dispatcher to our test dispatcher
            Dispatchers.setMain(dispatcher)
        }

        override fun finished(description: Description) {
            // Resets the main dispatcher to the original one after the test is done
            Dispatchers.resetMain()
        }
    }
