package com.example.patternlock

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.patternlock.databinding.ActivityCreatePatternBinding
import com.example.patternlock.viewmodel.PatternLockViewModel

class CreatePatternActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePatternBinding
    private val viewModel: PatternLockViewModel by viewModels()

    private var isConfirmingPattern = false
    private var firstPattern: List<Int>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePatternBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.patternLockView.patternComplete.observe(this) { pattern ->
            if (!isConfirmingPattern) {
                firstPattern = pattern
                binding.statusText.text = "Vẽ lại mẫu hình để xác nhận"
                isConfirmingPattern = true
            } else {
                if (pattern == firstPattern) {
                    viewModel.savePattern(pattern)
                    Toast.makeText(this, "Hình vẽ đã được lưu!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    binding.statusText.text = "Hình vẽ không khớp. Thử lại."
                    firstPattern = null
                    isConfirmingPattern = false
                }
            }
        }
    }
}
