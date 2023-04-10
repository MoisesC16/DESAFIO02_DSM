package com.example.desafio02_dsm

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.desafio02_dsm.datos.Alumnos

class AdaptadorAlumno(private val context: Activity, var alumnos: List<Alumnos>) :
    ArrayAdapter<Alumnos?>(context, R.layout.activity_adaptador_alumno, alumnos){
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val layoutInflater = context.layoutInflater
        var rowview: View? = null
        rowview = view ?: layoutInflater.inflate(R.layout.activity_adaptador_alumno, null)
        val tvNombre = rowview!!.findViewById<TextView>(R.id.tvNombre)
        val tvNota1 = rowview.findViewById<TextView>(R.id.tvNota1)
        val tvNota2 = rowview.findViewById<TextView>(R.id.tvNota2)
        val tvNota3 = rowview.findViewById<TextView>(R.id.tvNota3)
        val tvNota4 = rowview.findViewById<TextView>(R.id.tvNota4)
        val tvNota5 = rowview.findViewById<TextView>(R.id.tvNota5)
        val resultado = rowview!!.findViewById<TextView>(R.id.txtResultado)
        val estado = rowview!!.findViewById<TextView>(R.id.txtEstado)

        tvNombre.text = "Nombre : " + alumnos[position].nombre
        tvNota1.text = "Nota1 : " + alumnos[position].nota1
        tvNota2.text = "Nota2 : " + alumnos[position].nota2
        tvNota3.text = "Nota3 : " + alumnos[position].nota3
        tvNota4.text = "Nota4 : " + alumnos[position].nota4
        tvNota5.text = "Nota5 : " + alumnos[position].nota5
        resultado.text = "Promedio : " + alumnos[position].resultado
        estado.text = "Estado : " + alumnos[position].estado
        return rowview
    }
}