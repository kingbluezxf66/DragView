package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DragTextView.OnPositionCallBack
import kotlin.math.log


class SecondActivity : AppCompatActivity() {
    val emptyViewList = mutableListOf<TextView>()
    val dragMap = mutableMapOf<TextView, DragTextView>()
    private val bottomViewList = mutableMapOf<DragTextView, ImageView>()
    val strList = ArrayList<String>()

    //正确答案顺序
    val correctList = ArrayList<String>()

    //用户答案顺序
    val userAnswerList = ArrayList<String>()

    //dragTextView text集合
    val dragTextLst = ArrayList<String>()
    val textViewList = ArrayList<TextView>()
    val imageViewList = ArrayList<ImageView>()
    val dragTextViewList = ArrayList<DragTextView>()
    val dragTextViewMap = mutableMapOf<DragTextView, String>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dtv1 = findViewById<DragTextView>(R.id.dtv_1)
        val dtv2 = findViewById<DragTextView>(R.id.dtv_2)
        val dtv3 = findViewById<DragTextView>(R.id.dtv_3)
        val tv1 = findViewById<TextView>(R.id.tv_1)
        val tv2 = findViewById<TextView>(R.id.tv_2)
        val tv3 = findViewById<TextView>(R.id.tv_3)
        val iv10 = findViewById<ImageView>(R.id.iv_10)
        val iv11 = findViewById<ImageView>(R.id.iv_11)
        val iv12 = findViewById<ImageView>(R.id.iv_12)
        val ilFirst = findViewById<ConstraintLayout>(R.id.il_first)
        val ilSecond = findViewById<ConstraintLayout>(R.id.il_second)
        val tvSelect = findViewById<TextView>(R.id.tv_select)
        val tvShowCorrect = findViewById<TextView>(R.id.tv_show_correct)
        val btSeven = findViewById<Button>(R.id.bt_seven)

        val strList = mutableListOf("##", "##", "##")
        //正确答案顺序
        val correctList = mutableListOf("C", "B", "A")
        //用户答案顺序
        val userAnswerList = ArrayList<String>()
        //dragTextView text集合
        val dragTextLst = mutableListOf("A", "B", "C")
        val textViewList = mutableListOf<TextView>(tv1, tv2, tv3)
        val imageViewList = mutableListOf<ImageView>(iv10, iv11, iv12)
        val dragTextViewList = mutableListOf<DragTextView>(dtv1, dtv2, dtv3)
        val dragTextViewMap = mutableMapOf<DragTextView, String>()

        tvShowCorrect.setOnClickListener {
            userAnswerList.clear()
            //显示正确答案
            if (dragMap.size == imageViewList.size) {
                for (index in textViewList.indices) {
                    val textView = textViewList[index]
                    val dragTextView = dragMap[textView]
                    userAnswerList.add(dragTextView?.text.toString())
                }
                if (correctList.toString() == userAnswerList.toString()) {
                    Log.i("zxf", "回答正确了")
                } else {
                    Log.i("zxf", "回答错了")
                }
            }
        }
        btSeven.setOnClickListener {
            ilFirst.visibility = View.GONE
            ilSecond.visibility = View.VISIBLE
        }

