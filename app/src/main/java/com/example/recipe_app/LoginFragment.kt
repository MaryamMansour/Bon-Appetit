package com.example.recipe_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class LoginFragment : Fragment() {

    lateinit var btntosignup: TextView
    lateinit var edittextemail : TextInputEditText
    lateinit var edittextpassword : TextInputEditText
    lateinit var btnlogin : Button




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edittextemail= view.findViewById(R.id.editText_email)
        edittextpassword= view.findViewById(R.id.editText_Password)

        btnlogin= view.findViewById(R.id.button_login)
        btnlogin.setOnClickListener {

            val email = edittextemail.text.toString()
            val password = edittextpassword.text.toString()
            val database = PersonInfoDatabase.getintstance(requireActivity())
            val dao= database.personinfodao()
            lifecycleScope.launch(Dispatchers.IO) {

                val userdatabase = dao.getAllPersonInfo()
                val currentuser = PersonInfo(0 , email, password )
                if(currentuser in userdatabase){
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "login", Toast.LENGTH_LONG).show()
                    }
                }
                else{
                    //todo 2 possibilities of enetring email right but wrong passwprd

                  withContext(Dispatchers.Main)  {
                      Toast.makeText(context , "user not found , please sign up " , Toast.LENGTH_LONG).show()
                  }


                }


            }

        }



        btntosignup = view.findViewById(R.id.textview_createAccount)
        btntosignup.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            view.findNavController().navigate(action)

        }










    }



}