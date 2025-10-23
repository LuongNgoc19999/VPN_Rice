package com.chiennc.base.app.ui.mqtt

import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import javax.net.ssl.SSLSocketFactory


class MqttHelper(context: Context) {
    var callback: (String) -> Unit = {}//"ssl://broker-xxxx.hivemq.cloud:8883"
    private val serverUri = "ssl://617512bfbea14f30b3c6373cd897e4b3.s1.eu.hivemq.cloud:8883"
    private val topic = "test/topic"

    private val username = "ngoclh"
    private val pass = "Ngoc.1234"

    private val clientId = MqttClient.generateClientId()
    private val mqttClient = MqttAndroidClient(context.applicationContext, serverUri, clientId)

    fun connect() {
        val options = MqttConnectOptions().apply {
            userName = username
            password = pass.toCharArray()
            isCleanSession = true
            socketFactory = SSLSocketFactory.getDefault()
        }

        mqttClient.connect(options, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d("ngoc", "✅ Connected to HiveMQ")
                subscribe(topic)
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.e("ngoc", "❌ Connection failed: ${exception?.message}")
            }
        })
    }

    private fun subscribe(topic: String) {
        mqttClient.subscribe(topic, 1) { _, message ->
            Log.d("ngoc", "📩 Received: ${message.toString()}")
            callback.invoke(message.toString())
        }
    }

    fun publish(msg: String) {
        mqttClient.publish(topic, MqttMessage(msg.toByteArray()))
        Log.d("ngoc", "📤 Published: $msg")
    }

    fun disconnect() {
        mqttClient.disconnect()
    }
}