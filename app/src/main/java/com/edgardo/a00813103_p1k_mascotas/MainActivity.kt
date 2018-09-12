/**
 * Clase: MainActivity
 * Descripción:
 *  Clase prinsipal donde se mostrara la lista de perros agregados
 * Autor: <Edgardo Acosta Leal>,  <A00813103>
 *  Fecha de creación: 11/09/2018
 * Fecha de última modificación: 11/09/2018
 */

package com.edgardo.a00813103_p1k_mascotas

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    companion object {

        const val BUTTON_CLICKED: String = "show or edit"
        const val NAME_KEY: String = "nombre"
        const val Raza_KEY: String = "raza"
        const val EMAIL_KEY: String = "email"
        const val PHONE_KEY: String = "phone"
        const val ADDRESS_KEY: String = "address"
        const val ID_IMAGE_KEY: String = "idImage"

    }

    var adapterMascosta: MascotaAdapter? = null
    var mascotList : ArrayList<Mascota> = MacostaData().listamascotas


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_registrar.setOnClickListener { view ->
            val intent = Intent(this, RegisterActivity::class.java)

            intent.putExtra(BUTTON_CLICKED, "register")
            startActivityForResult(intent, 0)


//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()

        }
        adapterMascosta = MascotaAdapter(this, mascotList)

        list_dogs.adapter = adapterMascosta

        runOnUiThread {
            adapterMascosta?.notifyDataSetChanged()
        }

        list_dogs.setOnItemClickListener(this)


    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val mascosta: Mascota = mascotList[position]
        Log.d("Row Clicked", position.toString())

        val intent = Intent(this, RegisterActivity::class.java)

        intent.putExtra(BUTTON_CLICKED, "show")
        intent.putExtra(NAME_KEY, mascosta.Nombre)
        intent.putExtra(Raza_KEY, mascosta.Raza)
        intent.putExtra(EMAIL_KEY, mascosta.Correo)
        intent.putExtra(PHONE_KEY, mascosta.Telefono)
        intent.putExtra(ADDRESS_KEY, mascosta.Ubicacion)
        intent.putExtra(ID_IMAGE_KEY, mascosta.Id_Imagen)
        startActivity(intent)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("Activity Result", requestCode.toString())
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                val newMascota = data?.getSerializableExtra(RegisterActivity.NEW_REGISTER) as Mascota
                Log.d("Main-Resume", newMascota.Nombre)
                mascotList.add(newMascota)
                adapterMascosta?.notifyDataSetChanged()
            }

        }

    }
}


class MascotaAdapter(context: Context, mascotas: ArrayList<Mascota>) :
        ArrayAdapter<Mascota>(context, 0, mascotas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = LayoutInflater.from(context).inflate(R.layout.row, parent, false)

        val mascota = getItem(position)

        val tvName = rowView.findViewById(R.id.text_nombre) as TextView
        var tvRaza = rowView.findViewById(R.id.text_raza) as TextView
        val ivDog = rowView.findViewById(R.id.image_mascota) as ImageView


//        Log.d("ID ", mascota.Raza.toString())
//        Log.d("arreglo ", context.resources.getStringArray(R.array.razas_array)[mascota.Raza])
        var dogName = context.resources.getStringArray(R.array.razas_array)[mascota.Raza]

        tvName.text = mascota.Nombre
        tvRaza.text = dogName
        ivDog.setImageResource(mascota.Id_Imagen)
        return rowView
    }
}