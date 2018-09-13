package com.edgardo.a00813103_p1k_mascotas


import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import android.widget.*
import kotlinx.android.synthetic.main.activity_favorites.*

class FavoritesActivity : AppCompatActivity(), AdapterView.OnItemClickListener {


    var adapterMascosta: MascotaAdapterFavorites? = null
    var mascotList: ArrayList<Mascota>? = arrayListOf()

    companion object {

        const val BUTTON_CLICKED: String = "show or edit"
        const val NAME_KEY: String = "nombre"
        const val Raza_KEY: String = "raza"
        const val EMAIL_KEY: String = "email"
        const val PHONE_KEY: String = "phone"
        const val ADDRESS_KEY: String = "address"
        const val FAVORITE_KEY: String = "address"
        const val ID_IMAGE_KEY: String = "idImage"
        const val POSITION_KEY: String = "position"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        toolbar_favorites.title = resources.getString(R.string.title_activity_favorites)


        val myToolbar = findViewById<View>(R.id.toolbar_favorites) as Toolbar
        setSupportActionBar(myToolbar)

        actionBar?.setDisplayHomeAsUpEnabled(true)


        // Read value from MainActivity
        val extras = intent.extras ?: return

        val listFavorites = extras.get("list_favorites") as HashMap<Int, Mascota>

        listFavorites.forEach { (_, value) ->
            mascotList!!.add(value)
        }


        adapterMascosta = MascotaAdapterFavorites(this, mascotList!!)

        list_dogs_favorities.adapter = adapterMascosta

        runOnUiThread {
            adapterMascosta?.notifyDataSetChanged()
        }

        list_dogs_favorities.setOnItemClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {

            android.R.id.home -> finish()
        }

        return true
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val mascosta: Mascota = mascotList!![position]
        Log.d("Row Clicked", position.toString())

        val intentFavorites = Intent(this, RegisterActivity::class.java)

        intentFavorites.putExtra("BUTTON_CLICKED", "show")
        intentFavorites.putExtra(NAME_KEY, mascosta.Nombre)
        intentFavorites.putExtra(Raza_KEY, mascosta.Raza)
        intentFavorites.putExtra(EMAIL_KEY, mascosta.Correo)
        intentFavorites.putExtra(PHONE_KEY, mascosta.Telefono)
        intentFavorites.putExtra("ADDRESS_KEY", mascosta.Ubicacion)
        intentFavorites.putExtra(FAVORITE_KEY, mascosta.Favorito)

//        intentFavorites.putExtra(ID_IMAGE_KEY, mascosta.Id_Imagen)
        intentFavorites.putExtra("ID_IMAGE_KEY", mascosta.Id_Imagen)
        intentFavorites.putExtra(POSITION_KEY, position)
        intentFavorites.putExtra("SOURECE", "Favorite")

        startActivity(intentFavorites)
    }
}

class MascotaAdapterFavorites(context: Context, mascotas: ArrayList<Mascota>) :
        ArrayAdapter<Mascota>(context, 0, mascotas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = LayoutInflater.from(context).inflate(R.layout.row, parent, false)

        val mascota = getItem(position)

        val tvName = rowView.findViewById(R.id.text_nombre) as TextView
        var tvRaza = rowView.findViewById(R.id.text_raza) as TextView
        var tvDate = rowView.findViewById(R.id.text_fecha_agregado) as TextView
        val ivDog = rowView.findViewById(R.id.image_mascota) as ImageView


        var dogName = context.resources.getStringArray(R.array.razas_array)[mascota!!.Raza]

        tvName.text = mascota!!.Nombre
        tvRaza.text = dogName
        tvDate.text = mascota!!.Fecha

        Log.d("Row", mascota.Id_Imagen.toString())


        ivDog.setImageBitmap(mascota.Id_Imagen)
        return rowView
    }


}



