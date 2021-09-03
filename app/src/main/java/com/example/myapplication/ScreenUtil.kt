package com.example.myapplication

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

object ScreenUtil {

    fun getWithedHeighed(context: Context): Int {
        // 获得屏幕宽度
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        val sWidth = dm.widthPixels // 屏幕宽度
        return dm.heightPixels
    }

    fun getWithedWidth(context: Context): Int {
        // 获得屏幕宽度
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

    fun getScreenHeight(context: Context, isDp: Boolean): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        val height = dm.heightPixels // 屏幕高度（像素）
        if (!isDp) {
            return height
        }
        val density = dm.density // 屏幕密度（0.75 / 1.0 / 1.5）
        return (height / density).toInt()
    }

    fun getScreenWidth(context: Context, isDp: Boolean): Int {
        var screenWidth = 0
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels // 屏幕高度（像素）
        if (!isDp) {
            return width
        }
        val density = dm.density // 屏幕密度（0.75 / 1.0 / 1.5）
        screenWidth = (width / density).toInt() // 屏幕高度(dp)
        return screenWidth
    }

    /**
     * dp转换成px
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


}