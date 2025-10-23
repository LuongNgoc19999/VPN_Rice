package com.chiennc.base.app.ui.mqtt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chiennc.base.R
import com.chiennc.base.databinding.ActivityMqttBinding

class MqttActivity : AppCompatActivity() {
    private lateinit var mqttHelper: MqttHelper
    lateinit var binding: ActivityMqttBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mqtt)
        mqttHelper = MqttHelper(this).apply {
            callback = {
                binding.tv.text = it
            }
        }
        mqttHelper.connect()
        binding.btn1.setOnClickListener {
            mqttHelper.publish("hello 1")
        }
        binding.btn2.setOnClickListener {
            mqttHelper.publish("ngoclh 2")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mqttHelper.disconnect()
    }
}