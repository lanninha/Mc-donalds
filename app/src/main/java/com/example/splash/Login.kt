package com.example.splash

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class Login : AppCompatActivity() {
    private var attemptCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        window.statusBarColor = Color.parseColor("#DA291C")

        val botaoLogin: Button = findViewById(R.id.botaoEntrar)
        val botaoRegistro: Button = findViewById(R.id.botaoCadastro)
        val username: EditText = findViewById(R.id.caixinhaEmail)
        val password: EditText = findViewById(R.id.caixinhaSenha)

        botaoLogin.setOnClickListener {
            val nome = username.text.toString()
            val senha = password.text.toString()

            if (validacaoLogin(nome, senha)) {
                val intent = Intent(this, Home::class.java)
                intent.putExtra("USERNAME", nome) // Corrigi o nome da chave
                startActivity(intent)
                finish()
            } else {
                attemptCount++
                if (attemptCount >= 5) {
                    bloquearMenssagem()
                    disableInputs()
                    Handler(Looper.getMainLooper()).postDelayed({
                        enableInputs()
                    }, 10000)
                } else {
                    showErrorMenssage()
                    highlightFields()
                }
            }
        }

        botaoRegistro.setOnClickListener {
            startActivity(Intent(this, Cadastro::class.java))
        }
    }

    private fun validacaoLogin(nome: String, senha: String): Boolean {
        val preferencia = getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        val storedUsername = preferencia.getString("username", null)
        val storedPassword = preferencia.getString("password", null)

        // Adiciona mensagens de depuração
        println("Stored Username: $storedUsername")
        println("Stored Password: $storedPassword")
        println("Entered Username: $nome")
        println("Entered Password: $senha")

        return nome == storedUsername && senha == storedPassword
    }

    private fun showErrorMenssage() {
        Toast.makeText(this, "Usuário/Senha Inválidos!", Toast.LENGTH_SHORT).show()
    }

    private fun bloquearMenssagem() {
        Toast.makeText(this, "Login bloqueado, aguarde 10s!", Toast.LENGTH_SHORT).show()
    }

    private fun highlightFields() {
        findViewById<EditText>(R.id.caixinhaEmail).background.setTint(ContextCompat.getColor(this, R.color.branco))
        findViewById<EditText>(R.id.caixinhaSenha).background.setTint(ContextCompat.getColor(this, R.color.branco))
    }

    private fun disableInputs() {
        findViewById<Button>(R.id.botaoEntrar).isEnabled = false
        findViewById<Button>(R.id.botaoCadastro).isEnabled = false
        findViewById<EditText>(R.id.caixinhaEmail).isEnabled = false
        findViewById<EditText>(R.id.caixinhaSenha).isEnabled = false
    }

    private fun enableInputs() {
        findViewById<Button>(R.id.botaoEntrar).isEnabled = true // Corrigido para true
        findViewById<Button>(R.id.botaoCadastro).isEnabled = true // Corrigido para true
        findViewById<EditText>(R.id.caixinhaEmail).isEnabled = true // Corrigido para true
        findViewById<EditText>(R.id.caixinhaSenha).isEnabled = true // Corrigido para true
    }
}
