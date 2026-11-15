package com.example.threefingerscreenshot

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.os.Build
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class ScreenshotAccessibilityService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        // Service connected and ready to trigger system actions
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Accessibility events
    }

    override fun onInterrupt() {
    }

    fun triggerScreenshot(): Boolean {
        val prefs = getSharedPreferences("swipe_screenshot_prefs", Context.MODE_PRIVATE)
        val isActive = prefs.getBoolean("is_active", false)
        if (!isActive) return false

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // Native global screenshot action introduced in Android 9+ (API 28)
            performGlobalAction(GLOBAL_ACTION_TAKE_SCREENSHOT)
        } else {
            Toast.makeText(this, "Screenshot requires Android 9+", Toast.LENGTH_SHORT).show()
            false
        }
    }
}
