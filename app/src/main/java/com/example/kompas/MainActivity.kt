package com.example.kompas

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kompas.ui.theme.KompasTheme

class MainActivity : ComponentActivity(),SensorEventListener {

    var manager: SensorManager? = null
    var degree =  mutableStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


           manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager


        setContent {


            var angle by rememberSaveable  { mutableStateOf(0f) }

            Column(modifier = Modifier.fillMaxSize().background(color = Color.LightGray),  horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text = degree.value.toString(), modifier = Modifier.padding(top = 100.dp).clickable(onClick = { angle -= 1f}), fontSize = 44.sp)


            Box (modifier = Modifier.padding(top = 80.dp)){

              Image(painter = painterResource(R.drawable.komp_out), contentDescription = "",
                  modifier = Modifier.fillMaxWidth().rotate( -degree.value.toFloat()), contentScale = ContentScale.Crop)

                Image(painter = painterResource(R.drawable.komp_in), contentDescription = "",contentScale = ContentScale.Crop,
                  modifier = Modifier.fillMaxWidth())

            }
        }}

    }

    override fun onResume() {
        super.onResume()
        manager?.registerListener(this,manager?.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        manager?.unregisterListener(this)
    }


    override fun onSensorChanged(event: SensorEvent?) {
        degree.value = event?.values?.get(0)?.toInt()!!
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}