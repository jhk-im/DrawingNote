package com.jhk

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class DrawingView (context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var mDrawBitmap: Bitmap? = null
    private var mDrawCanvas: Canvas? = null
    private var mCanvasPaint = Paint(Paint.DITHER_FLAG)

    private var isClearCanvas = false
    var touchAction = 0

    inner class DrawPath(
        var color: Int,
        var strokeWidth: Float,
        var alpha: Int,
        var isAntiAlias: Boolean,
        var xfermode: Xfermode?
    ) : Path()

    private var mPaths = ArrayList<DrawPath>()
    private var mUndoPath = ArrayList<DrawPath>()

    private var mPaint = Paint().apply {
        color = Color.WHITE
        alpha = 255
        strokeWidth = 4f
        xfermode = null
        isAntiAlias = false
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    private var mDrawPath =
        DrawPath(
            mPaint.color,
            mPaint.strokeWidth,
            mPaint.alpha,
            mPaint.isAntiAlias,
            mPaint.xfermode
        )

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mDrawBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mDrawBitmap?.let { bitmap ->
            mDrawCanvas = Canvas(bitmap)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mDrawBitmap?.let { bitmap ->
            canvas?.drawBitmap(bitmap, 0f, 0f, mCanvasPaint)
            mDrawCanvas?.drawBitmap(bitmap, 0f, 0f, mCanvasPaint)

            // Action Move
            if (touchAction == 2) {
                if (!mDrawPath.isEmpty) {
                    mPaint.strokeWidth = mDrawPath.strokeWidth
                    mPaint.color = mDrawPath.color
                    mPaint.alpha = mDrawPath.alpha
                    mPaint.xfermode = mDrawPath.xfermode
                    mPaint.isAntiAlias = mDrawPath.isAntiAlias
                    mDrawCanvas?.drawPath(mDrawPath, mPaint)
                }
            } else {
                mDrawCanvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                for (path in mPaths) {
                    mPaint.strokeWidth = path.strokeWidth
                    mPaint.color = path.color
                    mPaint.alpha = path.alpha
                    mPaint.xfermode = path.xfermode
                    mPaint.isAntiAlias = path.isAntiAlias
                    mDrawCanvas?.drawPath(path, mPaint)
                }
            }

            if (isClearCanvas) {
                isClearCanvas = false
                mDrawCanvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
            }
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        super.dispatchTouchEvent(event)

        event?.let { motionEvent ->
            val touchX = motionEvent.x
            val touchY = motionEvent.y
            touchAction = motionEvent.action
            //isColorTooltip = false
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    performClick()
                    actionDown(touchX = touchX, touchY = touchY)
                }
                MotionEvent.ACTION_UP -> {
                    actionUp()
                }
                MotionEvent.ACTION_MOVE -> {
                    actionMove(touchX = touchX, touchY = touchY)
                }
                else -> {}
            }
        }

        return true
    }


    private fun actionDown(touchX: Float, touchY: Float) {
        mDrawPath.reset()
        mDrawPath.moveTo(touchX, touchY)
        invalidate()
    }

    private fun actionUp() {
        mPaths.add(mDrawPath)
        mDrawPath = DrawPath(
            mPaint.color,
            mPaint.strokeWidth,
            mPaint.alpha,
            mPaint.isAntiAlias,
            mPaint.xfermode
        )
        invalidate()
    }

    private fun actionMove(touchX: Float, touchY: Float) {
        mDrawPath.lineTo(touchX, touchY)
        invalidate()
    }

    fun write(writeColor: Int) {
        isClearCanvas = false
        mDrawPath.apply {
            isAntiAlias = false
            xfermode = null
            alpha = 255
            color = writeColor
            strokeWidth = 4f
        }
    }

    fun erase() {
        isClearCanvas = false
        mDrawPath.apply {
            isAntiAlias = true
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            alpha = 0
            color = Color.TRANSPARENT
            strokeWidth = 60f
        }
    }

    fun undo() {
        if (mPaths.size > 0) {
            mUndoPath.add(mPaths[mPaths.size - 1])
            mPaths.removeAt(mPaths.size - 1)
            invalidate()
        }
    }

    fun redo() {
        if (mUndoPath.size > 0) {
            mPaths.add(mUndoPath[mUndoPath.size - 1])
            mUndoPath.removeAt(mUndoPath.size - 1)
            invalidate()
        }
    }

    fun clear() {
        mPaths.clear()
        mUndoPath.clear()
        isClearCanvas = true
        invalidate()
    }
}