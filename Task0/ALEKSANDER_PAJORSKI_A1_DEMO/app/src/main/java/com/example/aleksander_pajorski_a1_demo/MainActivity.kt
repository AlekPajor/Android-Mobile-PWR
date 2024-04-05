package com.example.aleksander_pajorski_a1_demo

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.RelativeLayout
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var button1: Button
    private lateinit var button2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)

        button1.setOnClickListener {
            button1.visibility = View.INVISIBLE
            button2.visibility = View.VISIBLE

            setRandomPosition(button2)
        }

        button2.setOnClickListener {
            button2.visibility = View.INVISIBLE
            button1.visibility = View.VISIBLE

            setRandomPosition(button1)
        }
    }

    private fun setRandomPosition(button: Button) {
        val layoutParams = button.layoutParams as RelativeLayout.LayoutParams
        layoutParams.leftMargin = getRandomX()
        layoutParams.topMargin = getRandomY()
        button.layoutParams = layoutParams
    }

    private fun getRandomX(): Int {
        val screenWidth = resources.displayMetrics.widthPixels
        return Random.nextInt(screenWidth - button1.width)
    }

    private fun getRandomY(): Int {
        val screenHeight = resources.displayMetrics.heightPixels
        return Random.nextInt(screenHeight - button1.height)
    }
}