package com.example.recipe_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
    lateinit var db: PersonInfoDatabase
    lateinit var dao: PersonInfoDao


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_email_login = view.findViewById(R.id.editText_email)
        et_password_login = view.findViewById(R.id.et_password)
        btnlogin = view.findViewById(R.id.btn_create_account)
        ettxtlayout_email = view.findViewById(R.id.editTextLayout_email)
        ettxtlayout_password = view.findViewById(R.id.et_layout_password)

        db = PersonInfoDatabase.getintstance(requireActivity())
        dao = db.personinfodao()

        btnlogin.setOnClickListener {

            var email = et_email_login.text.toString()
            var password = et_password_login.text.toString()

            var e_email=when(email.isEmpty()){
                true -> {
                    ettxtlayout_email.error = "Email is required"
                    true
                }
                false -> {
                    ettxtlayout_email.error = null
                    false
                }
            }
            var e_pass=when(password.isEmpty()){
                true -> {
                    ettxtlayout_password.error = "password is required"
                    true
                }
                false -> {
                    ettxtlayout_password.error = null
                    false
                }
            }
            var valid_email = when((email.contains("@") && email.contains(".")) ){
                true -> {
                    ettxtlayout_email.error = null
                    false
                }
                false -> {
                    if(!e_email){
                        ettxtlayout_email.error = "Email is not valid"
                        true
                    }
                    false
                }
            }
            if(!(e_email || e_pass || valid_email)){
                var currentuser= PersonInfo(0, email, password)
                lifecycleScope.launch(Dispatchers.IO) {
                   var  result= dao.getPersonInfo(email)
                    withContext(Dispatchers.Main){
                        if(result!=null){
                            if(result?.password.equals(currentuser.password)){
                                Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()
                                // todo val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
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

        }
        btntosignup = view.findViewById(R.id.textview_createAccount)
        btntosignup.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            findNavController().navigate(action)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}

