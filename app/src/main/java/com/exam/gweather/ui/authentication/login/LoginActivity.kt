package com.exam.gweather.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat

import com.exam.gweather.databinding.ActivityLoginBinding
import com.exam.gweather.ui.authentication.AuthViewModel
import com.exam.gweather.ui.authentication.registration.RegistrationActivity
import com.exam.gweather.ui.MainActivity
import com.exam.gweather.utils.isValidEmail
import com.exam.gweather.utils.startActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        initButton()
    }


    private fun initObserver() {

        authViewModel.isUserLoggedIn.observe(this) {
            if (it) {
                val image = androidx.core.util.Pair<View, String>(binding.image, "image");
                this.startActivity<MainActivity>(sharedElemets = arrayOf(image))
            }
        }

        authViewModel.authResult.observe(this) {
            if (it.isSuccess) {

                val image = androidx.core.util.Pair<View, String>(binding.image, "image");

                this.startActivity<MainActivity>(sharedElemets = arrayOf(image))

            } else {
                Toast.makeText(
                    applicationContext,
                    it.exceptionOrNull()?.message,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun initButton() {

        binding.txtRegister.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)

            val image = androidx.core.util.Pair<View, String>(binding.image, "image")

            val options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, image)
            startActivity(intent, options.toBundle())
        }

        binding.btnLogin.setOnClickListener {

            if (binding.txtUsername.text.isNullOrEmpty()) {
                binding.txtInputLayoutUsername.error = "Please input username."
            } else if (!binding.txtUsername.text.toString().isValidEmail()) {
                binding.txtInputLayoutUsername.error = "Please input valid username."
            } else {
                binding.txtInputLayoutUsername.error = ""
            }

            if (binding.txtPassword.text.isNullOrEmpty()) {
                binding.txtInputLayoutPassword.error = "Please input password."
            } else {
                binding.txtInputLayoutPassword.error = ""
            }

            if (!binding.txtUsername.text.isNullOrEmpty() && !binding.txtPassword.text.isNullOrEmpty()) {
                authViewModel.signIn(
                    email = binding.txtUsername.text.toString(),
                    password = binding.txtPassword.text.toString()
                )
            }
        }
    }

//    private fun navigate(intent: Intent) {
//        val image = androidx.core.util.Pair<View, String>(binding.image, "image")
//        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, image)
//        startActivity(intent, options.toBundle())
//    }
}