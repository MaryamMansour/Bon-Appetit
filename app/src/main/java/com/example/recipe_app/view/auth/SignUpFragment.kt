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
import com.example.recipe_app.utils.GreenSnackBar
import com.example.recipe_app.viewModels.AuthViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var etName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnSignup: Button
    private lateinit var etLayoutEmail: TextInputLayout
    private lateinit var etLayoutName: TextInputLayout
    private lateinit var etLayoutPassword: TextInputLayout
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etName = view.findViewById(R.id.et_name_signup)
        etEmail = view.findViewById(R.id.et_email_signup)
        etPassword = view.findViewById(R.id.et_password_signup)
        btnSignup = view.findViewById(R.id.btn_create_account)
        etLayoutEmail = view.findViewById(R.id.et_layot_email_signup)
        etLayoutName = view.findViewById(R.id.et_layout_name_signup)
        etLayoutPassword = view.findViewById(R.id.et_layout_password_signup)

        btnSignup.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            //check email is valid or not
            val eEmail = when (email.isEmpty()) {
                true -> {
                    etLayoutEmail.error = "Email is required"
                    false
                }

                false -> {
                    etLayoutEmail.error = null
                    true
                }
            }
            val ePass = when (password.isEmpty()) {
                true -> {
                    etLayoutPassword.error = "password is required"
                    false
                }

                false -> {

                    val checked = isValidPassword(password)
                    if (checked == null) {
                        etLayoutPassword.error = null
                        true
                    } else {
                        etLayoutPassword.error = checked
                        false

                    }

                }
            }

            val eName = when (name.isEmpty()) {
                true -> {
                    etLayoutName.error = "name is required"
                    false
                }

                false -> {
                    etLayoutName.error = null
                    true
                }
            }
            val validEmail = isValidmail()


            if (eEmail && ePass && eName && validEmail) {
                //check email is already exist or not
                viewModel.getUserByEmail(email)
                viewModel.user.observe(viewLifecycleOwner) {
                    if (it != null) {
                        etLayoutEmail.error = "Email is already exist"
                    } else {
                        etLayoutEmail.error = null
                        val user = PersonInfo(0, email, password)
                        user.name = name
                        viewModel.insertUser(user)
                        Toast.makeText(context, "user created", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.homeActivity)
                        val pref = requireActivity().getSharedPreferences("mypref", 0)
                        val editor = pref.edit()
                        editor.putBoolean("isloggedin", true)
                        editor.putString("CurrentUserMail", email)
                        editor.apply()
                        activity?.finish()
                    }
                }
            } else {
                GreenSnackBar.showSnackBarLong(view,"user not created")
            }
        }
    }

    private fun isValidmail(): Boolean {
        val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")
        return when {
            !etEmail.text.toString().trim().matches(emailPattern) -> {
                etLayoutEmail.error = "Email is not valid"
                false

            }

            else -> {
                etLayoutEmail.error = null
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