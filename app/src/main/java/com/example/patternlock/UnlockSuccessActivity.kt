package com.example.patternlock

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.patternlock.databinding.ActivityUnlockSuccessBinding
import com.example.patternlock.viewmodel.PatternLockViewModel

class UnlockSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUnlockSuccessBinding
    private val viewModel: PatternLockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnlockSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnExit: Button = binding.btnExit
        val btnReset: Button = binding.btnReset

        btnExit.setOnClickListener {
            finishAffinity()
        }
        btnReset.setOnClickListener {
            viewModel.clearPattern()
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}
