package com.example.camila.bd_contactos.UI

/*Paula Camila Gonzalez Ortega - Carnet 18398 - Seccion 10
Esta actividad presenta el formulario para ingresar los
datos de un nuevo contacto y asi poder agregarlo a la
base de datos. Permite regresar a la MainActivity
 */

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.example.camila.bd_contactos.Logic.Contacto
import com.example.camila.bd_contactos.Logic.DataBase
import com.example.camila.bd_contactos.MainActivity
import com.example.camila.bd_contactos.R
import kotlinx.android.synthetic.main.activity_crear.*

class CrearActivity : AppCompatActivity() {

    lateinit var  correo: EditText
    lateinit var numero: EditText
    lateinit var nombre: EditText
    internal lateinit var db:DataBase
    internal var first:List<Contacto> = ArrayList<Contacto>()
    lateinit var  foto : Bitmap
    var hayfoto = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear)
        db= DataBase(this)

        //Actividad para que el user inserte su profile picture
        imageView.setOnClickListener {
            val photoC = 1046
            val intent = Intent(
                Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            if (intent.resolveActivity(packageManager) != null) {
                // Se abre el album de fotos
                startActivityForResult(intent, photoC)
            }
        }
    }

    //Metodo que se ejecuta al presionar el boton "agregar", crea un nuevo contacto y lo agrega a la DB
    fun onClick_Add(view: View){
        correo = this.findViewById(R.id.editMail)
        nombre=findViewById(R.id.editName)
        numero=findViewById(R.id.editPhone)
        if (hayfoto) {
            val x:BitmapDrawable = imageView.drawable as BitmapDrawable
            val bitmap:Bitmap = x.bitmap
            //pasa la info ingresada para crear el contacto
            val nuevo = Contacto(
                nombre.text.toString(),
                numero.text.toString(),
                correo.text.toString(),
                foto
            )
            //se agrega a la base de datos
            db.addContacto(nuevo)
            Toast.makeText(applicationContext, "Haz agregado exitosamente a $nombre}", Toast.LENGTH_LONG).show()
        }

    }

    //Boton para ver MainActivity de nuevo
    fun onClick_Home(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //Metodo que permite agregar foto a imageView
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val photoUri = data?.data
        if (photoUri!=null) {
            val selectedImage = MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)
            foto = selectedImage
            imageView.setImageBitmap(foto)
            hayfoto = true
        }
    }

}
