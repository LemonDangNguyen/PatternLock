package com.example.patternlock.model

import android.animation.ValueAnimator

data class Dot(
    val x: Float,
    val y: Float,
    var radius: Float,
    val id: Int
) {
    var animatedRadius = radius

    fun contains(px: Float, py: Float): Boolean {
        return (px - x) * (px - x) + (py - y) * (py - y) <= animatedRadius * animatedRadius
    }

    fun startAnimation() {
        ValueAnimator.ofFloat(radius, radius * 1.5f, radius).apply {
            duration = 500
            addUpdateListener { animation ->
                animatedRadius = animation.animatedValue as Float
            }
            start()
        }
    }
}
