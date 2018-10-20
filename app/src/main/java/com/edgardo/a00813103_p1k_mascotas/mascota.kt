/**
 * Clase: mascota
 * Descripción:
 *  Clase que define la estructura de como es una mascota
 * Autor: <Edgardo Acosta Leal>,  <A00813103>
 *  Fecha de creación: 11/09/2018
 * Fecha de última modificación: 12/09/2018
 */
package com.edgardo.a00813103_p1k_mascotas

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.io.Serializable
import android.graphics.BitmapFactory


class Mascota(val Nombre: String?, val Raza: Int = 0, val Ubicacion: String?, val Fecha: String?,
              val Telefono: String?, val Correo: String?, Id_Imagen: Bitmap?, var Favorito: Boolean = false) : Serializable {
    private val imgBA: ByteArray

    val Id_Imagen: Bitmap?
        get() = BitmapFactory.decodeByteArray(imgBA, 0, imgBA.size)

    init {
        val stream = ByteArrayOutputStream()
        Id_Imagen?.compress(Bitmap.CompressFormat.PNG, 100, stream)

        imgBA = stream.toByteArray()
    }


}

