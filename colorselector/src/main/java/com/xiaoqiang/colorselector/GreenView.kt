package com.xiaoqiang.colorselector

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import java.util.*

class GreenView : View {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attributeSet, defStyleAttr, defStyleRes) {
    }

    val mPaint = Paint()

    init {
        mPaint.isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mPaint.setShader(LinearGradient(0f, 0f, w.toFloat(), h.toFloat(),
                Color.rgb(0, 0, 0), Color.rgb(0, 255, 0), Shader.TileMode.CLAMP))
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawPaint(mPaint)

    }
}