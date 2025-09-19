package com.example.managementpayment.ui.consejosfinancieros

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.managementpayment.R
import kotlin.random.Random

class ConsejosFinancierosFragment : Fragment() {

    companion object {
        fun newInstance() = ConsejosFinancierosFragment()
    }

    private val viewModel: ConsejosFinancierosViewModel by viewModels()

    private val financialAdvice = listOf(
        "Ahorra al menos el 20% de tus ingresos cada mes.",
        "Invierta en una cartera diversificada para minimizar el riesgo.",
        "Elabore un presupuesto y aténgase a él para gestionar sus gastos.",
        "Crea un fondo de emergencia para cubrir gastos imprevistos.",
        "Paga las deudas con intereses elevados lo antes posible.",
        "Automatice sus ahorros para garantizar contribuciones constantes.",
        "Haga un seguimiento de sus gastos para identificar las áreas en las que puede reducirlos.",
        "Revise periódicamente sus objetivos financieros y ajústelos según sea necesario."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_consejos_financieros, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.button6)
        val textView: TextView = view.findViewById(R.id.textView18)

        button.setOnClickListener {
            val advice = financialAdvice[Random.nextInt(financialAdvice.size)]
            textView.text = advice
        }
    }
}