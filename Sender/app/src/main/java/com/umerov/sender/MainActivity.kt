package com.umerov.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private var adress: TextInputEditText? = null
    private var elektro1: TextInputEditText? = null
    private var elektro2: TextInputEditText? = null
    private var nomer: TextInputEditText? = null
    private var ot_kogo: TextInputEditText? = null
    private var water_cold: TextInputEditText? = null
    private var water_hot: TextInputEditText? = null
    private var saveButton: MaterialButton? = null
    private var profileTab: TabLayout? = null
    private var profileIndex = 0
    private val profileCount = 2
    private fun resolveData() {
        ot_kogo?.setText(
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
                .getString("ot_kogo_$profileIndex", "")
        )
        adress?.setText(
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
                .getString("adress_$profileIndex", "")
        )
        nomer?.setText(
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
                .getString("nomer_$profileIndex", "")
        )
    }

    private fun getName(index: Int): String {
        val str = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            .getString("adress_$index", (index + 1).toString())
        if (str == null || str.isEmpty()) {
            return (index + 1).toString()
        }
        return str
    }

    private fun resolveIndex() {
        profileIndex =
            PreferenceManager.getDefaultSharedPreferences(applicationContext).getInt("profile", 0)
    }

    private fun saveIndex() {
        PreferenceManager.getDefaultSharedPreferences(applicationContext).edit()
            .putInt("profile", profileIndex).apply()
    }

    private fun saveData() {
        PreferenceManager.getDefaultSharedPreferences(applicationContext).edit()
            .putString("nomer_$profileIndex", nomer!!.text.toString()).apply()
        PreferenceManager.getDefaultSharedPreferences(applicationContext).edit()
            .putString("ot_kogo_$profileIndex", ot_kogo!!.text.toString()).apply()
        PreferenceManager.getDefaultSharedPreferences(applicationContext).edit()
            .putString("adress_$profileIndex", adress!!.text.toString()).apply()
    }

    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById<View>(R.id.toolbar) as MaterialToolbar)
        water_cold = findViewById<View>(R.id.water) as TextInputEditText
        water_hot = findViewById<View>(R.id.water_hot) as TextInputEditText
        elektro1 = findViewById<View>(R.id.elekto_1) as TextInputEditText
        elektro2 = findViewById<View>(R.id.elekto_2) as TextInputEditText
        ot_kogo = findViewById<View>(R.id.schot) as TextInputEditText
        adress = findViewById<View>(R.id.adress) as TextInputEditText
        nomer = findViewById<View>(R.id.nomer) as TextInputEditText
        saveButton = findViewById<View>(R.id.save_button) as MaterialButton
        profileTab = findViewById<View>(R.id.profile) as TabLayout

        resolveIndex()
        for (i: Int in 0 until profileCount) {
            profileTab?.addTab(profileTab!!.newTab().setText(getName(i)), i, i == profileIndex)
        }
        profileTab?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                profileIndex = profileTab!!.selectedTabPosition
                saveIndex()
                resolveData()
            }
        })
        resolveData()
        saveButton?.setOnClickListener {
            saveData()
            profileTab?.getTabAt(profileIndex)?.text = getName(profileIndex)
        }

        val floatingActionButton = findViewById<View>(R.id.fab2) as FloatingActionButton
        floatingActionButton.setOnClickListener {
            val intent =
                Intent("android.intent.action.VIEW", Uri.parse("sms:" + nomer!!.text.toString()))
            intent.putExtra(
                "sms_body",
                ot_kogo!!.text.toString() + " " + water_cold!!.text.toString() + " " + water_hot!!.text.toString() + " " + elektro1!!.text.toString() + " " + elektro2!!.text.toString() + " " + adress!!.text.toString()
            )
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
}