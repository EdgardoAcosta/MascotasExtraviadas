/**
 * Clase: mascota
 * Descripción:
 *  Clase que define la estructura de como es una mascota
 * Autor: <Edgardo Acosta Leal>,  <A00813103>
 *  Fecha de creación: 11/09/2018
 * Fecha de última modificación: 11/09/2018
 */
package com.edgardo.a00813103_p1k_mascotas

import android.graphics.Bitmap
import java.io.Serializable

class Mascota(var Nombre: String?, var Raza: Int = 0, var Ubicacion: String?, var Fecha: String?,
              var Telefono: String?, var Correo: String?, @Transient var Id_Imagen: Bitmap?, var Favorito: Boolean = false) : Serializable

