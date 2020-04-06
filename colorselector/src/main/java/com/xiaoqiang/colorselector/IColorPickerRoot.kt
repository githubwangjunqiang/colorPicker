package com.xiaoqiang.colorselector

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet

interface IColorPickerRoot {
    /**
     * 初始化
     */
    fun initView(context: Context, attributeSet: AttributeSet?)

    /**
     * 画横线
     */
    fun drawLineHorizontal(canvas: Canvas, x: Int, y: Int, endX: Int, endY: Int, paint: Paint)

    /**
     * 画纵向
     */
    fun drawLineVertical(canvas: Canvas,x: Int, y: Int, endX: Int, endY: Int, paint: Paint)

    /**
     * 画横线  圆标
     */
    fun drawRoundLabelHorizontal(canvas: Canvas, x: Int, y: Int, radius: Int, paint: Paint)

    /**
     * 画纵向 圆标
     */
    fun drawRoundLabelVertical(canvas: Canvas, x: Int, y: Int, radius: Int, paint: Paint)

}
