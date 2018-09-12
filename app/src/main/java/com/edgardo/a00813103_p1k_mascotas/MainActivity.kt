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
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.edgardo.a00813103_p1k_mascotas.R.styleable.Snackbar

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.row.*

var list_favorits : ArrayList<Int> = arrayListOf()

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
    var mascotList: ArrayList<Mascota>? = arrayListOf() //= MacostaData().listamascotas


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
        if (mascotList!!.size == 0) {
            Toast.makeText(applicationContext,
                    "No dogs on list", Toast.LENGTH_SHORT).show()
        }
        adapterMascosta = MascotaAdapter(this, mascotList!!)

        list_dogs.adapter = adapterMascosta

        runOnUiThread {
            adapterMascosta?.notifyDataSetChanged()
        }

        list_dogs.setOnItemClickListener(this)


    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val mascosta: Mascota = mascotList!![position]
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
                val thumbnailBitmap = data.getParcelableExtra<Bitmap>("thumbnailBitmap")
                Log.d("Main-Resume", newMascota.Nombre)
                Log.d("Bitmap", thumbnailBitmap.toString())
                newMascota.Id_Imagen = thumbnailBitmap
                mascotList!!.add(newMascota)
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
        var tvDate = rowView.findViewById(R.id.text_fecha_agregado) as TextView
        val ivDog = rowView.findViewById(R.id.image_mascota) as ImageView
        val favorito = rowView.findViewById(R.id.button_favorito) as ImageButton


        var dogName = context.resources.getStringArray(R.array.razas_array)[mascota!!.Raza]

        tvName.text = mascota!!.Nombre
        tvRaza.text = dogName
        tvDate.text = mascota!!.Fecha


        favorito.setOnClickListener { v: View? ->
//            click_Favorito(v!!, mascota)
            Toast.makeText(context,
                    "Click button", Toast.LENGTH_SHORT).show()
        }


        ivDog.setImageBitmap(mascota.Id_Imagen)
        return rowView
    }

    private fun click_Favorito(v: View, mascota: Mascota) {

        Log.d("View Id", v.id.toString())
        val favorito = v.findViewById(R.id.button_favorito) as ImageButton
//        context.resources.drawable(favorito)
//
//        if (context.resources.getDrawable(favorito) == getDrawable R.drawable.ic_star_full) {
//            mascota.Favorito = false
//            favorito.setImageResource(R.drawable.ic_star_empty)
//        } else {
//            mascota.Favorito = false
//            favorito.setImageResource(R.drawable.ic_star_full)
//        }
    }
}

