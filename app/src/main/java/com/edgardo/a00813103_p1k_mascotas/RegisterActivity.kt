/**
 * Clase: RegistroActivity
 * Descripción:
 *  Clase para editar y mostrar la informacion completa del perro
 * Autor: <Edgardo Acosta Leal>,  <A00813103>
 *  Fecha de creación: 11/09/2018
 * Fecha de última modificación: 11/09/2018
 */

package com.edgardo.a00813103_p1k_mascotas

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*


class RegisterActivity : AppCompatActivity() {

    companion object {

        const val NEW_REGISTER: String = "show or edit"
        const val TAKE_PHOTO_REQUEST = 2
        const val PHOTO_REQUEST_PERMISSION = 3
        const val HAS_THUMBNAIL_KEY = ""
        const val IMAGE_THUMBNAIL_KEY = ""
    }

    var hasThumbnail = false
    var thumbnailBitmap: Bitmap? = null
    var checkBoxViewMode = false
    var position: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val myToolbar = findViewById<View>(R.id.toolbar_register) as Toolbar
        myToolbar.title = ""
        setSupportActionBar(myToolbar)

        actionBar?.setDisplayHomeAsUpEnabled(true)


        // Check orientation of vie

        if (savedInstanceState != null) {
            hasThumbnail = savedInstanceState?.getBoolean(HAS_THUMBNAIL_KEY)
            if (hasThumbnail) {
                thumbnailBitmap = savedInstanceState?.getParcelable(IMAGE_THUMBNAIL_KEY)!!
                image_foto.setImageBitmap(thumbnailBitmap)
            }
        }


        // Adapter for spiner

