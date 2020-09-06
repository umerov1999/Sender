package com.f0x1d.sender

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private var adress: TextInputEditText? = null
    private var elektro1: TextInputEditText? = null
    private var elektro2: TextInputEditText? = null
    private var nomer: TextInputEditText? = null
    private var ot_kogo: TextInputEditText? = null
    private var save_adress: MaterialCheckBox? = null
    private var save_nomer: MaterialCheckBox? = null
    private var save_ot_kogo: MaterialCheckBox? = null
    private var water_cold: TextInputEditText? = null
    private var water_hot: TextInputEditText? = null
    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById<View>(R.id.toolbar) as MaterialToolbar)
        water_cold = findViewById<View>(R.id.water) as TextInputEditText
        water_hot = findViewById<View>(R.id.water_hot) as TextInputEditText
        elektro1 = findViewById<View>(R.id.elekto_1) as TextInputEditText
        elektro2 = findViewById<View>(R.id.elekto_2) as TextInputEditText
        ot_kogo = findViewById<View>(R.id.schot) as TextInputEditText
        ot_kogo!!.setText(PreferenceManager.getDefaultSharedPreferences(applicationContext).getString("ot_kogo", ""))
        adress = findViewById<View>(R.id.adress) as TextInputEditText
        adress!!.setText(PreferenceManager.getDefaultSharedPreferences(applicationContext).getString("adress", ""))
        nomer = findViewById<View>(R.id.nomer) as TextInputEditText
        nomer!!.setText(PreferenceManager.getDefaultSharedPreferences(applicationContext).getString("nomer", ""))
        save_ot_kogo = findViewById<View>(R.id.save_schot) as MaterialCheckBox
        save_adress = findViewById<View>(R.id.save_adress) as MaterialCheckBox
        save_nomer = findViewById<View>(R.id.save_nomer) as MaterialCheckBox
        if (PreferenceManager.getDefaultSharedPreferences(applicationContext).getString("ot_kogo", "") != "") {
            save_ot_kogo!!.text = "Обновить"
        }
        if (PreferenceManager.getDefaultSharedPreferences(applicationContext).getString("adress", "") != "") {
            save_adress!!.text = "Обновить"
        }
        if (PreferenceManager.getDefaultSharedPreferences(applicationContext).getString("nomer", "") != "") {
            save_nomer!!.text = "Обновить"
        }
        val floatingActionButton = findViewById<View>(R.id.fab2) as FloatingActionButton
        floatingActionButton.setImageBitmap(textAsBitmap("SMS", 30.0f, -1))
        floatingActionButton.setOnClickListener {
            if (save_nomer!!.isChecked) {
                PreferenceManager.getDefaultSharedPreferences(applicationContext).edit().putString("nomer", nomer!!.text.toString()).apply()
            }
            if (save_ot_kogo!!.isChecked) {
                PreferenceManager.getDefaultSharedPreferences(applicationContext).edit().putString("ot_kogo", ot_kogo!!.text.toString()).apply()
            }
            if (save_adress!!.isChecked) {
                PreferenceManager.getDefaultSharedPreferences(applicationContext).edit().putString("adress", adress!!.text.toString()).apply()
            }
            val intent = Intent("android.intent.action.VIEW", Uri.parse("sms:" + nomer!!.text.toString()))
            intent.putExtra("sms_body", ot_kogo!!.text.toString() + " " + water_cold!!.text.toString() + " " + water_hot!!.text.toString() + " " + elektro1!!.text.toString() + " " + elektro2!!.text.toString() + " " + adress!!.text.toString())
            startActivity(intent)
        }
        /*
        (findViewById<View>(R.id.fab) as FloatingActionButton).setOnClickListener {
            val intent = Intent("android.intent.action.SEND")
            intent.type = "message/rfc822"
            intent.putExtra("android.intent.extra.EMAIL", arrayOf(kuda!!.text.toString()))
            intent.putExtra("android.intent.extra.TEXT", ot_kogo!!.text.toString() + " " + water_cold!!.text.toString() + " " + water_hot!!.text.toString() + " " + elektro1!!.text.toString() + " " + elektro2!!.text.toString() + " " + adress!!.text.toString())
            startActivity(Intent.createChooser(intent, "Send mail..."))
        }
         */
    }

    companion object {
        fun textAsBitmap(str: String?, f: Float, i: Int): Bitmap {
            val paint = Paint(1)
            paint.textSize = f
            paint.color = i
            paint.textAlign = Paint.Align.LEFT
            val f2 = -paint.ascent()
            val createBitmap = Bitmap.createBitmap((paint.measureText(str) + 0.0f).toInt(), (paint.descent() + f2 + 0.0f).toInt(), Bitmap.Config.ARGB_8888)
            Canvas(createBitmap).drawText(str!!, 0.0f, f2, paint)
            return createBitmap
        }
    }
}