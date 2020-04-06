package com.xiaoqiang.colorselector

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.graphics.createBitmap

class ColorPickerView : View, IColorPickerView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        initView(context, attributeSet)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attributeSet, defStyleAttr, defStyleRes) {
        initView(context, attributeSet)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        val width = if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
//            var size = MeasureSpec.getSize(widthMeasureSpec);
//            if (size % 255 == 0) {
//                widthMeasureSpec
//            } else {
//                size = if (size > 255) size / 255 * 255 else 255;
//                MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY)
//            }
//        } else
//            MeasureSpec.makeMeasureSpec(255, MeasureSpec.EXACTLY)
//        val height = if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
//            var size = MeasureSpec.getSize(heightMeasureSpec);
//            if (size % 255 == 0) {
//                heightMeasureSpec
//            } else {
//                size = if (size > 255) size / 255 * 255 else 255;
//                MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY)
//            }
//        } else
//            MeasureSpec.makeMeasureSpec(255, MeasureSpec.EXACTLY)


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

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private var green = 0
    var centerX: Int = 0
    var centerY: Int = 0
    fun setGreen(g: Int) {
        green = g
        createBitmap = null
        postInvalidate()
    }

    val mPaint = Paint()

    override fun initView(context: Context, attributeSet: AttributeSet?) {
        mPaint.isAntiAlias = true
        mPaint.strokeWidth = 1F
    }

    var radius: Int = 4
    private var downX: Float = 0F
    private var downY: Float = 0F
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawColorSelector(canvas)
        mPaint.color = Color.BLACK


        drawLineHorizontal(canvas, centerX, centerY, width, centerY, mPaint)
        drawRoundLabelHorizontal(canvas, width - radius * 2, centerY, radius, mPaint)

        drawLineVertical(canvas, centerX, centerY, centerX, height, mPaint)
        drawRoundLabelVertical(canvas, centerX, height - radius * 2, radius, mPaint)
    }

    var createBitmap: Bitmap? = null
    override fun drawColorSelector(canvas: Canvas) {

        Log.d("12345", "drawColorSelector绘制")
        if (createBitmap == null) {
            createBitmap = Bitmap.createBitmap(width - paddingEnd, height - paddingBottom, Bitmap.Config.ARGB_8888)

            val can = Canvas(createBitmap!!)
            for (w in 0..width - paddingEnd) {
                for (h in 0..height - paddingBottom) {
                    mPaint.color = getColorSelector(w, h)
                    can.drawPoint(w.toFloat(), h.toFloat(), mPaint)
                }
            }
            canvas.drawBitmap(createBitmap!!, 0f, 0f, null)


        } else {
            canvas.drawBitmap(createBitmap!!, 0f, 0f, null)
        }


    }


    override fun getColorSelector(selectorX: Int, selectorY: Int): Int {
        var color: Int
        val pxW = width / 255
        val pxH = height / 255
        color = Color.rgb(selectorX / pxW, green, selectorY / pxH)
        return color
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

    private var horizontal: Int = 0
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
                centerX = Math.min(width-paddingEnd, centerX)
                centerX = Math.max(0, centerX)
            }
            if (horizontal == 2) {
                centerY += v1.toInt()
                centerY = Math.min(height-paddingBottom, centerY)
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