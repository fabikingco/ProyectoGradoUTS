package com.fabianardila.proyectogradouts.vista.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.fabianardila.proyectogradouts.network.Callback
import com.fabianardila.proyectogradouts.network.FirestoreService
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    lateinit var firestoreService: FirestoreService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firestoreService = FirestoreService(FirebaseFirestore.getInstance())
    }

    fun btnRegistrarse(view: View) {
        val intent = Intent(this@LoginActivity, RegistroActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        val currentUser= auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            Snackbar.make(
                btnIniciarSesion,
                currentUser.email.toString(),
                Snackbar.LENGTH_LONG
            )
                .setAction("Info", null).show()

            firestoreService.findUserByEmail(currentUser.email.toString(), object : Callback<User> {
                override fun onSuccess(result: User?) {
                    ingresar(result)
                }

                override fun onFailed(exception: Exception) {
                    //TODO("Not yet implemented")
                    Toast.makeText(baseContext, "No encontre el usuario, Me quedo mal la sentencia",
                        Toast.LENGTH_SHORT).show()
                }

            })
        }

    }

    fun iniciarSesion(view: View) {
        auth.signInWithEmailAndPassword(etCorreo.text.toString(), etPass.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun ingresar(user: User?) {
        if (user != null && user.bibliotecario) {
            val intent = Intent(this@LoginActivity, MenuBibliotecarioActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this@LoginActivity, MenuEstudiantesActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
            finish()
        }
    }
}