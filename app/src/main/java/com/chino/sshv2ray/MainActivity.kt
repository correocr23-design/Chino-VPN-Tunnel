
package com.chino.sshv2ray

import android.app.Activity
import android.os.Bundle
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.*

class MainActivity : Activity() {

    private lateinit var configInput: EditText
    private lateinit var statusText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.rgb(15, 18, 28)
        window.navigationBarColor = Color.rgb(15, 18, 28)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(24, 35, 24, 24)
            setBackgroundColor(Color.rgb(15, 18, 28))
        }

        val title = TextView(this).apply {
            text = "CHINO VPN TUNNEL"
            textSize = 26f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        val subtitle = TextView(this).apply {
            text = "Conexión segura V2Ray VMess"
            textSize = 15f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
        }

        configInput = EditText(this).apply {
            hint = "Pega aquí tu enlace VMess o JSON"
            setHintTextColor(Color.GRAY)
            setTextColor(Color.WHITE)
            textSize = 14f
            minLines = 4
            gravity = Gravity.TOP
            setPadding(14, 14, 14, 14)
            setBackgroundColor(Color.rgb(35, 40, 52))
        }

        statusText = TextView(this).apply {
            text = "Estado: DESCONECTADO"
            textSize = 17f
            setTextColor(Color.YELLOW)
            gravity = Gravity.CENTER
        }

        val importButton = Button(this).apply {
            text = "IMPORTAR CONFIGURACIÓN"
            setOnClickListener { importConfig() }
        }

        val connectButton = Button(this).apply {
            text = "CONECTAR"
            setOnClickListener { connectVpn() }
        }

        val disconnectButton = Button(this).apply {
            text = "DESCONECTAR"
            setOnClickListener {
                statusText.text = "Estado: DESCONECTADO"
            }
        }

        layout.addView(title)
        layout.addView(subtitle)
        layout.addView(space(30))
        layout.addView(configInput)
        layout.addView(space(15))
        layout.addView(importButton)
        layout.addView(space(10))
        layout.addView(connectButton)
        layout.addView(space(10))
        layout.addView(disconnectButton)
        layout.addView(space(25))
        layout.addView(statusText)

        setContentView(ScrollView(this).apply {
            addView(layout)
        })
    }

    private fun space(height: Int): android.view.View {
        return android.view.View(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                1,
                height
            )
        }
    }

    private fun importConfig() {
        val clipboard = getSystemService(
            Context.CLIPBOARD_SERVICE
        ) as ClipboardManager

        val data: ClipData? = clipboard.primaryClip

        if (data != null && data.itemCount > 0) {
            configInput.setText(
                data.getItemAt(0).coerceToText(this)
            )
        } else {
            Toast.makeText(
                this,
                "Portapapeles vacío",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun connectVpn() {
        val config = configInput.text.toString().trim()

        if (config.isEmpty()) {
            Toast.makeText(
                this,
                "Introduce una configuración VMess",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (!config.startsWith("vmess://") &&
            !config.startsWith("{")) {
            Toast.makeText(
                this,
                "Formato VMess o JSON no reconocido",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            this,
            "Configuración recibida. Falta integrar el motor Xray.",
            Toast.LENGTH_LONG
        ).show()
    }
}
