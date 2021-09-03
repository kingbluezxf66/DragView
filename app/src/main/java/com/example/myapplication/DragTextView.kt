package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.math.abs

class DragTextView : androidx.appcompat.widget.AppCompatButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {}
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int)
            : super(context, attributeSet, defStyleAttr)

    //记录最后的位置
    private var lastX: Int = 0
    private var lastY: Int = 0
    private var screenWidth = 0
    private var screenHeight = 0

    init {
        screenWidth = ScreenUtil.getWithedWidth(context)
        screenHeight = ScreenUtil.getWithedHeighed(context)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var action = event?.action ?: return super.onTouchEvent(event)
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.rawX.toInt()
                lastY = event.rawY.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                setBackgroundResource(R.drawable.shape_rectangle_white_corners_15)
                //偏移距离
                var dx = (event.rawX.toInt()) - lastX
                var dy = (event.rawY.toInt()) - lastY
                var mLeft = left + dx
                var mRight = right + dx
                var mBottom = bottom + dy
                var mTop = top + dy
                if (mLeft < 0) {
                    mLeft = 0
                    mRight = width
                }
                if (mRight > screenWidth) {
                    mRight = screenWidth
                    mLeft = mRight - width
                }
                if (mTop < 0) {
                    mTop = 0
                    mBottom = height
                }
                val viewGroup: ViewGroup = parent as ViewGroup
                Log.i(TAG, "zxf parent-----------:" + viewGroup.height)

                if (mBottom > viewGroup.height) {
                    mBottom = viewGroup.height
                    mTop = mBottom - height
                }
                //利用layout方法重新更新view的位置
                this.layout(mLeft, mTop, mRight, mBottom)
                Log.i("zxf", "onTouchEvent:--888--- $mLeft   $mRight   $mTop   $mBottom")
//

                lastX = event.rawX.toInt()
                lastY = event.rawY.toInt()
            }
            MotionEvent.ACTION_UP -> {
                mPositionCallBack?.let {
                    it.callback(left, top, right, bottom)
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private var mPositionCallBack: OnPositionCallBack? = null

    fun setPositionCallBack(positionCallBack: OnPositionCallBack) {
        mPositionCallBack = positionCallBack
    }

    interface OnPositionCallBack {
        fun callback(left: Int, top: Int, right: Int, bottom: Int)
    }
}