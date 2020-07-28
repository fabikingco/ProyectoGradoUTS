package com.fabianardila.proyectogradouts.vista.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.fabianardila.proyectogradouts.BuildConfig
import com.fabianardila.proyectogradouts.R
import kotlinx.android.synthetic.main.activity_about.*
import java.util.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        initToolbar()

        tvVersionApp!!.text = BuildConfig.VERSION_NAME
        val buildDate: Date = BuildConfig.BUILD_TIME
        tvVersionDate.text = buildDate.toString()
    }

    private fun initToolbar() {
        val toolbar =
            findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Sobre la aplicación"
    }
}