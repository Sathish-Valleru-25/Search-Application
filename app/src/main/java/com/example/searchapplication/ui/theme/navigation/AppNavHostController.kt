package com.example.searchapplication.ui.theme.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.searchapplication.ui.theme.detailscreen.CharacterDetailScreen
import com.example.searchapplication.ui.theme.searchscreen.SearchScreen


@Composable
fun AppNavHostController() {
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.SEARCH
        ) {

            composable(Screen.SEARCH) {
                SearchScreen(
                    innerPadding = innerPadding,
                    navController = navController
                )
            }


            composable(
                route = Screen.DETAIL,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) {
                CharacterDetailScreen()
            }
        }
    }

}


