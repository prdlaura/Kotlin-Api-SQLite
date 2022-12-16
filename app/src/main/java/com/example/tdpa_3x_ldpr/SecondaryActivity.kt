package com.example.tdpa_3x_ldpr

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_secondary.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SecondaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondary)
        cargarImagenes()
        getImage()
        logica()
    }

    fun cargarImagenes(){
        val urlImagenPrincipal = "https://static.vecteezy.com/system/resources/previews/003/641/130/non_2x/colorful-pink-blurred-backgrounds-valentine-s-day-pink-background-abstract-gradient-light-pink-illustration-free-vector.jpg"
        val imagenFondo: Uri = Uri.parse(urlImagenPrincipal)
        val urlGatito = "https://stickershop.line-scdn.net/stickershop/v1/product/14809354/LINEStorePC/main.png"
        val imagenGatito: Uri = Uri.parse(urlGatito)

        Glide.with(applicationContext).load(imagenFondo).into(imgFondo2)
        Glide.with(applicationContext).load(imagenGatito).into(imgGatito2)

    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getImage() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val call = getRetrofit().create(ApiService::class.java).getImage()
                val data = call.body()
                runOnUiThread {
                    if (call.isSuccessful) {
                        try {
                            val imagen: String? = data?.imagen
                            val url: Uri = Uri.parse(imagen.toString())
                            Glide.with(applicationContext).load(url).into(imgApi)
                        } catch (e: Exception) {
                            print(e)
                        }
                    } else {
                        Toast.makeText(
                            this@SecondaryActivity,
                            "Se guardó correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } catch (e: Exception) {
            print(e)
        }
    }
    fun logica(){
        val bundle = intent.extras
        val calificacion1 = bundle?.getDouble("calificacion1")
        val calificacion2 = bundle?.getDouble("calificacion2")
        val equivalente = calificacion1!!*.2 + calificacion2!!*.2
        val resultadoSeis = 6-equivalente
        txtParaSeis.setText("Necesitas un: '${resultadoSeis}' para el seis")
        if (calificacion1 == 10.0 && calificacion2 == 10.0){
            txtParaDiez.setText("¡Saca 10!")
            txtResultado.setText("Sí se puede")
        }
        else{
            txtResultado.setText("Buuuu no se puede sacar 10 :(")
        }
    }
}
