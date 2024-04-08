package com.example.intentsapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var openSettingsButton: Button
    private lateinit var openGoogleMapsButton: Button
    private lateinit var takePhotoButton: Button
    private lateinit var photoImageView: ImageView

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap: Bitmap? = data?.getParcelableExtra("data")
            photoImageView.setImageBitmap(imageBitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        openSettingsButton = findViewById(R.id.openSettingsButton)
        openGoogleMapsButton = findViewById(R.id.openGoogleMapsButton)
        takePhotoButton = findViewById(R.id.takePhotoButton)
        photoImageView = findViewById(R.id.photoImageView)

        openSettingsButton.setOnClickListener {
            val intent = Intent(Settings.ACTION_SETTINGS)
            try {
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                println(ex.message)
            }
        }

        openGoogleMapsButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("geo:52.23,21.01?z=11")
            }
            try {
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                println(ex.message)
            }
        }

        takePhotoButton.setOnClickListener {
            val Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(Intent, Companion.REQUEST_IMAGE_CAPTURE)
            } catch (ex: ActivityNotFoundException) {
                println(ex.message)
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }
}