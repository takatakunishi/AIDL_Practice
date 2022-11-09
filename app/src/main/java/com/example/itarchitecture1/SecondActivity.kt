package com.example.itarchitecture1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val startServiceBtn = findViewById<Button>(R.id.start_suisei_service)
        val stopServiceBtn = findViewById<Button>(R.id.stop_suisei_service)
        startServiceBtn.setOnClickListener{
            val intent = Intent(application, SuiseiAidlService::class.java)
            startService(intent)
        }
        stopServiceBtn.setOnClickListener{
            Log.d("second activity", "stop btn pushed")
            val intent = Intent(application, SuiseiAidlService::class.java)
            stopService(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("second activity", "activity destroyed")
    }
}