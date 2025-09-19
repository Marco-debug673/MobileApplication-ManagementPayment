package com.example.managementpayment.ui.noticias

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import com.example.managementpayment.R
import com.google.firebase.firestore.FirebaseFirestore

class NoticiasFragment : Fragment() {

    companion object {
        fun newInstance() = NoticiasFragment()
    }

    private val db = FirebaseFirestore.getInstance()
    private val viewModel: NoticiasViewModel by viewModels()
    private lateinit var talDinero: TableLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_noticias, container, false)

        // Initialize the TableLayout after inflating the view
        talDinero = view.findViewById(R.id.tlDinero)

        // Load the payments into the table
        llenardinero()

        return view
    }

    @SuppressLint("InflateParams")
    private fun llenardinero() {
        db.collection("salario")
            .get()
            .addOnSuccessListener { result ->
                talDinero.removeAllViews()
                for (document in result) {
                    val registro = LayoutInflater.from(context).inflate(R.layout.tabladinero, null, false)
                    registro.findViewById<TextView>(R.id.tvId)
                    val tvNombre = registro.findViewById<TextView>(R.id.tvNombre)
                    val tvApellido = registro.findViewById<TextView>(R.id.tvApellido)
                    val tvSalario = registro.findViewById<TextView>(R.id.tvSalario)

                    tvNombre.text = document.getString("nombre")
                    tvApellido.text = document.getString("apellido")
                    val salario = document.getLong("salario")?.toString() ?: "No disponible"
                    tvSalario.text = salario

                    talDinero.addView(registro)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("NoticiasFragment", "Error getting documents: ", exception)
                Toast.makeText(requireContext(), "Error al obtener el historial", Toast.LENGTH_SHORT).show()
            }
    }
}