package com.tzion.navigation

import android.content.Context
import android.content.Intent
import com.tzion.navigation.PackagePath.ABOUT_PATH
import com.tzion.navigation.PackagePath.MOVIES_PATH

object Actions {

    fun getOpenMoviesIntent(context: Context): Intent =
        intentTo(context, MOVIES_PATH)

    fun getAboutIntent(context: Context): Intent =
        intentTo(context, ABOUT_PATH)

    private fun intentTo(context: Context, addressableActivity: String): Intent = Intent().setClassName(
        context.packageName, addressableActivity
    )

    private fun internalIntent(context: Context, action: String) =
        Intent(action).setPackage(context.packageName)

}