        val spinner = findViewById(R.id.spinner_razas) as Spinner
        val adapter = ArrayAdapter.createFromResource(this,
                R.array.razas_array, android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.adapter = adapter


        // Permission for taking picture from camera

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            button_image.isEnabled = false

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PHOTO_REQUEST_PERMISSION)
        }


        button_save.setOnClickListener { click(it) }
        button_cancel.setOnClickListener { click(it) }
        // Set Listener for the camera button
        button_image.setOnClickListener { click(it) }

        checkBox_favorites.setOnClickListener { checkBoxClicker(it) }


        // Read value from MainActivity

        val extras = intent.extras ?: return



        when (extras.getString("BUTTON_CLICKED")) {
            "show" -> {

                val lbname = extras.getString(MainActivity.NAME_KEY)
                toolbar_register.title = resources.getString(R.string.title_activity_register_view) + lbname

                val spinerRaza = extras.getInt(MainActivity.Raza_KEY)
                val lbemail = extras.getString(MainActivity.EMAIL_KEY)
                val lbphone = extras.getString(MainActivity.PHONE_KEY)
                val lbaddress = extras.getString("ADDRESS_KEY")
                val chBoxFav = extras.getBoolean(MainActivity.FAVORITE_KEY)
                val idImagen = extras.getParcelable("ID_IMAGE_KEY") as? Bitmap
                val source = extras.getString("SOURECE")



                position = extras.getInt(MainActivity.POSITION_KEY)


                checkBox_favorites.isChecked = chBoxFav

                if (source.equals("Favorite")) {
                    checkBox_favorites.isEnabled = false
                }

                image_foto.setImageBitmap(idImagen)

                label_name.setText(lbname)
                disableEditText(label_name)

                spinner_razas.setSelection(spinerRaza)
                spinner_razas.isEnabled = false
                spinner_razas.isClickable = false



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
                toolbar_register.title = resources.getString(R.string.title_activity_register_add)
                Toast.makeText(applicationContext,
                        "${resources.getString(R.string.register_mode_register)}", Toast.LENGTH_SHORT).show()
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            }
            "edit" -> {
                toolbar_register.title = "Edit mode"
                Toast.makeText(applicationContext,
                        "${resources.getString(R.string.register_mode_edit)}", Toast.LENGTH_SHORT).show()

            }
            else -> {
                Log.d("Register-Activity", "No option selected")
            }
        }


    }

    // Click Listener
    fun click(view: View) {

        button_cancel
        when (view.id) {
            R.id.button_save -> {

                if (!checkBoxViewMode) {
                    if (validateInputs()) {
                        Toast.makeText(applicationContext,
                                "Dog saved", Toast.LENGTH_SHORT).show()


                        val df = SimpleDateFormat("dd-MM-yy -  h:mm a")
                        val date = df.format(Calendar.getInstance().getTime()).toString()


                        var newMascot = Mascota(label_name.text.toString(), spinner_razas.selectedItemPosition,
                                label_address.text.toString(), date, label_phone.text.toString(),
                                label_email.text.toString(), thumbnailBitmap, checkBox_favorites.isChecked)


                        val intent = Intent(this, MainActivity::class.java)
//                    intent.putExtra("thumbnailBitmap", thumbnailBitmap)

                        intent.putExtra(NEW_REGISTER, newMascot)

                        setResult(Activity.RESULT_OK, intent)
                        finish()


                    }
                } else {
                    Toast.makeText(applicationContext,
                            "Fav Change", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
//                    intent.putExtra("thumbnailBitmap", thumbnailBitmap)

                    intent.putExtra("positionModify", position)
                    intent.putExtra("valueModify", checkBox_favorites.isChecked)

                    setResult(Activity.RESULT_OK, intent)
                    finish()

                }


            }
            R.id.button_image -> {
                Toast.makeText(applicationContext,
                        "Take photo", Toast.LENGTH_SHORT).show()
                activateCamera()

            }
            R.id.button_cancel -> {
                setResult(RESULT_CANCELED)
                finish()
            }
        }
    }

    //
    fun checkBoxClicker(view: View) {

        if (!button_save.isEnabled) {

            button_save.isEnabled = true
            checkBoxViewMode = true
        }

    }

    // Override functions

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {

            android.R.id.home -> finish()
        }

        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PHOTO_REQUEST_PERMISSION) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                button_image.isEnabled = true
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        hasThumbnail = false

        if (requestCode == TAKE_PHOTO_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                thumbnailBitmap = data?.extras?.get("data") as Bitmap
                hasThumbnail = true
                image_foto.setImageBitmap(thumbnailBitmap)


            } else {
                Toast.makeText(applicationContext,
                        "Imagen Cacelada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)

        outState?.putBoolean(HAS_THUMBNAIL_KEY, hasThumbnail)
        if (hasThumbnail) {
            outState?.putParcelable(IMAGE_THUMBNAIL_KEY, thumbnailBitmap)
        }

    }

    // Helpers

    fun finishActivity(v: View) {
        finish()
    }

    private fun disableEditText(editText: EditText) {
        editText.isFocusable = false
        editText.isEnabled = false
        editText.isCursorVisible = false
        editText.keyListener = null
//        editText.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun validateInputs(): Boolean {

        var verified = true


        label_name.error = null
        label_email.error = null
        label_phone.error = null
        label_address.error = null

        if (label_name.text.toString().trim().isEmpty()) {
            label_name.error = resources.getString(R.string.register_msg_error)
            verified = false
        } else if (!hasThumbnail) {
            button_image.error = resources.getString(R.string.register_msg_error)
            return false
        } else if (label_email.text.toString().trim().isEmpty()) {
            label_email.error = resources.getString(R.string.register_msg_error)
            verified = false
        } else if (label_phone.text.toString().trim().isEmpty()) {
            label_phone.error = resources.getString(R.string.register_msg_error)
            verified = false
        } else if (label_address.text.toString().trim().isEmpty()) {
            label_address.error = resources.getString(R.string.register_msg_error)
            verified = false
        }

        return verified

    }

    private fun activateCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, TAKE_PHOTO_REQUEST)
        }
    }

    private fun initOnFocus(v: View) {
        label_name.setOnFocusChangeListener { v, hasFocus -> }
    }

}
