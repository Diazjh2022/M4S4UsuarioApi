package com.example.m4s4usuarioapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.m4s4usuarioapi.Retrofit.RestEngine
import com.example.m4s4usuarioapi.Retrofit.Usuario
import com.example.m4s4usuarioapi.Retrofit.UsuarioApiService
import com.example.m4s4usuarioapi.Retrofit.UsuarioItem
import com.example.m4s4usuarioapi.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnObtener.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            obtenerRegistros()
        }

        binding.btnRegistrar.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                val x: Int = async {
                    cantidadRegistros()
                }.await() //espera a carga de imágenes

                guardarRegistro(x, UsuarioItem(
                    binding.txtUsuario.text.toString(),
                    binding.txtClave.text.toString(),
                    binding.txtNombre.text.toString()))
            }
        }
    }

    private fun guardarRegistro(id:Int, usuarioItem:  UsuarioItem) {
        CoroutineScope(Dispatchers.IO).launch {
            val llamada: UsuarioApiService=
                RestEngine.getRestEngine().create(UsuarioApiService::class.java)
            val resultado: Call<UsuarioItem> = llamada.agregarusuario(id, usuarioItem)
            val u:UsuarioItem? = resultado.execute().body()

            if (u != null){
                runOnUiThread {
                    binding.txtId.text = "Elemento agregado: \n ${Gson().toJson(u)}"
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun cantidadRegistros(): Int{
        val llamada: UsuarioApiService =
            RestEngine.getRestEngine().create(UsuarioApiService::class.java)
        val resultado: Call<Usuario> = llamada.obtenerUsuario("bd.json")
        val u:Usuario? = resultado.execute().body()
        return u!!.size
    }

    private fun obtenerRegistros() {
        CoroutineScope(Dispatchers.IO).launch {
            val llamada: UsuarioApiService =
                RestEngine.getRestEngine().create(UsuarioApiService::class.java)
            val resultado: Call<Usuario> = llamada.obtenerUsuario("bd.json")
            val u:Usuario? = resultado.execute().body()

            if(u != null){
                runOnUiThread {
                    binding.txtRegistros.text = "Usuario: \n\n"
                    for(i in u){
                        binding.txtRegistros.append(i.nombre + "\n")
                    }

                    /*
                    Picasso.with(applicationContext)
                        .load("https://i.imgur.com/ytrkU2H.jpeg")
                        .resize(600, 600)
                        .into(binding.imageView)
                     */

                    /*
                    Picasso.with(applicationContext)
                        .load("https://i.imgur.com/ytrkU2H.jpeg")
                        .resize(600, 600)
                        .centerInside()
                        .into(binding.imageView)
                     */

                    /*
                    Picasso.with(applicationContext)
                        .load("https://i.imgur.com/ytrkU2H.jpeg")
                        .resize(600, 600)
                        .centerInside()
                        .into(binding.imageView)
                     */

                    /*
                    Picasso.with(applicationContext)
                        .load("https://i.imgur.com/ytrkU2H.jpeg")
                        .rotate(180f)
                        .into(binding.imageView, object : Callback{
                            override fun onSuccess() {
                                binding.progressBar.visibility = View.GONE
                            }
                            override fun onError() {
                                binding.progressBar.visibility = View.GONE
                            }

                        })
                     */






                    /*
                    Glide
                        .with(applicationContext)
                        .load("https://i.imgur.com/ytrkU2H.jpeg")
                        .placeholder(R.drawable.cargando)
                        .into(binding.imageView);
                    binding.progressBar.visibility = View.GONE
                     */

                }
            }
        }
    }
}
