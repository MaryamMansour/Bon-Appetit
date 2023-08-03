package com.example.recipe_app.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.recipe_app.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //if user is logged in then navigate to home activity else navigate to login fragment
        var pref=requireActivity().getSharedPreferences("mypref",0)
        var isloggedin=pref.getBoolean("isloggedin",false)
        lifecycleScope.launch (Dispatchers.Main){
            delay(3000)
            if(isloggedin){
                    findNavController().navigate(R.id.homeActivity)
                    activity?.finish()

            }
            else{
                    findNavController().navigate(R.id.loginFragment)
            }
        }
        findNavController().popBackStack()


    }
}