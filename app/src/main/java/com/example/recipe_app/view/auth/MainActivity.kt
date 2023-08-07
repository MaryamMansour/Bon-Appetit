package com.example.recipe_app.view.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import com.example.recipe_app.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        if (findNavController(this,R.layout.activity_main).currentDestination?.id == R.id.loginFragment) {
//            finish()
//        }
//    }


}