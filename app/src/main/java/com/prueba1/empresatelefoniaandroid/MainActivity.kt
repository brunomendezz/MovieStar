package com.prueba1.empresatelefoniaandroid

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.prueba1.empresatelefoniaandroid.databinding.ActivityMainBinding
import com.prueba1.empresatelefoniaandroid.databinding.ClienteEncontradoBinding
import com.prueba1.empresatelefoniaandroid.databinding.FragmentAgregarClienteBinding
import com.prueba1.empresatelefoniaandroid.databinding.FragmentBuscarClienteBinding


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mFirebaseCrashlyticsRegistrar: FirebaseCrashlytics
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle


    @SuppressLint("ResourceType", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.layout.drawle_layoutmenu)
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

        val navigationView= findViewById<NavigationView>(R.id.vista_nav)
        navigationView.setNavigationItemSelectedListener(this)


// OPCION AGREGAR CLIENTE

        mainBinding.agregarCliente.setOnClickListener {
            val addClientBinding = FragmentAgregarClienteBinding.inflate(layoutInflater)
            setContentView(addClientBinding.root)
            mFirebaseCrashlyticsRegistrar.log("agregar cliente")
//BOTON CANCELAR DE AGREGAR CLIENTE

            addClientBinding.botoncancelar1.setOnClickListener {
                setContentView(mainBinding.root)
            }
//BOTON REGISTRAR DE AGREGAR CLIENTE

            addClientBinding.botonregistrar1.setOnClickListener {

                val chequeoDeNoNumeros = { nombreOApellido: String ->
                    nombreOApellido.forEach {
                        if (it.isDigit())
                            throw java.lang.RuntimeException("NO INGRESAR NUMEROS ,")
                    }
                }

                try {
                    val nombre = addClientBinding.nombre.text.toString()
                    chequeoDeNoNumeros(nombre)
                    //PARA QUE NO INGRESEN "ASD" POR EJEMPLO
                    check(nombre.length >= 2)

                    val apellido = addClientBinding.apellido.text.toString()
                    chequeoDeNoNumeros(apellido)
                    check(apellido.length >= 2)

                    if (ClientManager().darDeAltaCliente(nombre, apellido)) {
                        Toast.makeText(
                            this,
                            "El cliente se ha agregado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        setContentView(mainBinding.root)
                    } else {
                        Toast.makeText(
                            this,
                            "No se ha podido registrar el cliente, intente de nuevo",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "INGRESE NOMBRE O APELLIDO VALIDO", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }

        //OPCION CONSULTAR COSTO DE UN CLIENTE POR ID

        mainBinding.consultarCostoDeUnCliente.setOnClickListener {
            val consutarCostoClienteBinding = FragmentBuscarClienteBinding.inflate(layoutInflater)
            setContentView(consutarCostoClienteBinding.root)

            initRecyclerView()

            val nroDeClienteABuscar = consutarCostoClienteBinding.idabuscar

            val chequeoDeQueSeaNumero = { nroClienteABuscar: String ->
                nroClienteABuscar.forEach {
                    if (!it.isDigit())

                        throw java.lang.RuntimeException("INGRESE ID VALIDO")
                }
            }


            //BOTON CANCELAR
            consutarCostoClienteBinding.botoncancelar2.setOnClickListener {
                setContentView(mainBinding.root)
            }

            //BOTON BUSCAR
            consutarCostoClienteBinding.botonbuscar2.setOnClickListener {
                try {
                    val listaDeClientes = ClientManager().obtenerListaDeClientes()
                    chequeoDeQueSeaNumero(nroDeClienteABuscar.text.toString())
                    if (nroDeClienteABuscar.text.toString()
                            .toInt() !in 0..listaDeClientes.size || nroDeClienteABuscar.text.equals(
                            null
                        )
                    ) {
                        Toast.makeText(
                            this,
                            "ESE ID DE CLIENTE NO EXISTE",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        setContentView(R.layout.cliente_encontrado)
                        val costotalDelCliente =
                            findViewById<TextView>(R.id.costotaldelcliente)
                        costotalDelCliente.text = "EL COSTO TOTAL DEL CLIENTE ES:$ ${
                            CallsManager().costoDeLlamada(
                                nroDeClienteABuscar.text.toString().toInt()
                            )
                        }"
                        val botonVolver = findViewById<Button>(R.id.botonVolver)
                        botonVolver.setOnClickListener {
                            setContentView(mainBinding.root)
                        }
                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "INGRESE UN ID VALIDO", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }


        //OPCION OBTENER COSTO TOTAL DE TODOS LOS CLIENTES
        mainBinding.consultarCostoTotal.setOnClickListener {
            val costoTotalBinding = ClienteEncontradoBinding.inflate(layoutInflater)
            setContentView(costoTotalBinding.root)
            costoTotalBinding.costotaldelcliente.text =
                "EL COSTO TOTAL DE LOS CLIENTES ES: $${CallsManager().costoTotalDeLosClientes()}"
            costoTotalBinding.botonVolver.setOnClickListener {
                setContentView(mainBinding.root)
            }
        }

        //OPCION CERRAR SESION

        mainBinding.botoncerrarsesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            createSignInIntent()
        }

        //OPCION SALIR


        mainBinding.botonsalir.setOnClickListener {
            finish()
            android.os.Process.killProcess(android.os.Process.myPid())
        }

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
            createSignInIntent()
        }


    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rvListaDeClientes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter(ClientManager().obtenerListaDeClientes())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.validarEmail -> {
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG)
                    .show()


            }
            R.id.validarTelefono -> {      Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG)
                .show()}

            R.id.borrar_cuenta -> {      Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG)
                .show()}

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





