package com.jdccmobile.devexpertchallenge.ui.screens.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jdccmobile.devexpertchallenge.R
import com.jdccmobile.devexpertchallenge.ui.screens.home.HomeActivity

class MainActivity : AppCompatActivity() {
    // todo mirar por que no le gusta ese nombre
    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        splashScreen.setKeepOnScreenCondition {true}
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}

// todo sacar los strings a resources
// todo error al abrir la app sin internet ¿solo emulador?