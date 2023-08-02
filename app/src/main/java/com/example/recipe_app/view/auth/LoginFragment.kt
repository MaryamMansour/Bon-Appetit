package com.example.recipe_app.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.recipe_app.R
import com.example.recipe_app.local.LocalSourceImp
import com.example.recipe_app.model.PersonInfo
import com.example.recipe_app.local.dao.PersonInfoDao
import com.example.recipe_app.local.db.MealDataBase
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.repository.Repository
import com.example.recipe_app.repository.RepositoryImpl
import com.example.recipe_app.viewModels.AuthViewModel
import com.example.recipe_app.viewModels.AuthViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    lateinit var btntosignup: TextView
    lateinit var et_email_login: TextInputEditText
    lateinit var et_password_login: TextInputEditText
    lateinit var ettxtlayout_email:TextInputLayout
    lateinit var ettxtlayout_password:TextInputLayout
    lateinit var btnlogin: Button
    lateinit var viewModel: AuthViewModel
    lateinit var authViewModelFactory: AuthViewModelFactory



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModelFactory= AuthViewModelFactory(RepositoryImpl(LocalSourceImp(requireActivity()),ApiClient))
        viewModel= ViewModelProvider(this,authViewModelFactory).get(AuthViewModel::class.java)

        et_email_login = view.findViewById(R.id.editText_email)
        et_password_login = view.findViewById(R.id.et_password)
        btnlogin = view.findViewById(R.id.btn_create_account)
        ettxtlayout_email = view.findViewById(R.id.editTextLayout_email)
        ettxtlayout_password = view.findViewById(R.id.et_layout_password)



        btnlogin.setOnClickListener {

            var email = et_email_login.text.toString()
            var password = et_password_login.text.toString()

            var e_email=when(email.isEmpty()){
                true -> {
                    ettxtlayout_email.error = "Email is required"
                    false
                }
                false -> {
                    ettxtlayout_email.error = null
                    true
                }
            }
            var e_pass=when(password.isEmpty()){
                true -> {
                    ettxtlayout_password.error = "password is required"
                    false
                }
                false -> {
                    ettxtlayout_password.error = null
                    true
                }
            }
            var valid_email = when((email.contains("@") && email.contains(".")) ){
                true -> {
                    ettxtlayout_email.error = null
                    true
                }
                false -> {
                    if(e_email){
                        ettxtlayout_email.error = "Email is not valid"
                        false
                    }
                    else{
                        true
                    }
                }
            }
            if(e_email && e_pass && valid_email){
                var currentuser= PersonInfo(0, email, password)
                viewModel.getUserByEmail(email)
                viewModel.user.observe(viewLifecycleOwner){result->
                    if(result!=null){
                        if(result?.password.equals(currentuser.password)){
                            Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()
                            var pref=requireActivity().getSharedPreferences("mypref",0)
                            var editor=pref.edit()
                            editor.putBoolean("isloggedin",true)
                            editor.apply()
                            findNavController().navigate(R.id.homeActivity)
                            activity?.finish()


                        }
                        else{
                            Toast.makeText(context, "Incorrect Password", Toast.LENGTH_LONG).show()
                        }
                    }
                    else{
                        Toast.makeText(context, "User not found please create an account", Toast.LENGTH_LONG).show()

                    }
                }

            }

        }
        btntosignup = view.findViewById(R.id.textview_createAccount)
        btntosignup.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            findNavController().navigate(action)
        }
    }

}

