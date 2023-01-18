package com.jhk.drawingnote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import com.jhk.drawingnote.databinding.ActivityMainBinding
import com.jhk.drawingview.DrawingView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.drawingView.setOnTouchListener { view, motionEvent ->
            val touchX = motionEvent.x
            val touchY = motionEvent.y
            binding.drawingView.touchAction = motionEvent.action
            binding.isColorTooltip = false
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    view?.performClick()
                    binding.drawingView.actionDown(touchX = touchX, touchY = touchY)
                }
                MotionEvent.ACTION_UP -> {
                    binding.drawingView.actionUp()
                }
                MotionEvent.ACTION_MOVE -> {
                    binding.drawingView.actionMove(touchX = touchX, touchY = touchY)
                }
                else -> {}
            }
            true
        }
    }
}