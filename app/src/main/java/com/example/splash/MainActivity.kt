package com.example.splash

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        window.statusBarColor = Color.parseColor("#DA291C")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        val tempoSplashScreen = if (primeiravez()) 1000 else 3000
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, Login::class.java))
            finish()
        }, tempoSplashScreen.toLong())
    }

    private fun primeiravez(): Boolean {
        val preferencias = getSharedPreferences("Prefs", Context.MODE_PRIVATE)
        val primeiraVez = preferencias.getBoolean("isFirstLaunch", true)
        if (primeiraVez) {
            preferencias.edit().putBoolean("isFirstLaunch", false).apply()
        }
        return primeiraVez // Retornar o valor da primeira verificação
    }
}
