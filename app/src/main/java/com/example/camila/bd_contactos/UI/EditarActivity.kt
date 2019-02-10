package com.example.camila.bd_contactos.UI

/*Paula Camila Gonzalez Ortega - Carnet 18398 - Seccion 10
Esta actividad presenta los datos guardados previamente
 de un contacto para poder modificarlos y actulizar la
base de datos. Permite regresar a la MainActivity
 */

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.example.camila.bd_contactos.Logic.Contacto
import com.example.camila.bd_contactos.Logic.DataBase
import com.example.camila.bd_contactos.Logic.MyApplication
import com.example.camila.bd_contactos.MainActivity
import com.example.camila.bd_contactos.R
import kotlinx.android.synthetic.main.activity_editar.*

class EditarActivity : AppCompatActivity() {

    internal lateinit var db:DataBase
    lateinit var myapp:MyApplication
    lateinit var contacto: Contacto
    lateinit var  foto : Bitmap
    var hayfoto = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar)

        //Se crea la conexion con MyApplication y se obtiene el atributo de dicha clase
        myapp = applicationContext as MyApplication
        contacto = myapp.getContactoNuevo()
        //Se rellenan los EditText con los datos del contacto seleccionado a editar
        newName.setText(contacto.name)
        newPhone.setText(contacto.phone)
        newMail.setText(contacto.mail)
        newPhoto.setImageBitmap(contacto.ppic)
        //OnListener de la foto
        newPhoto.setOnClickListener{
            val photoC = 1046
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            if (intent.resolveActivity(packageManager) != null) {
                // Bring up gallery to select a photo
                startActivityForResult(intent, photoC)
            }
        }
    }

    //Modifica los datos del contacto para actualizar la base de datos
    fun onClick_Modificar(view: View){
        db = DataBase(this)
        if (hayfoto){
            val bitmap = (newPhoto.getDrawable() as BitmapDrawable).bitmap
            var actualizado =Contacto(newName.text.toString(),
                newPhone.text.toString(),
                newMail.text.toString(),
                bitmap)
            //Se mantiene el mismo id
            actualizado.id= contacto.id
            //Se llama a la funcion de la base de datos que actualiza
            db.editContacto(actualizado)
            Toast.makeText(this, "Contacto exitosamente editado", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            //envia al user directamente a MainActivity
            startActivity(intent)
            finish()
        }

    }

    //Boton para ver Mainactivity de nuevo
    fun onClick_Back(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }



}
