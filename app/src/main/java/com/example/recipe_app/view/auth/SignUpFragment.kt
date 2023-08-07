package com.example.recipe_app.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.recipe_app.R
import com.example.recipe_app.model.PersonInfo
import com.example.recipe_app.viewModels.AuthViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
@AndroidEntryPoint
class SignUpFragment : Fragment() {
    lateinit var et_name: TextInputEditText
    lateinit var et_email: TextInputEditText
    lateinit var et_password: TextInputEditText
    lateinit var btn_signUp: Button
    lateinit var et_layout_email: TextInputLayout
    lateinit var et_layout_name: TextInputLayout
    lateinit var et_layout_password: TextInputLayout
    val viewModel: AuthViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_sign_up, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_name = view.findViewById(R.id.et_name_signup)
        et_email = view.findViewById(R.id.et_email_signup)
        et_password = view.findViewById(R.id.et_password_signup)
        btn_signUp = view.findViewById(R.id.btn_create_account)
        et_layout_email = view.findViewById(R.id.et_layot_email_signup)
        et_layout_name = view.findViewById(R.id.et_layout_name_signup)
        et_layout_password = view.findViewById(R.id.et_layout_password_signup)

        btn_signUp.setOnClickListener {
            var name = et_name.text.toString()
            var email = et_email.text.toString()
            var password = et_password.text.toString()
            //check email is valid or not
            var e_email = when (email.isEmpty()) {
                true -> {
                    et_layout_email.error = "Email is required"
                    false
                }
                false -> {
                    et_layout_email.error = null
                    true
                }
            }
            var e_pass = when (password.isEmpty()) {
                true -> {
                    et_layout_password.error = "password is required"
                    false
                }
                false -> {

                    var checked = isValidPassword(password)
                    if (checked == null) {
                        et_layout_password.error = null
                        true
                    } else {
                        et_layout_password.error = checked
                        false

                    }

                }
            }

            var e_name = when (name.isEmpty()) {
                true -> {
                    et_layout_name.error = "name is required"
                    false
                }
                false -> {
                    et_layout_name.error = null
                    true
                }
            }
            var valid_email = isValidmail(email)


            if (e_email && e_pass && e_name && valid_email) {
                var user = PersonInfo(0, email, password)
                user.name = name
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.insertUser(user)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "user created", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.homeActivity)
                        var pref = requireActivity().getSharedPreferences("mypref", 0)
                        var editor = pref.edit()
                        editor.putBoolean("isloggedin", true)
                        editor.putString("CurrentUserMail","$email")
                        editor.apply()
                        activity?.finish()

                    }
                }
            } else {
                Toast.makeText(context, "user not created", Toast.LENGTH_LONG).show()
            }


        }


    }

    fun isValidmail(email: String): Boolean {
        val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")
        return when{
            !et_email.text.toString().trim().matches(emailPattern)->{
                et_layout_email.error="Email is not valid"
                false

            }
            else ->{
                et_layout_email.error = null
                true
            }
        }
    }

    fun isValidPassword(password: String): String? {
        if (password.length < 8) {
            return "Minimum 8 character password"
        }
        if (!password.matches(".*[A-Z].*".toRegex())) {

            return "Must Contain 1 Upper-Case Character"
        }
        if (!password.matches(".*[a-z].*".toRegex())) {

            return "Must Contain 1 Lower-Case Character"
        }
        if (!password.matches(".*[@#\$%^&+=].*".toRegex())) {

            return "Must Contain 1 Special Character (@#\$%^&+=)"
        }

          return null
    }

}