package com.example.patternlock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.patternlock.model.Dot

class PatternLockView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val dots = mutableListOf<Dot>()
    private val selectedDots = mutableListOf<Dot>()
    private val path = Path()
    private var currentX = 0f
    private var currentY = 0f

    private val dotPaint = Paint().apply {
        color = Color.DKGRAY
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val selectedDotPaint = Paint().apply {
        color = Color.GRAY
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val linePaint = Paint().apply {
        color = Color.GRAY
        alpha = 128
        strokeWidth = 30f
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        isAntiAlias = true
    }

    private val _patternComplete = MutableLiveData<List<Int>>()
    val patternComplete: LiveData<List<Int>> get() = _patternComplete

    private var dotRadius = 25f
    private var spacing = 120f
    private var startX = 0f
    private var startY = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        createDots(w, h)
    }

    private fun createDots(width: Int, height: Int) {
        dots.clear()
        val rows = 3
        val cols = 3

        val availableWidth = width.toFloat() - paddingLeft - paddingRight
        val availableHeight = height.toFloat() - paddingTop - paddingBottom

        spacing = Math.min(availableWidth, availableHeight) / 5f

        val totalGridWidth = (cols - 1) * spacing + cols * 2 * dotRadius
        val totalGridHeight = (rows - 1) * spacing + rows * 2 * dotRadius

        startX = (width - totalGridWidth) / 2f + dotRadius
        startY = (height - totalGridHeight) / 2f + dotRadius

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val x = startX + col * (2 * dotRadius + spacing)
                val y = startY + row * (2 * dotRadius + spacing)
                dots.add(Dot(x, y, dotRadius, row * 3 + col))
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        dots.forEach { dot ->
            val paint = if (selectedDots.contains(dot)) selectedDotPaint else dotPaint
            canvas.drawCircle(dot.x, dot.y, dot.animatedRadius, paint)
        }

        if (selectedDots.isNotEmpty()) {
            path.reset()
            val firstDot = selectedDots.first()
            path.moveTo(firstDot.x, firstDot.y)
            for (i in 1 until selectedDots.size) {
                path.lineTo(selectedDots[i].x, selectedDots[i].y)
            }
            canvas.drawPath(path, linePaint)
        }

        if (selectedDots.isNotEmpty()) {
            val lastDot = selectedDots.last()
            canvas.drawLine(lastDot.x, lastDot.y, currentX, currentY, linePaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        currentX = event.x
        currentY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                selectedDots.clear()
                handleTouch(currentX, currentY)
            }

            MotionEvent.ACTION_MOVE -> {
                handleTouch(currentX, currentY)
            }

            MotionEvent.ACTION_UP -> {
                _patternComplete.value = selectedDots.map { it.id }
                selectedDots.forEach { it.startAnimation() }
            }
        }

        invalidate()
        return true
    }
// bỏ qua nếu ko chạm
//    private fun handleTouch(x: Float, y: Float) {
//        dots.forEach { dot ->
//            if (!selectedDots.contains(dot) && dot.contains(x, y)) {
//                selectedDots.add(dot)
//                dot.startAnimation()
//            }
//        }
//    }


    // đi qua là dính
    private fun handleTouch(x: Float, y: Float) {
        val targetDot = dots.firstOrNull { !selectedDots.contains(it) && it.contains(x, y) }
        if (targetDot != null) {
            // Nếu có Dot mới được chạm
            val lastDot = selectedDots.lastOrNull()
            if (lastDot != null) {
                val midDot = findIntermediateDot(lastDot, targetDot)
                if (midDot != null && !selectedDots.contains(midDot)) {
                    selectedDots.add(midDot)
                    midDot.startAnimation()
                }
            }

            selectedDots.add(targetDot)
            targetDot.startAnimation()
        }
    }
    private fun findIntermediateDot(dot1: Dot, dot2: Dot): Dot? {
        // Nếu cả hai cùng hàng hoặc cùng cột hoặc đường chéo
        val row1 = dot1.id / 3
        val col1 = dot1.id % 3
        val row2 = dot2.id / 3
        val col2 = dot2.id % 3

        val midRow = (row1 + row2) / 2
        val midCol = (col1 + col2) / 2

        // Nếu đi qua chính giữa một Dot
        if ((row1 + row2) % 2 == 0 && (col1 + col2) % 2 == 0) {
            val midId = midRow * 3 + midCol
            return dots.firstOrNull { it.id == midId }
        }

        return null
    }
}
