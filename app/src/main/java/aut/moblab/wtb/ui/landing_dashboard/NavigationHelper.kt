package aut.moblab.wtb.ui.landing_dashboard

import androidx.navigation.NavController

interface NavigationHelper {
    var navController: NavController

    fun watchDetails(movieId: String)
    fun addToWatched(movieId: String, title: String)

}