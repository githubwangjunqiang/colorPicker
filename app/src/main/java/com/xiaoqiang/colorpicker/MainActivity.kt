package com.xiaoqiang.colorpicker

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.xiaoqiang.colorselector.ColorPickerView

class MainActivity : AppCompatActivity() {

    var viewColor: ColorPickerView? = null
    var bar: SeekBar? = null
    var biaoji: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewColor = findViewById(R.id.colorse)
        bar = findViewById(R.id.seebrarara)
        biaoji = findViewById(R.id.biaoji)
        biaoji = findViewById(R.id.biaoji)
        viewColor?.radius = 10

        bar!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                biaoji?.translationX = bar!!.width.toFloat() / 255F * progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewColor!!.setGreen(seekBar!!.progress)
            }

        })


    }
}