package com.example.proyectoandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proyectoandroid.fragments.FragmentListaRecordatorios
import com.example.proyectoandroid.fragments.FragmentMenu
import com.example.proyectoandroid.fragments.FragmentRegistroRecordatorios
import com.example.proyectoandroid.fragments.OnFragmentActionListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Recordatorios : AppCompatActivity() , OnFragmentActionListener{
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recordatorios)
        auth = Firebase.auth
        cargarFragments()
    }

    private fun cargarFragments() {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.setReorderingAllowed(true)
        transaction.add(R.id.fg_menu, FragmentMenu())
        transaction.add(R.id.fg_listaRecordatorios, FragmentListaRecordatorios())


        supportFragmentManager.findFragmentById(R.id.fg_listaRecordatorios)?.let { transaction.show(it) }
        supportFragmentManager.findFragmentById(R.id.fg_registro_recordatorios)?.let { transaction.hide(it) }
        transaction.commit()
    }

    override fun mostrarMensaje(msj: String) {
        Toast.makeText(this,msj, Toast.LENGTH_SHORT).show()
    }

    //Muestra el fragmento en el que registramos los recordatorios de los conciertos
    override fun cargarFragmentRegistro(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()


        supportFragmentManager.findFragmentById(R.id.fg_listaRecordatorios)?.let { transaction.hide(it) }
        supportFragmentManager.findFragmentById(R.id.fg_registro_recordatorios)?.let { transaction.show(it) }

        transaction.commit()
    }

    //Muestra la lista de los conciertos que hemos guardado ocultando el
    // fragment de los registros de los recordatorios de los conciertos
    override fun cargarFragmentLista(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()


        supportFragmentManager.findFragmentById(R.id.fg_registro_recordatorios)?.let { transaction.hide(it) }
        supportFragmentManager.findFragmentById(R.id.fg_listaRecordatorios)?.let { transaction.show(it) }
        transaction.commit()
    }

    override fun irActivityPrincipal(intent: Intent) {
        startActivity(Intent(this,Principal::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_opciones, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_sesion ->{
                cerrarSesion()
            }
            R.id.item_perfil ->{
                irActivityPerfil()
            }
            R.id.item_salir ->{
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun cerrarSesion() {
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun irActivityPerfil() {
        startActivity(Intent(this,Perfil_Usuario::class.java))
    }
}