package com.example.managementpayment.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.managementpayment.databinding.FragmentGalleryBinding
import com.google.firebase.firestore.FirebaseFirestore

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ViewModelProvider(this)[GalleryViewModel::class.java]

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        db = FirebaseFirestore.getInstance()

        binding.button4.setOnClickListener {
            saveDataToFirestore()
        }

        return root
    }

    private fun saveDataToFirestore() {
        val name = binding.editTextText4.text.toString()
        val lastName = binding.editTextText5.text.toString()
        val salary = binding.editTextNumberDecimal.text.toString().toDoubleOrNull()

        if (name.isNotEmpty() || lastName.isNotEmpty() || salary!!.isInfinite()) {
            val userMap = hashMapOf(
                "nombre" to name,
                "apellido" to lastName,
                "salario" to salary
            )

            db.collection("salario")
                .add(userMap) // Guardar como un nuevo documento en la colección "salario"
                .addOnSuccessListener {
                    Toast.makeText(context,"Los datos han sido registrados éxitosamente", Toast.LENGTH_SHORT).show()
                    binding.editTextText4.text.clear()
                    binding.editTextText5.text.clear()
                    binding.editTextNumberDecimal.text.clear()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context,"Error al guardar los datos: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(context, "Llene todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}