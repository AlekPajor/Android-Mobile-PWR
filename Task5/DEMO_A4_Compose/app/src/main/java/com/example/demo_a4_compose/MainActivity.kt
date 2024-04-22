package com.example.demo_a4_compose

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Paint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demo_a4_compose.ui.theme.DEMO_A4_ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            DemoA4Content()

            //  Lub pełniejsza wersja uruchomienia tej samej zawartości ( z dodaniem stylu/Theme )
            //  DEMO_A4_ComposeTheme {
            //      // A surface container using the 'background' color from the theme
            //      Surface(
            //          modifier = Modifier.fillMaxSize(),
            //          color = MaterialTheme.colorScheme.background
            //      ) {
            //          DemoA4Content()       // <<<<<<<<<<<
            //      }
            //  }

        }
    }
}


@Composable
fun DemoA4Content() {  //funkcja odpowiadająca za cały ekran (zawierający trzy różne "Dema"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        Text(
            text = "DEMO A4 Compose",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Divider(modifier = Modifier.height(30.dp))
        DemoPrzelaczanePrzyciski()
        Divider(modifier = Modifier.height(20.dp))
        DemoNapiwek()
        Divider(modifier = Modifier.height(20.dp))
        DemoBatteryBroadcast()  // ToDo
    }

}


@Composable
fun DemoPrzelaczanePrzyciski() {
    // zmienne modelu danych -> stanu aplikacji
    var stanPrzyciskow by remember { mutableStateOf(1) }  // 1 - aktywny jest pierwszy przycisk
                                                                    // 2 - aktywny jest drugi, itd
    // elementy interfejsu użytkownika
    Column(
        modifier = Modifier.fillMaxWidth(1f)
    ) {
        Text(
            text = "1) DemoPrzyciski",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(1f),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            // PIERWSZY przycisk, którego kliknięcie aktywuje DRUGI przycisk
            Button(
                onClick = { stanPrzyciskow = if (stanPrzyciskow == 1) 2 else 1 },
                enabled = ( stanPrzyciskow == 1 )
            ) {
                Text(text = "PIERWSZY")
            }

            Spacer(modifier = Modifier.height(30.dp))

            // DRUGI przycisk, którego kliknięcie aktywuje PIERWSZY przycisk
            Button(
                onClick = { stanPrzyciskow = if (stanPrzyciskow == 1) 2 else 1 },
                enabled = ( stanPrzyciskow == 2 )
            ) {
                Text(text = "DRUGI")
            }
        }
    }
}


@Composable
fun DemoNapiwek() {
    // zmienne modelu danych -> stanu aplikacji
    var tekstKosztuZamowienia by rememberSaveable { mutableStateOf("") }
    var tekstKwotyNapiwku by rememberSaveable { mutableStateOf("---") }

    // elementy interfejsu użytkownika
    Column(
        modifier = Modifier.fillMaxWidth(1f)
    ) {
        Text(
            text = "2) DemoNapiwek",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = tekstKosztuZamowienia,
            onValueChange = {
                                tekstKosztuZamowienia = it
                                // jeżeli obliczenia nie są skomplikowane,
                                // to można już tutaj policzyć napiwek
                                // i ustawić zawartość wynikowej zmiennej "tekstKwotyNapiwku"
                                // (wtedy przycisk "POLICZ NAPIWEK" można pominąć)
                            },
            placeholder = {Text("Tutaj wpisz koszt zamówienia")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text( text = "Domyślny napiwek ma teraz stałą wartość 5%",
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            fontStyle = FontStyle.Italic
        )
        Spacer(modifier = Modifier.height(20.dp))

        val context = LocalContext.current  //kontekst do wyswietlenia powiadomienia "Toast"
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                val kosztZamowienia = tekstKosztuZamowienia.toDoubleOrNull()
                if( kosztZamowienia!= null ) {
                    val wartoscNapiwku = kosztZamowienia * 0.05
                    tekstKwotyNapiwku = String.format("%.2f zł", wartoscNapiwku)
                } else {
                    tekstKwotyNapiwku = "---"
                    Toast.makeText(context,"Niepoprawny koszt zamówienia",Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "POLICZ NAPIWEK")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Napiwek: " + tekstKwotyNapiwku,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}


@Composable
fun DemoBatteryBroadcast() {

    var isBatteryGood by remember {
        mutableStateOf(true)
    }

    val context = LocalContext.current

    DisposableEffect(context) {
        val intentFilter: IntentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(Intent.ACTION_BATTERY_OKAY)
        }
        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.action?.let { action ->
                    val message = when (action) {
                        Intent.ACTION_BATTERY_LOW -> false
                        Intent.ACTION_BATTERY_OKAY -> true
                        else -> throw IllegalArgumentException("Something went wrong and action is: $action")
                    }
                isBatteryGood = message
                }
            }
        }

        context.registerReceiver(broadcastReceiver, intentFilter)

        onDispose {
            context.unregisterReceiver(broadcastReceiver)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(1f)
    ) {
        Text(
            text = "3) DemoBatteryBroadcast",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(20.dp))
        if(isBatteryGood) {
            Text(
                text = "Battery is OK :)",
                fontSize = 40.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Green
            )
        } else {
            Text(
                text = "Battery is LOW :C",
                fontSize = 40.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Red
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DemoA4ContentPreview() {
    DemoA4Content()
}