package com.jhk.drawingnote

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.jhk.drawingnote.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.ivDelete.setOnClickListener {
            binding.drawingView.clear()
        }

        binding.ivUndo.setOnClickListener {
            binding.drawingView.undo()
        }

        binding.ivRedo.setOnClickListener {
            binding.drawingView.redo()
        }

        binding.ivErase.setOnClickListener {
            binding.drawingView.erase()
        }

        binding.ivPencil.setOnClickListener {
            binding.drawingView.write(R.color.white)
        }


    }
}