package com.example.recipe_app.utils

import android.view.View
import androidx.core.content.ContextCompat
import com.example.recipe_app.R
import com.google.android.material.snackbar.Snackbar

object GreenSnackBar {
     fun showSnackBarLong(view: View, message: String) {
         val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
         snackbar.setBackgroundTint(ContextCompat.getColor(view.context,R.color.md_theme_light_primary))
         snackbar.setActionTextColor(ContextCompat.getColor(view.context,R.color.white))
         snackbar.show()
    }
     fun showSnackBarWithDismiss(view: View, message: String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
        snackbar.setBackgroundTint(ContextCompat.getColor(view.context,R.color.md_theme_light_primary))
        snackbar.setActionTextColor(ContextCompat.getColor(view.context,R.color.white))
        snackbar.setAction("DISMISS") {
            snackbar.dismiss()
        }
        snackbar.show()
    }
}