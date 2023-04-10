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
import com.example.desafio02_dsm.datos.Empleados


class Empleado : AppCompatActivity() {
    var consultaOrdenada: Query = refEmpleados.orderByChild("nombre")
    var empleados: MutableList<Empleados>? = null
    var listaEmpleados: ListView? = null

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleado)
        inicializar()
    }

    private fun inicializar() {
        val fab_agregar: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab_agregar)
        listaEmpleados = findViewById<ListView>(R.id.ListaEmpleados)

        listaEmpleados!!.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                val intent = Intent(getBaseContext(), AddEmpleado::class.java)
                intent.putExtra("accion", "e") // Editar
                intent.putExtra("key", empleados!![i].key)
                intent.putExtra("nombre", empleados!![i].nombre)
                intent.putExtra("salario", empleados!![i].salario)
                startActivity(intent)
            }
        })


        listaEmpleados!!.onItemLongClickListener = object : AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                l: Long
            ): Boolean {

                val ad = AlertDialog.Builder(this@Empleado)
                ad.setMessage("¿Desea eliminar este registro?")
                    .setTitle("Confirmación")
                ad.setPositiveButton("Si"
                ) { dialog, id ->
                    empleados!![position].nombre?.let {
                        refEmpleados.child(it).removeValue()
                    }
                    Toast.makeText(
                        this@Empleado,
                        "Registro Eliminado con éxito", Toast.LENGTH_SHORT
                    ).show()
                }
                ad.setNegativeButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id: Int) {
                        Toast.makeText(
                            this@Empleado,
                            "Cancelado", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                ad.show()
                return true
            }
        }
        fab_agregar.setOnClickListener(View.OnClickListener {
            val i = Intent(getBaseContext(), AddEmpleado::class.java)
            i.putExtra("accion", "a") // Agregar
            i.putExtra("key", "")
            i.putExtra("nombre", "")
            i.putExtra("salario", "")
            startActivity(i)
        })
        empleados = ArrayList<Empleados>()

        consultaOrdenada.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                empleados!!.removeAll(empleados!!)
                for (dato in dataSnapshot.getChildren()) {
                    val empleado: Empleados? = dato.getValue(Empleados::class.java)
                    empleado?.key(dato.key)
                    if (empleado != null) {
                        empleados!!.add(empleado)
                    }
                }
                val adapter = AdaptadorEmpleado(
                    this@Empleado,
                    empleados as ArrayList<Empleados>
                )
                listaEmpleados!!.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refEmpleados: DatabaseReference = database.getReference("empleados")
    }
}