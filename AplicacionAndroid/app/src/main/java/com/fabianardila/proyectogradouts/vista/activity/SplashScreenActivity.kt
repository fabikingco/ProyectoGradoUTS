package com.fabianardila.proyectogradouts.vista.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.fabianardila.proyectogradouts.MainActivity
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.platzi.android.firestore.network.Callback
import com.platzi.android.firestore.network.FirestoreService
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_splash_screen.*
import java.lang.Exception

class SplashScreenActivity : AppCompatActivity() {

    private val TAG = "SplashScreenActivity"

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    lateinit var firestoreService: FirestoreService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        firestoreService = FirestoreService(FirebaseFirestore.getInstance())

        supportActionBar?.hide()

        val currentUser= auth.currentUser
        updateUI(currentUser)

        /*Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)*/
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            Snackbar.make(
                imgSplash,
                currentUser.email.toString(),
                Snackbar.LENGTH_LONG
            )
                .setAction("Info", null).show()

            firestoreService.findUserByEmail(currentUser.email.toString(), object :
                Callback<User> {
                override fun onSuccess(result: User?) {
                    val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    intent.putExtra("user", result)
                    startActivity(intent)
                    finish()
                }

                override fun onFailed(exception: Exception) {
                    //TODO("Not yet implemented")
                }

            })
        } else {
            Handler().postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }
    }


}