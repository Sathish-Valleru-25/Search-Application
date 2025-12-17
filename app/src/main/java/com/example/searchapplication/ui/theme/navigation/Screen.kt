package com.example.searchapplication.ui.theme.navigation

object  Screen {
    const val SEARCH = "search"
    const val DETAIL = "detail/{id}"

    fun detailRoute(id: Int): String = "detail/$id"

}