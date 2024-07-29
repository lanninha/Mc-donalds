package com.example.splash

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Cadastro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val botaoRegistro: Button = findViewById(R.id.cadastrar)
        val nomeConta: EditText = findViewById(R.id.nomeUsuario)
        val email: EditText = findViewById(R.id.caixinhaEmailCadastro)
        val senha: EditText = findViewById(R.id.caixinhaSenhaCadastro)
        val confirmaSenha: EditText = findViewById(R.id.conferirSenhaCadastro)

        botaoRegistro.setOnClickListener {
            val nome = nomeConta.text.toString()
            val emailInput = email.text.toString()
            val password = senha.text.toString()
            val confirmPassword = confirmaSenha.text.toString()

            // Valida os campos
            if (nome.isBlank() || emailInput.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                showErrorMessage("Todos os campos são obrigatórios!")
            } else if (!isEmailValid(emailInput)) {
                showErrorMessage("E-mail inválido!")
            } else if (password != confirmPassword) {
                showErrorMessage("As senhas não coincidem!")
            } else if (!isPasswordValid(password)) {
                showErrorMessage("A senha deve ter no mínimo 6 caracteres, conter pelo menos uma letra maiúscula, 3 números e um caractere especial.")
            } else {
                // Salva os dados localmente
                saveUserData(nome, emailInput, password)
                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Salva os dados do usuário localmente
    private fun saveUserData(username: String, email: String, password: String) {
        val sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Armazena os dados do usuário
        editor.putString("username", username)
        editor.putString("email", email) // Armazena o e-mail
        editor.putString("password", password)

        // Confirma a gravação dos dados
        editor.apply()
    }

    // Valida os requisitos da senha
    private fun isPasswordValid(password: String): Boolean {
        if (password.length < 6) return false
        if (!password.any { it.isUpperCase() }) return false
        if (password.count { it.isDigit() } < 3) return false
        if (!password.any { it.isLetter() }) return false
        if (!password.any { !it.isLetterOrDigit() }) return false

        return true
    }

    // Valida o formato do e-mail
    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
