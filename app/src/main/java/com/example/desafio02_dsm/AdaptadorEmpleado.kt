package com.example.desafio02_dsm

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.desafio02_dsm.datos.Empleados


class AdaptadorEmpleado(private val context: Activity, var empleados: List<Empleados>) :
    ArrayAdapter<Empleados?>(context, R.layout.activity_adaptador_empleado, empleados){
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val layoutInflater = context.layoutInflater
        var rowview: View? = null
        rowview = view ?: layoutInflater.inflate(R.layout.activity_adaptador_empleado, null)
        val tvNombre = rowview!!.findViewById<TextView>(R.id.tvNombre)
        val tvSalario = rowview.findViewById<TextView>(R.id.tvSalario)
        val resultado = rowview!!.findViewById<TextView>(R.id.txtResultado)
        tvNombre.text = "Nombre : " + empleados[position].nombre
        tvSalario.text = "Salario : " + empleados[position].salario
        resultado.text = "Salario Neto : " + empleados[position].resultado
        return rowview
    }
}