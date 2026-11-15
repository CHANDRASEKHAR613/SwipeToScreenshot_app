package com.example.threefingerscreenshot

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.app.Activity

class MainActivity : Activity() {

    private lateinit var btnTurnOn: Button
    private lateinit var btnTurnOff: Button
    private lateinit var tvStatus: TextView
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences("swipe_screenshot_prefs", Context.MODE_PRIVATE)

        btnTurnOn = findViewById(R.id.btnTurnOn)
        btnTurnOff = findViewById(R.id.btnTurnOff)
        tvStatus = findViewById(R.id.tvStatus)

        btnTurnOn.setOnClickListener {
            if (!isAccessibilityServiceEnabled()) {
                Toast.makeText(
                    this,
                    "Please enable 'Swipe Screenshot Service' in Accessibility settings",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                startActivity(intent)
            } else {
                setSwipeScreenshotActive(true)
            }
        }

        btnTurnOff.setOnClickListener {
            setSwipeScreenshotActive(false)
        }

        updateUiState(isServiceActive())
    }

    override fun onResume() {
        super.onResume()
        // Synchronize with accessibility service status
        val accessibilityEnabled = isAccessibilityServiceEnabled()
        val preferredActive = prefs.getBoolean("is_active", false)
        val active = accessibilityEnabled && preferredActive
        setSwipeScreenshotActive(active)
    }

    private fun setSwipeScreenshotActive(active: Boolean) {
        prefs.edit().putBoolean("is_active", active).apply()
        updateUiState(active)
    }

    private fun updateUiState(active: Boolean) {
        if (active) {
            // 1st button is active, 2nd button is greyed and unclickable
            tvStatus.text = "Status: ACTIVE (Turned ON)"
            tvStatus.setTextColor(Color.parseColor("#198754")) // Green

            // Turn On button state
            btnTurnOn.isEnabled = false
            btnTurnOn.setBackgroundColor(Color.parseColor("#198754"))
            btnTurnOn.alpha = 1.0f

            // Turn Off button state
            btnTurnOff.isEnabled = true
            btnTurnOff.setBackgroundColor(Color.parseColor("#DC3545")) // Red
            btnTurnOff.alpha = 1.0f
        } else {
            tvStatus.text = "Status: INACTIVE (Turned OFF)"
            tvStatus.setTextColor(Color.parseColor("#6C757D"))

            // Turn On button state: clickable
            btnTurnOn.isEnabled = true
            btnTurnOn.setBackgroundColor(Color.parseColor("#0D6EFD")) // Blue
            btnTurnOn.alpha = 1.0f

            // Turn Off button state: greyed and unclickable
            btnTurnOff.isEnabled = false
            btnTurnOff.setBackgroundColor(Color.parseColor("#ADB5BD")) // Grey
            btnTurnOff.alpha = 0.5f
        }
    }

    private fun isServiceActive(): Boolean {
        return prefs.getBoolean("is_active", false) && isAccessibilityServiceEnabled()
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val am = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val enabledServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
        for (service in enabledServices) {
            if (service.resolveInfo.serviceInfo.packageName == packageName) {
                return true
            }
        }
        return false
    }
}
