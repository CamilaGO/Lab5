package com.example.camila.bd_contactos.Logic

import android.graphics.Bitmap

class Contacto{
    //Atributos que un objeto de este tipo debe tener
    var id: Int=0
    var name: String=""
    var phone: String=""
    var mail: String=""
    var ppic: Bitmap? = null

    constructor()

    constructor(nombre: String, telefono: String, correo: String, foto: Bitmap){
        this.name = nombre
        this.phone = telefono
        this.mail = correo
        this.ppic = foto
    }

    override fun toString(): String {
        return this.name
    }

}