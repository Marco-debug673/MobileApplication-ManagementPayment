package com.example.managementpayment.ui.historialpagos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.managementpayment.R
import com.google.firebase.firestore.FirebaseFirestore

class HistorialPagosFragment : Fragment() {

    companion object {
        fun newInstance() = HistorialPagosFragment()
    }

    private val db = FirebaseFirestore.getInstance()
    private val viewModel: HistorialPagosViewModel by viewModels()
    private lateinit var talPagos: TableLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_historial_pagos, container, false)

        // Initialize the TableLayout after inflating the view
        talPagos = view.findViewById(R.id.tlPagos)

        // Load the payments into the table
        llenarpagos()

        return view
    }

    private fun llenarpagos() {
        db.collection("pagos")
            .get()
            .addOnSuccessListener { result ->
                talPagos.removeAllViews()
                for (document in result) {
                    val registro = LayoutInflater.from(context).inflate(R.layout.tablapagos, null, false)
                    val tvId = registro.findViewById<TextView>(R.id.tvId)
                    val tvNombre = registro.findViewById<TextView>(R.id.tvNombre)
                    val tvTipo = registro.findViewById<TextView>(R.id.tvTipos)
                    val tvFecha = registro.findViewById<TextView>(R.id.tvFecha)

                    tvNombre.text = document.getString("nombre")
                    tvTipo.text = document.getString("tipo")
                    tvFecha.text = document.getString("fecha")

                    talPagos.addView(registro)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("HistorialPagosFragment", "Error getting documents: ", exception)
                Toast.makeText(requireContext(), "Error al obtener el historial", Toast.LENGTH_SHORT).show()
            }
    }
}
