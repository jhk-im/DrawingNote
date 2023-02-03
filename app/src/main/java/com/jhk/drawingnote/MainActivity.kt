package com.jhk.drawingnote

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.SeekBar
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
            binding.drawingView.write()
        }

        binding.sbPencil.progress = binding.drawingView.setStrokeWidth.toInt()
        binding.sbPencil.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.drawingView.setStrokeWidth = p1.toFloat()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                //
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                binding.drawingView.write()
            }
        })

        binding.sbPencilAlpha.progress = binding.drawingView.setAlpha
        binding.sbPencilAlpha.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.drawingView.setAlpha = p1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                //
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                binding.drawingView.write()
            }
        })

        binding.sbErase.progress = binding.drawingView.setEraseStrokeWidth.toInt()
        binding.sbErase.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.drawingView.setEraseStrokeWidth = p1.toFloat()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                //
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                binding.drawingView.erase()
            }
        })
    }
}