package com.tzion.corenavigation

import android.content.Context
import android.content.Intent

object Actions {

    private const val PACKAGE_NAME = "com.tzion.android"

    fun openAbout(context: Context) =
        internalIntent(
            context,
            "com.tzion.android.about.open"
        )
    fun openAbout2(): Intent =
        intentTo("com.tzion.android.about.AboutActivity")
    fun openMovies(context: Context): Intent =
        internalIntent(
            context,
            "com.tzion.android.movies.list.open"
        )

    private fun intentTo(addressableActivity: String): Intent = Intent().setClassName(
        PACKAGE_NAME, addressableActivity)

    private fun internalIntent(context: Context, action: String) = Intent(action).setPackage(context.packageName)

}