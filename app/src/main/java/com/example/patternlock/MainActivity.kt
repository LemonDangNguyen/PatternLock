package com.example.patternlock

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.patternlock.databinding.ActivityMainBinding
import com.example.patternlock.viewmodel.PatternLockViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: PatternLockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        observeSavedPattern()

        binding.btnCreatePattern.setOnClickListener {
            val intent = Intent(this, CreatePatternActivity::class.java)
            startActivity(intent)
        }

        binding.btnUnlock.setOnClickListener {
            val intent = Intent(this, UnlockActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeSavedPattern() {
        viewModel.savedPattern.observe(this) { pattern ->
            if (!pattern.isNullOrEmpty()) {
                binding.btnCreatePattern.visibility = View.INVISIBLE
                binding.btnUnlock.visibility = View.VISIBLE
            } else {
                binding.btnCreatePattern.visibility = View.VISIBLE
                binding.btnUnlock.visibility = View.INVISIBLE
            }
        }
    }

}
