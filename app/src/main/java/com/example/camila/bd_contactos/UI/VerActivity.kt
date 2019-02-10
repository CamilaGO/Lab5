package com.example.camila.bd_contactos.UI

/*Paula Camila Gonzalez Ortega - Carnet 18398 - Seccion 10
Esta actividad presenta los datos del contacto seleccionado
en el main y llamarlo o escribirle un mail.
Permite regresar a la MainActivity
 */

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.camila.bd_contactos.Logic.MyApplication
import com.example.camila.bd_contactos.MainActivity
import com.example.camila.bd_contactos.R
import kotlinx.android.synthetic.main.activity_ver.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class VerActivity : AppCompatActivity() {

    lateinit var mycorreo: TextView
    lateinit var mynumero: TextView
    lateinit var mynombre: TextView
    lateinit var myfoto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver)

        //Se conecta con MyApplication para extraer su contacto
        val myapp =applicationContext as MyApplication
        val persona = myapp.getContactoNuevo()
        //Se obtiene info del contacto seleccionado y se llenan los campos
        val nombre= persona.name
        val correo= persona.mail
        val numero= persona.phone
        //se llenan los campos
        mynombre=findViewById(R.id.cName)
        mynumero=findViewById(R.id.cPhone)
        mycorreo = findViewById(R.id.cMail)
        cPhoto.setImageBitmap(myapp.getContactoNuevo().ppic)
        mynombre.text = nombre
        mycorreo.text = correo
        mynumero.text = numero

        //Se llama al contacto si hace click es su numero telefonico
        mynumero.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$numero")
            startActivity(intent)
        }

        //Se manda mail al contacto si presiona su correo electronico
        //Si apacha en el email, se abrira la actividad para enviar un mensaje
        mycorreo.setOnClickListener{
            val intent  = Intent(this, CorreoActivity::class.java)
            intent.putExtra("recipientMail",correo)
            startActivity(intent)
        }

    }

    //Boton para ver MainActivity de nuevo
    fun onClick_Home(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
