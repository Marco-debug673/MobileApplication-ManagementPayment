package com.example.managementpayment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Analytics
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("Mensaje", "Integración de Firebase completa")
        analytics.logEvent("InitScreen", bundle)

        emailEditText = findViewById(R.id.editTextTextEmailAddress2)
        passwordEditText = findViewById(R.id.editTextTextPassword)
        mAuth = FirebaseAuth.getInstance()
        val registerTextView = findViewById<TextView>(R.id.textView4)
        val iniciarButton = findViewById<Button>(R.id.button)

        registerTextView.setOnClickListener {
            val intent = Intent(this, RegistroUsuario::class.java)
            startActivity(intent)
        }

        iniciarButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Por favor ingrese correo y contraseña", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Bienvenido $email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Menu::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Datos incorrectos: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}