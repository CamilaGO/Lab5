package com.example.camila.bd_contactos

/*Paula Camila Gonzalez Ortega - Carnet 18398 - Seccion 10
MainActivity es home en donde aparece la lista de contactos guardados
y un boton para abrir la actividad de crear/guardar un nuevo contacto
en la base de datos
 */

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.camila.bd_contactos.Logic.Contacto
import com.example.camila.bd_contactos.Logic.DataBase
import com.example.camila.bd_contactos.Logic.MyApplication
import com.example.camila.bd_contactos.UI.CrearActivity
import com.example.camila.bd_contactos.UI.VerActivity

class MainActivity : AppCompatActivity() {

    internal lateinit var db: DataBase
    lateinit var myapp: MyApplication
    internal var lista:List<Contacto> = arrayListOf()
    lateinit var  listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myapp = applicationContext as MyApplication
        listView = this.findViewById(R.id.listview_home)
        db= DataBase(this)
        //se llama al metodo refreshData determinado al final de esta clase
        refreshData()

        //Al cliquear un contacto se direcciona a VerActivity para mostrar su informacion almacenada
        listView.setOnItemClickListener { _, _, position, _ ->
            val contacto = Intent(this, VerActivity::class.java)
            // Se modifica el contacto de la clase MyApplication
            myapp.setContactoNuevo(lista[position])
            val seleccionado = lista[position]

            contacto.putExtra("nombre",seleccionado.name)
            contacto.putExtra("telefono",seleccionado.phone)
            contacto.putExtra("correo",seleccionado.mail)
            startActivity(contacto)}

        //Al dejar presionado a un contacto, se elimina de la base de datos
        listView.onItemLongClickListener = object: AdapterView.OnItemLongClickListener{
            override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
                db.deleteContacto(lista[position])
                //Se actualizan los datos de los contactos
                lista = db.misContactos
                var adapter = ArrayAdapter(applicationContext, R.layout.listviewcont, lista)
                listView.adapter = adapter
                Toast.makeText(applicationContext, "Contacto exitosamente eliminado", Toast.LENGTH_SHORT).show()
                return true
            }
        }


    }

    //Metodo que actualiza los datos de la listView
    private fun refreshData() {
        lista = db.misContactos
        val adapter = ArrayAdapter(this, R.layout.listviewcont,lista)
        listView.adapter = adapter
    }

    //Metodo para el boton que sirve para ver activity de nuevo contacto
    fun onClick_Crear(view: View){
        val intent = Intent(this, CrearActivity::class.java)
        startActivity(intent)
    }
}





