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

//var list_favorits: ArrayList<Int> = arrayListOf()
var list_favorits: HashMap<Int, Mascota> = hashMapOf()

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    companion object {

        const val BUTTON_CLICKED: String = "showOrEdit"
        const val NAME_KEY: String = "nombre"
        const val Raza_KEY: String = "raza"
        const val EMAIL_KEY: String = "email"
        const val PHONE_KEY: String = "phone"
        const val ADDRESS_KEY: String = "address"
        const val FAVORITE_KEY: String = "favorito"
        const val ID_IMAGE_KEY: String = "idImage"
        const val POSITION_KEY: String = "idImage"
        const val MASCOT_LIST: String = "listamascota"


    }

    lateinit var adapterMascosta: MascotaAdapter
    var mascotList: ArrayList<Mascota> = arrayListOf() //= MacostaData().listamascotas


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar_main.title = resources.getString(R.string.title_activity_main)
//        setTitle(resources.getString(R.string.title_activity_main))


        fab_registrar.setOnClickListener { view ->
            val intent = Intent(this, RegisterActivity::class.java)

            intent.putExtra(BUTTON_CLICKED, "register")
            startActivityForResult(intent, 0)


//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()

        }

        fab_favorito.setOnClickListener { v: View? ->
            val intent = Intent(this, FavoritesActivity::class.java)

            intent.putExtra("list_favorites", list_favorits)
            startActivityForResult(intent, 2)
        }

        if (savedInstanceState != null) {
            mascotList = savedInstanceState.getSerializable(MASCOT_LIST) as ArrayList<Mascota>
        }


        if (mascotList.size == 0) {
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

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putSerializable(MASCOT_LIST, mascotList)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val mascosta: Mascota = mascotList!![position]
        Log.d("Row Clicked", position.toString())

        val intentShow = Intent(this, RegisterActivity::class.java)

        intentShow.putExtra("BUTTON_CLICKED", "show")
        intentShow.putExtra(NAME_KEY, mascosta.Nombre)
        intentShow.putExtra(Raza_KEY, mascosta.Raza)
        intentShow.putExtra(EMAIL_KEY, mascosta.Correo)
        intentShow.putExtra(PHONE_KEY, mascosta.Telefono)
        intentShow.putExtra("ADDRESS_KEY", mascosta.Ubicacion)
        Log.d("Mascosta", mascosta.Ubicacion)
        intentShow.putExtra(FAVORITE_KEY, mascosta.Favorito)
        intentShow.putExtra("ID_IMAGE_KEY", mascosta.Id_Imagen)
        intentShow.putExtra(POSITION_KEY, position)
        intentShow.putExtra("SOURECE", "Main")


//        startActivity(intentShow)
        startActivityForResult(intentShow, 10)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("Activity Result", requestCode.toString())
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                val newMascota = data?.getSerializableExtra(RegisterActivity.NEW_REGISTER) as Mascota

                mascotList!!.add(newMascota)
                list_favorits.clear()
                adapterMascosta?.notifyDataSetChanged()
            }

        } else if (requestCode == 10) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d("Modify Favorite", requestCode.toString())
                val pos = data?.extras?.getInt("positionModify")
                val newValue = data?.extras?.getBoolean("valueModify")
                if (pos != null) {
                    val auxInt: Int = pos

                    mascotList[auxInt].Favorito = newValue!!
                    list_favorits.clear()
                    adapterMascosta?.notifyDataSetChanged()
                }


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
        val tvRaza = rowView.findViewById(R.id.text_raza) as TextView
        val tvDate = rowView.findViewById(R.id.text_fecha_agregado) as TextView
        val ivDog = rowView.findViewById(R.id.image_mascota) as ImageView


        val dogName = context.resources.getStringArray(R.array.razas_array)[mascota!!.Raza]

        tvName.text = mascota!!.Nombre
        tvRaza.text = dogName
        tvDate.text = mascota!!.Fecha

        if (mascota.Favorito) {
            list_favorits.put(position, mascota)


        }


        ivDog.setImageBitmap(mascota.Id_Imagen)
        return rowView
    }


}


