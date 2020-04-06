package com.xiaoqiang.colorselector

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

class ColorPickerRoot : FrameLayout, IColorPickerRoot {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        initView(context, attributeSet)
    }

    private var horizontal: Int = 0
    private var downX: Float = 0F
    private var downY: Float = 0F
    val radius: Int = 4
    val mPaint = Paint()

    override fun initView(context: Context, attributeSet: AttributeSet?) {
        mPaint.isAntiAlias = true
        mPaint.strokeWidth = 1F
    }

    override fun drawLineHorizontal(canvas: Canvas, x: Int, y: Int, endX: Int, endY: Int, paint: Paint) {
        canvas.drawLine(x.toFloat(), y.toFloat(), endX.toFloat(), endY.toFloat(), paint)
    }

    override fun drawLineVertical(canvas: Canvas, x: Int, y: Int, endX: Int, endY: Int, paint: Paint) {
        canvas.drawLine(x.toFloat(), y.toFloat(), endX.toFloat(), endY.toFloat(), paint)
    }

    override fun drawRoundLabelHorizontal(canvas: Canvas, x: Int, y: Int, radius: Int, paint: Paint) {
        canvas.drawCircle(x.toFloat(), y.toFloat(), radius.toFloat(), paint)
    }

    override fun drawRoundLabelVertical(canvas: Canvas, x: Int, y: Int, radius: Int, paint: Paint) {
        canvas.drawCircle(x.toFloat(), y.toFloat(), radius.toFloat(), paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = getSize(widthMeasureSpec)
        val height = getSize(heightMeasureSpec)

        super.onMeasure(width, height)
    }

    private fun getSize(mMeasureSpec: Int): Int {
        return if (MeasureSpec.getMode(mMeasureSpec) == MeasureSpec.EXACTLY) {
            if (MeasureSpec.getSize(mMeasureSpec) % 255 == 0) {
                MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(mMeasureSpec) + paddingEnd, MeasureSpec.EXACTLY)
            } else {
                MeasureSpec.makeMeasureSpec(if (MeasureSpec.getSize(mMeasureSpec) > 255) MeasureSpec.getSize(mMeasureSpec) / 255 * 255 + paddingEnd else 255 + paddingEnd, MeasureSpec.EXACTLY)
            }
        } else
            MeasureSpec.makeMeasureSpec(255 + paddingEnd, MeasureSpec.EXACTLY)
    }

    var centerX: Int = 0
    var centerY: Int = 0
    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)


        drawLineHorizontal(canvas, centerX, centerY, width, centerY, mPaint)
        drawRoundLabelHorizontal(canvas, width - radius*2, centerY, radius, mPaint)

        drawLineVertical(canvas, centerX, centerY, centerX, height, mPaint)
        drawRoundLabelVertical(canvas, centerX, height - radius*2, radius, mPaint)

    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event!!.action == MotionEvent.ACTION_DOWN) {
            downX = event.x
            downY = event.y
            horizontal = 0
        }
        if (event.action == MotionEvent.ACTION_MOVE) {
            val x = event.x
            val y = event.y
            val v: Float = x - downX
            val v1: Float = y - downY
            if (horizontal == 0) {
                horizontal = if (Math.abs(v) > Math.abs(v1)) 1 else 2
            }
            if (horizontal == 1) {
                centerX += v.toInt()
                centerX = Math.min(width, centerX)
                centerX = Math.max(0, centerX)
            }
            if (horizontal == 2) {
                centerY += v1.toInt()
                centerY = Math.min(height, centerY)
                centerY = Math.max(0, centerY)
            }
            downX = x
            downY = y
            postInvalidate()
        }
        if (event.action == MotionEvent.ACTION_UP) {
            horizontal = 0
            callback?.let {
                it.changeColor(centerX, centerY)
            }
        }
        if (event.action == MotionEvent.ACTION_CANCEL) {
            horizontal = 0
            callback?.let {
                it.changeColor(centerX, centerY)
            }
        }



        return true
    }

    var callback: Callback? = null

    interface Callback {
        fun changeColor(x: Int, y: Int)
    }
}