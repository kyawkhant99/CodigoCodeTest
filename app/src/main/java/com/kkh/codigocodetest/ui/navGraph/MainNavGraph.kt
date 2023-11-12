package com.kkh.codigocodetest.ui.navGraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kkh.codigocodetest.ui.screens.DetailScreen
import com.kkh.codigocodetest.ui.screens.HomeScreen
import com.kkh.codigocodetest.ui.util.ScreenRoute
import com.kkh.domain.model.MovieItem

@Composable
fun MainNavGraph() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenRoute.HomeScreen.route) {
        composable(ScreenRoute.HomeScreen.route) {
            HomeScreen(onItemClick = { movieItem ->
                navController.currentBackStackEntry?.savedStateHandle?.set("movie", movieItem)
                navController.navigate(ScreenRoute.DetailScreen.route)
            })
        }
        composable(ScreenRoute.DetailScreen.route) {
            val movieItem =
                navController.previousBackStackEntry?.savedStateHandle?.get<MovieItem>("movie")
            movieItem?.let {
                DetailScreen(movieItem = it) {
                    navController.popBackStack()
                }
            }
        }
    }
}