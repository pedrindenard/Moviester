package com.app.moviester.ui.activity

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.app.moviester.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_drawer.*
import java.io.*
import java.util.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toggle: ActionBarDrawerToggle
    private val circleImageView: CircleImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationView: NavigationView = findViewById(R.id.navView)
        navigationView.setNavigationItemSelectedListener(this)

        setupNavController()
        setupActionBarDrawer()
        loadImageFromInternalStorage()
    }

    // Configura o Menu Navigation
    private fun setupNavController() {
        val view: BottomNavigationView = findViewById(R.id.navigation_view)
        val hostFragment =
            supportFragmentManager.findFragmentById(R.id.navigation_host_fragment) as NavHostFragment
        val controller = hostFragment.navController
        view.setupWithNavController(controller)
    }

    // Configura o Menu lateral
    private fun setupActionBarDrawer() {
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Quando clicka no botão voltar do Android, ele fecha o Menu lateral e não o App
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Funções exercidas dos botões
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_camera -> selectImage(this@MainActivity)
            R.id.nav_item_gallery -> Toast.makeText(
                this,
                "Botão inutil ainda!!",
                Toast.LENGTH_SHORT
            ).show()
            R.id.nav_item_config -> Toast.makeText(this, "Botão inutil ainda!!", Toast.LENGTH_SHORT)
                .show()
        }
        drawerLayout.closeDrawer(GravityCompat.START) // Fecha o Menu quano clicka em algum botão
        return true
    }

    // Dialog pergunta se quer abrir a camera ou a galeria
    private fun selectImage(context: Context) {
        val options = arrayOf<CharSequence>("Tirar uma foto", "Escolher uma foto", "Cancelar")
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Selecione a sua foto de perfil")
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Tirar uma foto" -> {
                    requestCamera()
                }
                options[item] == "Escolher uma foto" -> {
                    requestGallery()
                }
                options[item] == "Cancelar" -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    // Requesita a permissão de usuabilidade da câmera
    private fun requestCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED
            ) {
                val permission =
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE_GALLERY)
            } else {
                instanceCamera()
            }
        } else {
            instanceCamera()
        }
    }

    // Requesita a permissão de usuabilidade da galeria
    private fun requestGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED
            ) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE_GALLERY)
            } else {
                instanceGallery()
            }
        } else {
            instanceGallery()

        }
    }

    // Permissão concedida? Instância câmera ou galeria, caso contrario, permissão negada
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE_GALLERY -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    instanceGallery()
                } else {
                    Toast.makeText(this, "Permissão negada!", Toast.LENGTH_SHORT).show()
                }
            }
            PERMISSION_CODE_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    instanceCamera()
                } else {
                    Toast.makeText(this, "Permissão negada!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Instancia a câmera
    private fun instanceCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, IMAGE_CAPTURE_CODE)
    }

    // Instancia a galeria
    private fun instanceGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, IMAGE_PICK_CODE)
    }

    // Joga imagem na ImageView do menu
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE) {
            if (data != null) {
                val contentURI = data.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    saveToInternalStorage(bitmap)
                    Toast.makeText(applicationContext, "Imagem salvada!", Toast.LENGTH_SHORT).show()
                    circleImageView?.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(applicationContext, "Erro ao salvar imagem!", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (requestCode == IMAGE_CAPTURE_CODE) {
            val thumbnail = data!!.extras!!["data"] as Bitmap?
            circleImageView?.setImageBitmap(thumbnail)
            if (thumbnail != null) {
                saveToInternalStorage(thumbnail)
            }
            Toast.makeText(applicationContext, "Imagem salvada!", Toast.LENGTH_SHORT).show()
        }
    }

    // Salva a imagem no armazenamento interno
    private fun saveToInternalStorage(bitmapImage: Bitmap): String {
        val cw = ContextWrapper(applicationContext)
        val directory = cw.getDir("imageDir", MODE_PRIVATE)
        val myPath = File(directory, "profileMoviester.jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(myPath)
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        readImageFromInternalStorage(directory.absolutePath)
        return directory.absolutePath
    }

    // Lê a imagem salva
    private fun readImageFromInternalStorage(path: String) {
        try {
            val file = File(path, "profileMoviester.jpg")
            val bitmap = BitmapFactory.decodeStream(FileInputStream(file))
            val image: ImageView = findViewById<View>(R.id.imageView_profile) as ImageView
            image.setImageBitmap(bitmap)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    // Carrega a imagem salva
    private fun loadImageFromInternalStorage() {
        try {
            val bitmap = BitmapFactory.decodeFile("data/data/com.app.moviester/app_imageDir/profileMoviester.jpg")
            val image: ImageView? = findViewById<View>(R.id.imageView_profile) as ImageView?
            image?.setImageBitmap(bitmap)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        private const val IMAGE_CAPTURE_CODE = 2000
        private const val PERMISSION_CODE_GALLERY = 1001
        private const val PERMISSION_CODE_CAMERA = 2001
    }
}