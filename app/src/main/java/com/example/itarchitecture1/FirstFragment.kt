package com.example.itarchitecture1

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.itarchitecture1.databinding.FragmentFirstBinding
import java.lang.Error

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var suiseiService: AidlInterface? = null
    private var isBound: Boolean = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName?, service: IBinder?) {
            suiseiService = AidlInterface.Stub.asInterface(service)
            Log.d("suisei in first fragment", "service suisei service is connected")
            getSuiseiWordByServiceAndSetTextView(suiseiService)
            isBound = true
        }

        override fun onServiceDisconnected(componentName: ComponentName?) {
            Toast.makeText(context, "service suisei service is disconnected", Toast.LENGTH_SHORT).show()
            Log.d("suisei", "disconnected")
        }
    }

    private fun getSuiseiWordByServiceAndSetTextView(suiseiService: AidlInterface?){
        try {
            val suiseiWord = suiseiService?.getSuiseiWords()
            Toast.makeText(context, suiseiWord, Toast.LENGTH_SHORT).show()
            if (view == null) {
                Log.d("suisei in first fragment", "view is null")
                return
            }
            changeSuiseiText(requireView(),  suiseiWord ?: "今日も可愛い！")
        } catch (e: Error) {
            Log.d("suiseiService error", e.toString())
        }
    }

    private fun bindListenner(){
        Log.d("suisei", "listener activated")
        if (!isBound) {
            val intent = Intent(context, SuiseiAidlService::class.java)
            intent.action = AidlInterface::class.java.name
            if (context == null) {
                Toast.makeText(context, "in fragment, activity is null", Toast.LENGTH_SHORT).show()
            }
            context?.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        } else {
            getSuiseiWordByServiceAndSetTextView(suiseiService)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener{
            bindListenner()
        }
//        {
//            bindListenner
//            changeSuiseiText(view, "今日も小さい〜〜〜！\n「な　ん　だ　っ　て　？」")
////            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun changeSuiseiText(view: View, text: String){
        var suiseiMsgView = view.findViewById<TextView>(R.id.suisei_word_text)
        suiseiMsgView.text = text
    }
}