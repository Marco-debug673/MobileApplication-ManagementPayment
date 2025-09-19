package com.example.managementpayment.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.managementpayment.databinding.FragmentSlideshowBinding
import com.google.firebase.firestore.FirebaseFirestore

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore
    private var fechaString: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ViewModelProvider(this)[SlideshowViewModel::class.java]

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        db = FirebaseFirestore.getInstance()

        val spinner = binding.spinner
        val options = arrayOf("Seleccione una opción", "Agua", "Luz", "Predial", "Internet", "Comida", "Postre", "Otros")
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            options
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        binding.calendarView.setOnDateChangeListener { _, year, month, day ->
            fechaString = "%02d".format(day) + "-" + "%02d".format((month + 1)) + "-" + year
        }

        binding.button5.setOnClickListener {
            savePayment()
        }

        return root
    }

    private fun savePayment() {
        val nombre = binding.editTextText6.text.toString()
        val tipo = binding.spinner.selectedItem.toString()

        if (nombre.isNotEmpty() && fechaString.isNotEmpty() && tipo != "Seleccione una opción") {
            val userMap = hashMapOf(
                "nombre" to nombre,
                "tipo" to tipo,
                "fecha" to fechaString)

            db.collection("pagos")
                .add(userMap)  // Guardar como un nuevo documento en la colección "pagos"
                .addOnSuccessListener {
                    Toast.makeText(context, "Pago guardado éxitosamente", Toast.LENGTH_SHORT).show()
                    binding.editTextText6.text.clear()
                    binding.spinner.setSelection(0)
                    fechaString =""
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        requireContext(),
                        "Error al guardar el pago: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            Toast.makeText(context, "Llene todos los campos correctamente", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
