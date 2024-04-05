package com.example.tip_calculator

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RatingBar
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var totalPriceInput: EditText
    private lateinit var prc5Button: ToggleButton
    private lateinit var prc10Button: ToggleButton
    private lateinit var prc15Button: ToggleButton
    private lateinit var ratingBar: RatingBar
    private lateinit var radioButtonTerrible: RadioButton
    private lateinit var radioButtonOK: RadioButton
    private lateinit var radioButtonExcellent: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        totalPriceInput = findViewById(R.id.totalPriceInput)
        ratingBar = findViewById(R.id.ratingBar)
        radioButtonTerrible = findViewById(R.id.radioButtonTerrible)
        radioButtonOK = findViewById(R.id.radioButtonOK)
        radioButtonExcellent = findViewById(R.id.radioButtonExcellent)
        prc5Button = findViewById(R.id.prc5Button)
        prc5Button.setBackgroundColor(Color.TRANSPARENT)
        prc10Button = findViewById(R.id.prc10Button)
        prc10Button.setBackgroundColor(Color.TRANSPARENT)
        prc15Button = findViewById(R.id.prc15Button)
        prc15Button.setBackgroundColor(Color.TRANSPARENT)

        button.setOnClickListener {
            val totalAmount = totalPriceInput.text.toString().toDoubleOrNull() ?: 0.0
            var tipPercentage = 0.0

            if (prc5Button.isChecked) {
                tipPercentage = 5.0
            } else if (prc10Button.isChecked) {
                tipPercentage = 10.0
            } else if (prc15Button.isChecked) {
                tipPercentage = 15.0
            }

            tipPercentage += ratingBar.rating

            if (radioButtonTerrible.isChecked) {
                tipPercentage -= 5.0
            } else if (radioButtonExcellent.isChecked) {
                tipPercentage += 5.0
            }

            val finalTipAmount = totalAmount * (tipPercentage / 100)
            val decimalFormat = DecimalFormat("#.00")
            val formattedFinalTipAmount = decimalFormat.format(finalTipAmount)

            println(formattedFinalTipAmount)

            val snackbar = Snackbar.make(
                findViewById(android.R.id.content),
                "Your final tip amount: $$formattedFinalTipAmount",
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        }

        radioButtonTerrible.setOnClickListener {
            radioButtonOK.isChecked = false
            radioButtonExcellent.isChecked = false
        }

        radioButtonOK.setOnClickListener {
            radioButtonTerrible.isChecked = false
            radioButtonExcellent.isChecked = false
        }

        radioButtonExcellent.setOnClickListener {
            radioButtonOK.isChecked = false
            radioButtonTerrible.isChecked = false
        }

        prc5Button.setOnClickListener {
            prc10Button.isChecked = false
            prc10Button.setBackgroundColor(Color.TRANSPARENT)
            prc15Button.isChecked = false
            prc15Button.setBackgroundColor(Color.TRANSPARENT)

            prc5Button.setBackgroundColor(Color.parseColor("#90EE90"))
        }

        prc10Button.setOnClickListener {
            prc5Button.isChecked = false
            prc5Button.setBackgroundColor(Color.TRANSPARENT)
            prc15Button.isChecked = false
            prc15Button.setBackgroundColor(Color.TRANSPARENT)

            prc10Button.setBackgroundColor(Color.parseColor("#90EE90"))
        }

        prc15Button.setOnClickListener {
            prc5Button.isChecked = false
            prc5Button.setBackgroundColor(Color.TRANSPARENT)
            prc10Button.isChecked = false
            prc10Button.setBackgroundColor(Color.TRANSPARENT)

            prc15Button.setBackgroundColor(Color.parseColor("#90EE90"))
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}