        for (index in dragTextLst.indices) {
            dragTextViewMap[dragTextViewList[index]] = dragTextLst[index]
        }
        //空 TextView
        for (index in strList.indices) {
            if (strList[index] == "##") {
                textViewList[index].setBackgroundResource(R.drawable.shape_rectangle_dotted_corners_15)
                emptyViewList.add(textViewList[index])
            } else {
                textViewList[index].text = strList[index]
            }
        }
        for (index in dragTextViewList.indices) {
            bottomViewList[dragTextViewList[index]] = imageViewList[index]
            val dragTextView = dragTextViewList[index]
            dragTextView.text = dragTextLst[index]
            val bottomImageView = bottomViewList[dragTextView]
            dragTextView.setPositionCallBack(object : OnPositionCallBack {
                override fun callback(left: Int, top: Int, right: Int, bottom: Int) {
                    //top bottom left right 在tv1的范围内 直接居中显示 否则回到原位置
                    Log.i(
                        "zxf",
                        "callback: --$left  $right $top $bottom "
                    )
                    var newTop = dragTextView.top
                    var newLeft = dragTextView.left
                    var newBottom = dragTextView.bottom
                    var newRight = dragTextView.right
                    //当前drag 绑定的view
                    var oldTextView: TextView? = null
                    Log.i("zxf", "drag-----: ----" + dragMap.size)

                    for ((textView, mDragView) in dragMap) {
                        if (dragTextView == mDragView) {
                            oldTextView = textView
                        }
                    }
                    for (emptyView in emptyViewList) {
                        val halfWidth = emptyView.width / 2
                        val halfHeight = emptyView.height / 2
                        val horizontalShow =
                            right >= emptyView.left + halfWidth && left <= emptyView.right - halfWidth
                        val verticalShow =
                            top <= emptyView.bottom - halfHeight && bottom >= emptyView.top + halfHeight

                        if (verticalShow && horizontalShow) {
                            //将dragTextView的text 赋值给 emptyView
                            // 两种情况 1.两个位置都有绑定 2.一个位置绑定
                            //当前text是否存在 dragText
                            val oldDragTextView = dragMap[emptyView]
                            dragMap[emptyView] = dragTextView
                            if (oldDragTextView != null) {
                                if (oldTextView != null) {
                                    //drag双向绑定
                                    Log.i("zxf", "已经双向绑定textView: ---")
                                    swapTextViewPosition(
                                        oldTextView, emptyView,
                                        oldDragTextView, dragTextView
                                    )
                                    return
                                } else {
                                    Log.i("zxf", "已经单向绑定textView: ---")
                                    //drag 单向绑定
                                    if (bottomImageView != null) {
                                        swapSingleBindPosition(
                                            emptyView,
                                            dragTextView,
                                            oldDragTextView
                                        )
                                        return
                                    }
                                }
                            }
                            newTop = emptyView.top
                            newLeft = emptyView.left
                            newRight = emptyView.right
                            newBottom = emptyView.bottom
                            break
                        } else {
                            //之前绑定的关系解除
                            if (dragMap.isNotEmpty()) {
                                var deleteView: TextView? = null
                                for ((textView, mDragTextView) in dragMap) {
                                    if (dragTextView == mDragTextView) {
                                        deleteView = textView
                                    }
                                }
                                deleteView?.let { dragMap.remove(it) }
                            }
                            bottomImageView?.let {
                                newTop = bottomImageView.top + 2
                                newLeft = bottomImageView.left + 2
                                newRight = bottomImageView.right - 2
                                newBottom = bottomImageView.bottom - 2
                            }
                        }
                    }
                    dragTextView.top = newTop
                    dragTextView.left = newLeft
                    dragTextView.right = newRight
                    dragTextView.bottom = newBottom
                    if (dragMap.size == imageViewList.size) {
                        tvSelect.setBackgroundColor(Color.RED)
                    } else {
                        tvSelect.setBackgroundColor(Color.WHITE)
                    }
                }
            })
        }
    }

    fun swapSingleBindPosition(
        newTextView: TextView, newDragTextView: DragTextView, oldDragTextView: DragTextView
    ) {
        dragMap[newTextView] = newDragTextView
        val bottomImageView = bottomViewList[oldDragTextView]
        //newDrag 显示在emptyView上  oldDrag回显到原位置
        val newViewLeft = newTextView.left
        val newViewRight = newTextView.right
        val newViewTop = newTextView.top
        val newViewBottom = newTextView.bottom
        newDragTextView.left = newViewLeft
        newDragTextView.right = newViewRight
        newDragTextView.top = newViewTop
        newDragTextView.bottom = newViewBottom
        bottomImageView?.let {
            val oldViewLeft = bottomImageView.left
            val oldViewRight = bottomImageView.right
            val oldViewTop = bottomImageView.top
            val oldViewBottom = bottomImageView.bottom
            oldDragTextView.left = oldViewLeft
            oldDragTextView.right = oldViewRight
            oldDragTextView.top = oldViewTop
            oldDragTextView.bottom = oldViewBottom
        }
    }

    fun swapTextViewPosition(
        oldTextView: TextView, newTextView: TextView,
        oldDragTextView: DragTextView, newDragTextView: DragTextView
    ) {
        dragMap[oldTextView] = oldDragTextView
        dragMap[newTextView] = newDragTextView

        val tv1Left = oldTextView.left
        val tv1Right = oldTextView.right
        val tv1Top = oldTextView.top
        val tv1Bottom = oldTextView.bottom
        val tv2Left = newTextView.left
        val tv2Right = newTextView.right
        val tv2Top = newTextView.top
        val tv2Bottom = newTextView.bottom
        oldDragTextView.left = tv1Left
        oldDragTextView.right = tv1Right
        oldDragTextView.top = tv1Top
        oldDragTextView.bottom = tv1Bottom
        newDragTextView.left = tv2Left
        newDragTextView.right = tv2Right
        newDragTextView.top = tv2Top
        newDragTextView.bottom = tv2Bottom
    }
}