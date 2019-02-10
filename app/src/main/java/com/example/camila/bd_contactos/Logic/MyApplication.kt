package com.example.camila.bd_contactos.Logic

/*Paula Camila Gonzalez Ortega - Carnet 18398 - Seccion 10
Clase que hereda de Application y permite relacionar
todas las actividades
 */

import android.app.Application

class MyApplication:Application(){
    //Su unico atributo es un objeto de tipo contacto
    lateinit var contacto: Contacto

    //Asigna el contacto de parametro al contacto atributo
    fun setContactoNuevo(persona:Contacto){
        this.contacto = persona
    }

    //getter de su unico atributo
    fun getContactoNuevo():Contacto{
        return this.contacto
    }


}