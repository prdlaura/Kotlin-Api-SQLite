package com.example.tdpa_3x_ldpr

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cargarImagenes()
        btnGuardar.setOnClickListener{insertar()}
        btnBuscar.setOnClickListener{encontrar()}
        btnEliminar.setOnClickListener{borrar()}
        btnEditar.setOnClickListener{editar()}
        btnEnviar.setOnClickListener{enviar()}
    }
    fun cargarImagenes(){
        val urlImagenPrincipal = "https://static.vecteezy.com/system/resources/previews/003/641/130/non_2x/colorful-pink-blurred-backgrounds-valentine-s-day-pink-background-abstract-gradient-light-pink-illustration-free-vector.jpg"
        val imagenFondo: Uri = Uri.parse(urlImagenPrincipal)
        val urlGatito = "https://stickershop.line-scdn.net/stickershop/v1/product/14809354/LINEStorePC/main.png"
        val imagenGatito: Uri = Uri.parse(urlGatito)
        val urlGifGatito = "https://media.giphy.com/media/1ym5BOW05OoZWQg8mg/giphy.gif"
        val gifGatitop: Uri = Uri.parse(urlGifGatito)
        Glide.with(applicationContext).load(imagenFondo).into(imgFondo)
        Glide.with(applicationContext).load(imagenGatito).into(imgGatito)
        Glide.with(applicationContext).load(gifGatitop).into(gifGatito)
    }
    fun insertar(){
        if (validarTodo()){
            val admin = AdminSQLiteOpenHelper(this,"administracion",null,1)
            val db = admin.writableDatabase
            val registro = ContentValues()

            registro.put("nombre", txtNombre.text.toString())
            registro.put("nombreMateria", txtMateria.text.toString())
            registro.put("primerCal", txtCalificacion1.text.toString().toDouble())
            registro.put("segundaCal", txtCalificacion2.text.toString().toDouble())
            db.insert("estudiantes", null, registro)
            db.close()

            txtNombre.setText("")
            txtMateria.setText("")
            txtCalificacion1.setText("")
            txtCalificacion2.setText("")
            Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show()
        }

    }
    fun encontrar(){
        if (validarNombre()){
            val admin = AdminSQLiteOpenHelper(this,"administracion",null,1)
            val db = admin.writableDatabase
            var fila = db.rawQuery("SELECT nombreMateria, primerCal, segundaCal FROM estudiantes WHERE nombre='${txtNombre.text}'",null)
            if (fila.moveToFirst()){
                txtMateria.setText(fila.getString(0))
                txtCalificacion1.setText(fila.getString(1))
                txtCalificacion2.setText(fila.getString(2))
            }
            else{
                Toast.makeText(this, "No se encontró el estudiante", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun editar(){
        if (validarTodo()){
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("nombre", txtNombre.text.toString())
            registro.put("nombreMateria", txtMateria.text.toString())
            registro.put("primerCal", txtCalificacion1.text.toString().toDouble())
            registro.put("segundaCal", txtCalificacion2.text.toString().toDouble())
            val cant = bd.update("estudiantes", registro, "nombre='${txtNombre.text}'", null)
            bd.close()
            if(cant == 1){
                Toast.makeText(this, "Información actualizada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Credenciales no válidas", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun borrar(){
        if (validarNombre()){
            val admin = AdminSQLiteOpenHelper(this,"administracion",null,1)
            val db = admin.writableDatabase
            var cant = db.delete("estudiantes", "nombre='${txtNombre.text}'",null)
            db.close()
            if (cant == 1){
                Toast.makeText(this, "Se borró adecuadamente", Toast.LENGTH_SHORT).show()
                txtNombre.setText("")
                txtMateria.setText("")
                txtCalificacion1.setText("")
                txtCalificacion2.setText("")
            }
            else{
                Toast.makeText(this, "No se encontró el estudiante", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun enviar(){
        if (validarTodo()){
            val intento = Intent(this, SecondaryActivity::class.java)
            intento.putExtra("calificacion1",txtCalificacion1.text.toString().toDouble())
            intento.putExtra("calificacion2",txtCalificacion2.text.toString().toDouble())
            startActivity(intento)
        }

    }
    private fun validarTodo():Boolean {
        var valido = true
        if(TextUtils.isEmpty(txtNombre.text.toString())){
            txtNombre.error = "Coloca un nombre"
            valido = false
        }
        if(TextUtils.isEmpty(txtMateria.text.toString())){
            txtMateria.error = "Coloca una materia"
            valido = false
        }
        if(TextUtils.isEmpty(txtCalificacion1.text.toString())){
            txtCalificacion1.error = "Coloca tu primera calificación"
            valido = false
        }
        if(TextUtils.isEmpty(txtCalificacion2.text.toString())){
            txtCalificacion2.error = "Coloca tu segunda calificación"
            valido = false
        }
        return valido
    }
    private fun validarNombre(): Boolean{
        var valido = true
        if(TextUtils.isEmpty(txtNombre.text.toString())){
            txtNombre.error = "Favor de poner un nombre"
            valido = false
        }
        return valido
    }
}

