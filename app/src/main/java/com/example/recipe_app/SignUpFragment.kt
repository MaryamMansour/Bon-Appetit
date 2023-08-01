package com.example.recipe_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withCreated
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpFragment : Fragment() {
    lateinit var  et_name: TextInputEditText
    lateinit var  et_email: TextInputEditText
    lateinit var  et_password: TextInputEditText
    lateinit var btn_signUp: Button
    lateinit var et_layout_email : TextInputLayout
    lateinit var et_layout_name : TextInputLayout
    lateinit var et_layout_password : TextInputLayout


    lateinit var db : PersonInfoDatabase
    lateinit var dao : PersonInfoDao
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_name = view.findViewById(R.id.et_name)
        et_email = view.findViewById(R.id.et_email)
        et_password = view.findViewById(R.id.et_Password)
        btn_signUp = view.findViewById(R.id.btn_create_account)
        et_layout_email = view.findViewById(R.id.editTextLayoutEmail)
        et_layout_name = view.findViewById(R.id.editTextLayoutName)
        et_layout_password = view.findViewById(R.id.editTextLayoutPassword)
        db = PersonInfoDatabase.getintstance(requireActivity())
        dao= db.personinfodao()

        btn_signUp.setOnClickListener {
            var name = et_name.text.toString()
            var email = et_email.text.toString()
            var password = et_password.text.toString()
            //check email is valid or not
            var e_email=when(email.isEmpty()){
                true -> {
                    et_layout_email.error = "Email is required"
                    true
                }
                false -> {
                    et_layout_email.error = null
                    false
                }
            }
            var e_pass=when(password.isEmpty()){
                true -> {
                    et_layout_password.error = "password is required"
                    true
                }
                false -> {
                    et_layout_password.error = null
                    false
                }
            }
            var e_name=when(name.isEmpty()){
                true -> {
                    et_layout_name.error = "name is required"
                    true
                }
                false -> {
                    et_layout_name.error = null
                    false
                }
            }
            var valid_email = when(email.contains("@") && email.contains(".")){
                true -> {
                    et_layout_email.error = null
                    false
                }
                false -> {
                    et_layout_email.error = "Email is not valid"
                    true
                }
            }
            if(!(e_email || e_pass || e_name||valid_email)){
                var user = PersonInfo(0, email, password)
                user.name = name
                lifecycleScope.launch(Dispatchers.IO) {
                    dao.insert(user)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "user created", Toast.LENGTH_LONG).show()
                    }
                }
            }
            else{
                Toast.makeText(context, "user not created", Toast.LENGTH_LONG).show()
            }


        }


    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}