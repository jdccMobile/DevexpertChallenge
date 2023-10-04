package com.jdccmobile.devexpertchallenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class SplashScreenActivity : AppCompatActivity() {
    // todo mirar por que no le gusta ese nombre
    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splashScreen.setKeepOnScreenCondition {true}
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}