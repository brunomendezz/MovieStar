package com.prueba1.empresatelefoniaandroid

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.*
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.prueba1.empresatelefoniaandroid.databinding.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle


    @SuppressLint("ResourceType", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)


        //BARRA DE NAVEGACION
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.boton_menu_abrir,
            R.string.boton_menu_cerrar
        )
        drawer.addDrawerListener(toggle)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView = findViewById<NavigationView>(R.id.vista_nav)
        navigationView.setNavigationItemSelectedListener(this)

        actionBar?.hide();

        if (FirebaseAuth.getInstance().currentUser != null) {
            Toast.makeText(
                this,
                "Bienvenido ${FirebaseAuth.getInstance().currentUser?.displayName}",
                Toast.LENGTH_LONG
            )
                .show()
        } else {
            createSignInIntent()


        }

    }

    private fun createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.AnonymousBuilder().build()
        )

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            //   .setLogo(R.drawable.my_great_logo) // Set logo drawable
            .setTheme(com.firebase.ui.auth.R.style.Base_Theme_MaterialComponents_Light_DarkActionBar) // Set theme
            .build()
        signInLauncher.launch(signInIntent)
        // [END auth_fui_create_intent]
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }


    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult?) {

        val response = result!!.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser

            if (user != null) {
                Toast.makeText(this, "Bienvenido ${user?.displayName}", Toast.LENGTH_LONG)
                    .show()

            }
        } else {
            Toast.makeText(this, "NO SE HA PODIDO INICIAR SESION", Toast.LENGTH_LONG)
                .show()

        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.validarEmail -> {

                val user = FirebaseAuth.getInstance().currentUser
                user?.sendEmailVerification()
                if (user != null) {
                    if(user.isEmailVerified){
                        Toast.makeText(this, "Tu email ya ha sido verificado", Toast.LENGTH_LONG)
                            .show()
                    }else{   Toast.makeText(
                        this,
                        "Te hemos enviado un correo a ${user?.email}",
                        Toast.LENGTH_LONG
                    )
                        .show()

                    }
                }else{
                    Toast.makeText(this, "Debe iniciar sesion con algun email para poder validarlo", Toast.LENGTH_LONG)
                        .show()
                }




            }
            R.id.validarTelefono -> {
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG)
                    .show()
            }

            R.id.borrar_cuenta -> {
                AuthUI.getInstance()
                    .delete(this)
                createSignInIntent()
                Toast.makeText(this, "Se ha eliminado tu cuenta", Toast.LENGTH_LONG)
                    .show()
            }

            R.id.cerrarSesion -> {
                FirebaseAuth.getInstance().signOut()
                createSignInIntent()
            }

        }

        drawer.closeDrawer(GravityCompat.START)
        return true

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)

    }


}





