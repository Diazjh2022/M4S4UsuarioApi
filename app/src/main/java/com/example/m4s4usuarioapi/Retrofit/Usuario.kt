package com.example.m4s4usuarioapi.Retrofit

class Usuario : ArrayList<UsuarioItem>()

data class UsuarioItem(
    val clave: String,
    val nombre: String,
    val usuario: String,

)