package com.exam.gweather.ui.authentication.registration

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.exam.gweather.databinding.ActivityRegistrationBinding
import com.exam.gweather.ui.authentication.AuthViewModel
import com.exam.gweather.ui.authentication.login.LoginActivity
import com.exam.gweather.ui.MainActivity
import com.exam.gweather.utils.isValidEmail
import com.exam.gweather.utils.startActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationActivity : AppCompatActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        initButton()
    }

    private fun initObserver() {
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

        binding.txtLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)

            val image = androidx.core.util.Pair<View, String>(binding.image, "image")

            val options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, image)
            startActivity(intent, options.toBundle())
        }


        binding.btnRegister.setOnClickListener {

            if (binding.txtUsername.text.isNullOrEmpty()) {
                binding.txtInputLayoutUsername.error = "Please input username."
                return@setOnClickListener
            } else if (!binding.txtUsername.text.toString().isValidEmail()) {
                binding.txtInputLayoutUsername.error = "Please input valid email address."
                return@setOnClickListener
            } else {
                binding.txtInputLayoutUsername.error = ""
            }

            if (binding.txtPassword.text.isNullOrEmpty()) {
                binding.txtInputLayoutPassword.error = "Please input password."
                return@setOnClickListener
            } else {
                binding.txtInputLayoutPassword.error = ""
            }

            if (binding.txtConfirmPassword.text.isNullOrEmpty()) {
                binding.txtInputLayoutConfirmPassword.error = "Please input confirm password."
                return@setOnClickListener
            } else if (binding.txtPassword.text.toString() != binding.txtConfirmPassword.text.toString()) {
                binding.txtInputLayoutConfirmPassword.error = "Confirm password doest not match."
                return@setOnClickListener
            } else {
                binding.txtInputLayoutConfirmPassword.error = ""
            }

            authViewModel.signUp(
                email = binding.txtUsername.text.toString(),
                password = binding.txtPassword.text.toString()
            )
//            { success, message ->
//
//                if (success) {
//                    binding.txtLogin.performClick();
//                } else {
//                    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
        }
    }
}