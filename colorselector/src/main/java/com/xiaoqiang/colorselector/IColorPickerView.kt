package com.xiaoqiang.colorselector

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet

interface IColorPickerView {
    /**
     * 初始化
     */
    fun initView(context: Context, attributeSet: AttributeSet?)

    /**
     * 绘制颜色表
     */
    fun drawColorSelector(canvas: Canvas)


    /**
     * 获取当前 选择的颜色
     */
    fun getColorSelector(selectorX:Int,selectorY:Int):Int


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