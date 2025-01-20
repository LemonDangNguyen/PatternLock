package com.example.patternlock

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.patternlock.databinding.ActivityUnlockBinding
import com.example.patternlock.viewmodel.PatternLockViewModel

class UnlockActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUnlockBinding
    private val viewModel: PatternLockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnlockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.patternLockView.patternComplete.observe(this) { pattern ->
            viewModel.validatePattern(pattern)
        }

        viewModel.unlockStatus.observe(this) { isSuccess ->
            if (isSuccess) {
                startActivity(Intent(this, UnlockSuccessActivity::class.java))
                finish()
            } else {
                binding.statusText.text = "Hình vẽ sai. Thử lại."
                Toast.makeText(this, "Hình vẽ sai!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
