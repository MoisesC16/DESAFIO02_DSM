package com.example.desafio02_dsm

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.example.desafio02_dsm.datos.Alumnos
import com.example.desafio02_dsm.datos.Empleados


class Alumno : AppCompatActivity() {

    var consultaOrdenada: Query = refAlumnos.orderByChild("nombre")
    var alumnos: MutableList<Alumnos>? = null
    var listaAlumnos: ListView? = null

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alumno)
        inicializar()
    }

    private fun inicializar() {
        val fab_agregar: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab_agregar)
        listaAlumnos = findViewById<ListView>(R.id.ListaAlumnos)

        listaAlumnos!!.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                val intent = Intent(getBaseContext(), AddAlumno::class.java)
                intent.putExtra("accion", "e") // Editar
                intent.putExtra("key", alumnos!![i].key)
                intent.putExtra("nombre", alumnos!![i].nombre)
                intent.putExtra("Nota1", alumnos!![i].nota1)
                intent.putExtra("Nota2", alumnos!![i].nota2)
                intent.putExtra("Nota3", alumnos!![i].nota3)
                intent.putExtra("Nota4", alumnos!![i].nota4)
                intent.putExtra("Nota5", alumnos!![i].nota5)
                startActivity(intent)
            }
        })

        listaAlumnos!!.onItemLongClickListener = object : AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                l: Long
            ): Boolean {

                val ad = AlertDialog.Builder(this@Alumno)
                ad.setMessage("¿Desea eliminar este registro?")
                    .setTitle("Confirmación")
                ad.setPositiveButton("Si"
                ) { dialog, id ->
                    alumnos!![position].nombre?.let {
                        refAlumnos.child(it).removeValue()
                    }
                    Toast.makeText(
                        this@Alumno,
                        "Registro Eliminado con éxito", Toast.LENGTH_SHORT
                    ).show()
                }
                ad.setNegativeButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id: Int) {
                        Toast.makeText(
                            this@Alumno,
                            "Cancelado", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                ad.show()
                return true
            }
        }
        fab_agregar.setOnClickListener(View.OnClickListener {
            val i = Intent(getBaseContext(), AddAlumno::class.java)
            i.putExtra("accion", "a") // Agregar
            i.putExtra("key", "")
            i.putExtra("nombre", "")
            i.putExtra("Nota1", "")
            i.putExtra("Nota2", "")
            i.putExtra("Nota3", "")
            i.putExtra("Nota4", "")
            i.putExtra("Nota5", "")
            startActivity(i)
        })
        alumnos = ArrayList<Alumnos>()


        consultaOrdenada.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                alumnos!!.removeAll(alumnos!!)
                for (dato in dataSnapshot.getChildren()) {
                    val alumno: Alumnos? = dato.getValue(Alumnos::class.java)
                    alumno?.key(dato.key)
                    if (alumno != null) {
                        alumnos!!.add(alumno)
                    }
                }
                val adapter = AdaptadorAlumno(
                    this@Alumno,
                    alumnos as ArrayList<Alumnos>
                )
                listaAlumnos!!.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refAlumnos: DatabaseReference = database.getReference("alumnos")
    }
}