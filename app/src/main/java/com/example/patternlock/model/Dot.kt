package com.example.patternlock.model

data class Dot(
    val x: Float,
    val y: Float,
    val radius: Float,
    val id: Int
) {
    fun contains(px: Float, py: Float): Boolean {
        return (px - x) * (px - x) + (py - y) * (py - y) <= radius * radius
    }
}