/**
 * Clase: RegistroActivity
 * Descripción:
 *  Clase para editar y mostrar la informacion completa del perro
 * Autor: <Edgardo Acosta Leal>,  <A00813103>
 *  Fecha de creación: 11/09/2018
 * Fecha de última modificación: 11/09/2018
 */

package com.edgardo.a00813103_p1k_mascotas

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    companion object {

        const val NEW_REGISTER: String = "show or edit"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val spinner = findViewById(R.id.spinner_razas) as Spinner
        val adapter = ArrayAdapter.createFromResource(this,
                R.array.razas_array, android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.adapter = adapter

        button_image.setOnClickListener { click(it) }


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
                button_save.setOnClickListener { click(it) }
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


    fun click(view: View) {


        when (view.id) {
            R.id.button_save -> {
                if (validateInputs()) {
                    Toast.makeText(applicationContext,
                            "Dog saved", Toast.LENGTH_SHORT).show()
                    var newMascot = Mascota(label_name.text.toString(), spinner_razas.selectedItemPosition,
                            label_address.text.toString(), label_phone.text.toString(),
                            label_email.text.toString(), R.drawable.defaul_dog)


                    val intent = Intent(this, MainActivity::class.java)

                    intent.putExtra(NEW_REGISTER, newMascot)

                    setResult(Activity.RESULT_OK, intent)

                    finish()


                }

            }
            R.id.button_image -> {
                Toast.makeText(applicationContext,
                        "Take photo", Toast.LENGTH_SHORT).show()

            }
        }
    }


    private fun validateInputs(): Boolean {

        var verified = true


        label_name.error = null
        label_email.error = null
        label_phone.error = null
        label_address.error = null

        if (label_name.text.toString().trim().isEmpty()) {
//            label_name.setHint(R.string.register_msg_error)//it gives user to hint
            label_name.error = resources.getString(R.string.register_msg_error)
            verified = false
        } else if (label_email.text.toString().trim().isEmpty()) {
//            label_email.setHint(R.string.register_msg_error)//it gives user to hint
            label_name.error = resources.getString(R.string.register_msg_error)
            verified = false
        } else if (label_phone.text.toString().trim().isEmpty()) {
//            label_phone.setHint(R.string.register_msg_error)//it gives user to hint
            label_name.error = resources.getString(R.string.register_msg_error)
            verified = false
        } else if (label_address.text.toString().trim().isEmpty()) {
//            label_name.setHint(R.string.register_msg_error)//it gives user to hint
            label_name.error = resources.getString(R.string.register_msg_error)
            verified = false
        } else if (label_address.text.toString().trim().isEmpty()) {
//            label_name.setHint(R.string.register_msg_error)//it gives user to hint
            label_name.error = resources.getString(R.string.register_msg_error)
            verified = false
        }

        return verified

    }

    private fun validateImage(): Int {
        if (image_foto.id == R.drawable.defaul_dog) {
            Log.d("Image compare", "Is default")
            return R.drawable.defaul_dog
        }
        return image_foto.id
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
