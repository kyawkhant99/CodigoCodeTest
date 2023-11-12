package com.kkh.codigocodetest.ui.util

sealed class ScreenRoute(val route:String){
    object HomeScreen: ScreenRoute("Home")
    object DetailScreen: ScreenRoute("detail")

}
