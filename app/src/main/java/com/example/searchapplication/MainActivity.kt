package com.example.searchapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.searchapplication.ui.theme.SearchApplicationTheme
import com.example.searchapplication.ui.theme.navigation.AppNavHostController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchApplicationTheme {
                AppNavHostController()
            }
        }
    }
}
