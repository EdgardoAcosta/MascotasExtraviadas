/**
 * Clase: RegistroActivity
 * Descripción:
 *  Clase para editar y mostrar la informacion completa del perro
 * Autor: <Edgardo Acosta Leal>,  <A00813103>
 *  Fecha de creación: 11/09/2018
 * Fecha de última modificación: 11/09/2018
 */

package com.edgardo.a00813103_p1k_mascotas

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val spinner = findViewById(R.id.spinner_razas) as Spinner
        val adapter = ArrayAdapter.createFromResource(this,
                R.array.razas_array, android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.adapter = adapter


        val extras = intent.extras ?: return

        Log.d("Register-Activity", extras.getString(MainActivity.BUTTON_CLICKED))

        when (extras.getString(MainActivity.BUTTON_CLICKED)) {
            "show" -> {
                val lbname = extras.getString(MainActivity.NAME_KEY)
                val spinerRaza = extras.getInt(MainActivity.Raza_KEY)
                val lbemail = extras.getString(MainActivity.EMAIL_KEY)
                val lbphone = extras.getString(MainActivity.PHONE_KEY)
                val lbaddress = extras.getString(MainActivity.ADDRESS_KEY)
                val idImagen = extras.getInt(MainActivity.ID_IMAGE_KEY)

                val imagenLibro = getDrawable(idImagen)
                image_foto.setImageDrawable(imagenLibro)

                label_name.setText(lbname)
                disableEditText(label_name)

                spinner_razas.setSelection(spinerRaza)
                spinner_razas.isEnabled = false
                spinner_razas.isClickable = false


//        Log.d("Registro",lbemail.toString())
                label_email.setText(lbemail)
                disableEditText(label_email)

                label_phone.setText(lbphone)
                disableEditText(label_phone)

                label_address.setText(lbaddress)
                disableEditText(label_address)

                button_image.isEnabled = false
                button_image.isCursorVisible = false
                button_save.isEnabled = false
                button_save.isCursorVisible = false
                Toast.makeText(applicationContext,
                        "${resources.getString(R.string.register_mode_show)}", Toast.LENGTH_SHORT).show()


            }
            "register" -> {
                Toast.makeText(applicationContext,
                        "${resources.getString(R.string.register_mode_register)}", Toast.LENGTH_SHORT).show()
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            }
            "edit" -> {
                Toast.makeText(applicationContext,
                        "${resources.getString(R.string.register_mode_edit)}", Toast.LENGTH_SHORT).show()

            }
            else -> {
                Log.d("Register-Activity", "No option selected")
            }
        }


    }

    private fun disableEditText(editText: EditText) {
        editText.isFocusable = false
        editText.isEnabled = false
        editText.isCursorVisible = false
        editText.keyListener = null
//        editText.setBackgroundColor(Color.TRANSPARENT)
    }

    fun finishActivity(v: View) {
        finish()
    }
}
