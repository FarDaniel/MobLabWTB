package aut.moblab.wtb.ui.lists

import aut.moblab.wtb.local_data.models.MovieTag

interface MovieTagRefreshener {
    fun refreshTag(movieTag: MovieTag)
}