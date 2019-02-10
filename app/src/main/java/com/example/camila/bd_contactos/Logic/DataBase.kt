package com.example.camila.bd_contactos.Logic

/*Paula Camila Gonzalez Ortega - Carnet 18398 - Seccion 10
Esta clase es el Content Provider de contactos que utiliza
SQLiteOpenHelper y pemite el CRUD (create, read, update y delete)
 */

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

class DataBase(context: Context):SQLiteOpenHelper(context, DATA_BASE_NAME, null, DATA_BASE_VERSION){
    companion object {
        //informacion de la data base
        private val DATA_BASE_VERSION = 1
        private val DATA_BASE_NAME = "Contactos"

        //columnas (organizacion) de la unica tabla
        private val TABLE_NAME = "contacto"
        private val COL_ID = "Id"
        private val COL_NAME="name"
        private val COL_PHONE="phone"
        private val COL_MAIL="mail"
        private val COL_PPIC="imagen"
    }

    /* Comienza el
   C =create
   R = read
   U = update
   D = delete
    */

    //Metodo que crea la base de dato
    override fun onCreate(db: SQLiteDatabase?) {
        val createT=("CREATE TABLE $TABLE_NAME($COL_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, $COL_NAME TEXT,$COL_PHONE TEXT,$COL_MAIL TEXT, $COL_PPIC BITMAP)")
        db!!.execSQL(createT);
    }
    //Metodo que permite modificar la base de datos
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    //Metodo que lee toda la informacion de la base de datos para mostrarla en la MainActivity como lista
    val misContactos:List<Contacto>
    get(){
        val lista = ArrayList<Contacto>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db=this.writableDatabase
        val cursor=db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()){
            do{
                val blob = cursor.getBlob(cursor.getColumnIndex(COL_PPIC))
                val bitmap =  BitmapFactory.decodeByteArray(blob, 0,blob.size)
                val contact=Contacto(cursor.getString(cursor.getColumnIndex(COL_NAME)),
                    cursor.getString(cursor.getColumnIndex(COL_PHONE)),
                    cursor.getString(cursor.getColumnIndex(COL_MAIL)),
                    bitmap )
                contact.id=cursor.getInt(cursor.getColumnIndex(COL_ID))
                lista.add(contact)
            }while (cursor.moveToNext())
        }
        db.close()
        return lista
    }

    //Metodo que agrega un nuevo contacto a la tabla
    fun addContacto(contacto: Contacto){
        var bitmap = contacto.ppic
        val stream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.PNG, 0,stream)
        val blob = stream.toByteArray()
        val db=this.writableDatabase
        val cv = ContentValues()
        //values.put(ID, contact.id)
        cv.put(COL_NAME, contacto.name)
        cv.put(COL_PHONE, contacto.phone)
        cv.put(COL_MAIL, contacto.mail)
        cv.put(COL_PPIC, blob)
        //Se llama al metodo insert y close
        db.insert(TABLE_NAME, null, cv)
        db.close()
    }

    //Metodo que modifica los datos de un contacto especifico
    fun editContacto(contacto: Contacto):Int{
        var bitmap = contacto.ppic
        val stream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.PNG, 0,stream)
        val blob = stream.toByteArray()
        val db=this.writableDatabase
        val cv = ContentValues()
        //values.put(ID, contact.id)
        cv.put(COL_NAME, contacto.name)
        cv.put(COL_PHONE, contacto.phone)
        cv.put(COL_MAIL, contacto.mail)
        cv.put(COL_PPIC, blob)
        //Se llama al metodo update
        return db.update(TABLE_NAME, cv,"$COL_ID=?", arrayOf(contacto.id.toString()))
    }

    //Metodo que permite eliminar a un contacto determinado
    fun deleteContacto(contacto: Contacto){
        val db=this.writableDatabase
        //Se llama al metodo delete y close
        db.delete(TABLE_NAME, "$COL_ID=?", arrayOf(contacto.id.toString()))
        db.close()
    }





}