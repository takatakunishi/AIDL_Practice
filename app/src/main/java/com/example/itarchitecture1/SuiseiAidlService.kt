package com.example.itarchitecture1

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class SuiseiAidlService : Service() {
    private var registeredSuiseiText: Array<String> = arrayOf(
        "しょたまちさん？\n「だから！\nショタは好きじゃ、\nないんだって！」",
        "彗星の如く現れたスターの原石！\nアイドルVTuberの星街すいせいです！"
    )

    private val binder = object : AidlInterface.Stub() {
        override fun getSuiseiWords(): String {
            return registeredSuiseiText.getOrElse(getRand()){"すいちゃんは〜〜？"}
        }

        override fun registerSuiseiWords(registerWord: String?) {
            if (registerWord == null) return
            registeredSuiseiText+=registerWord
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "suisei service starting", Toast.LENGTH_SHORT).show()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private fun getRand() : Int {
        return (0 until registeredSuiseiText.size).random()
    }

}