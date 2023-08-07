package com.example.recipe_app.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration

import androidx.navigation.ui.setupWithNavController
import com.example.recipe_app.R

import com.example.recipe_app.view.auth.MainActivity

import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(){
    lateinit var navController: NavController
    lateinit var navHostFragment : NavHostFragment
    lateinit var  bottomNavigationView : BottomNavigationView
    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar = findViewById(R.id.toolbar)

        toolbar.setupWithNavController(navController, appBarConfiguration)
        toolbar.inflateMenu(R.menu.home_menu)
        toolbar.setOnMenuItemClickListener {
            if (it.getItemId() == R.id.action_signout) {
                var pref = getSharedPreferences("mypref", MODE_PRIVATE)
                var editor = pref.edit()
                editor.putBoolean("isloggedin", false)
                editor.apply()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                // todo delete all data from database
                true
            } else if (it.getItemId() == R.id.action_about) {
                navController.navigate(R.id.aboutFragment)
                true
            } else {
                false

            }
        }

        bottomNavigationView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.detailsFragment -> {
                    bottomNavigationView.visibility = BottomNavigationView.GONE
                }
                R.id.aboutFragment -> {
                    bottomNavigationView.visibility = BottomNavigationView.GONE
                }
                else -> {
                    bottomNavigationView.visibility = BottomNavigationView.VISIBLE
                }

            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

}
