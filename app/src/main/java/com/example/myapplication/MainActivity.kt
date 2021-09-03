package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.DragTextView.OnPositionCallBack


class MainActivity : AppCompatActivity() {
    val emptyViewList = mutableListOf<TextView>()
    val dragMap = mutableMapOf<TextView, DragTextView>()
    private val bottomViewList = mutableMapOf<DragTextView, ImageView>()

    //用户答案顺序
    private val userAnswerList = ArrayList<String>()
    private val correctList = ArrayList<String>()
    private val dragTextViewMap = mutableMapOf<DragTextView, String>()
    private lateinit var tvSelect: TextView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dtv1 = findViewById<DragTextView>(R.id.dtv_1)
        val dtv2 = findViewById<DragTextView>(R.id.dtv_2)
        val dtv3 = findViewById<DragTextView>(R.id.dtv_3)
        val dtvMulti1 = findViewById<DragTextView>(R.id.dtv_multi_1)
        val dtvMulti2 = findViewById<DragTextView>(R.id.dtv_multi_2)
        val dtvMulti3 = findViewById<DragTextView>(R.id.dtv_multi_3)
        val dtvMulti4 = findViewById<DragTextView>(R.id.dtv_multi_4)
        val dtvMulti5 = findViewById<DragTextView>(R.id.dtv_multi_5)
        val tv1 = findViewById<TextView>(R.id.tv_1)
        val tv2 = findViewById<TextView>(R.id.tv_2)
        val tv3 = findViewById<TextView>(R.id.tv_3)
        val tvMulti1 = findViewById<TextView>(R.id.tv_multi_1)
        val tvMulti2 = findViewById<TextView>(R.id.tv_multi_2)
        val tvMulti3 = findViewById<TextView>(R.id.tv_multi_3)
        val tvMulti4 = findViewById<TextView>(R.id.tv_multi_4)
        val tvMulti5 = findViewById<TextView>(R.id.tv_multi_5)
        val tvMulti6 = findViewById<TextView>(R.id.tv_multi_6)
        val tvMulti7 = findViewById<TextView>(R.id.tv_multi_7)
        val iv10 = findViewById<ImageView>(R.id.iv_10)
        val iv11 = findViewById<ImageView>(R.id.iv_11)
        val iv12 = findViewById<ImageView>(R.id.iv_12)
        val ivMulti10 = findViewById<ImageView>(R.id.iv_multi_10)
        val ivMulti11 = findViewById<ImageView>(R.id.iv_multi_11)
        val ivMulti12 = findViewById<ImageView>(R.id.iv_multi_12)
        val ivMulti13 = findViewById<ImageView>(R.id.iv_multi_13)
        val ivMulti14 = findViewById<ImageView>(R.id.iv_multi_14)
        val ilFirst = findViewById<ConstraintLayout>(R.id.il_first)
        val ilSecond = findViewById<ConstraintLayout>(R.id.il_second)
        tvSelect = findViewById(R.id.tv_select)
        val tvShowCorrect = findViewById<TextView>(R.id.tv_show_correct)
        val btSeven = findViewById<Button>(R.id.bt_seven)
        val strList = listOf("##", "##", "##")
        val dragTextLst = listOf("A", "B", "C")
        val textViewList = listOf(tv1, tv2, tv3)
        val imageViewList = listOf(iv10, iv11, iv12)
        val dragTextViewList = listOf(dtv1, dtv2, dtv3)
        correctList.add("A")
        correctList.add("B")
        correctList.add("C")
        dealData(strList, dragTextLst, textViewList, imageViewList, dragTextViewList)
        tvShowCorrect.setOnClickListener {
            userAnswerList.clear()
            //显示正确答案
            if (dragMap.size == emptyViewList.size) {
                for (index in emptyViewList.indices) {
                    val textView = emptyViewList[index]
                    val dragTextView = dragMap[textView]
                    userAnswerList.add(dragTextView?.text.toString())
                }
                if (userAnswerList.isNotEmpty()) {
                    Log.i("zxf", "$correctList---------$userAnswerList")
                    if (correctList.toString() == userAnswerList.toString()) {
                        Toast.makeText(this, "回答正确", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.i("zxf", "回答错了")
                        Toast.makeText(this, "回答错误", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        btSeven.setOnClickListener {
            tvSelect.setBackgroundColor(Color.WHITE)
            ilFirst.visibility = View.GONE
            ilSecond.visibility = View.VISIBLE
            emptyViewList.clear()
            dragMap.clear()
            bottomViewList.clear()
            userAnswerList.clear()
            dragTextViewMap.clear()
            correctList.clear()
            val strList = listOf("##", "G", "##", "##", "F", "##", "##")
            correctList.add("A")
            correctList.add("B")
            correctList.add("C")
            correctList.add("D")
            correctList.add("E")
            val dragTextLst = listOf("A", "B", "C", "D", "E")
            val textViewList =
                listOf(tvMulti1, tvMulti2, tvMulti3, tvMulti4, tvMulti5, tvMulti6, tvMulti7)
            val imageViewList = listOf(ivMulti10, ivMulti11, ivMulti12, ivMulti13, ivMulti14)
            val dragTextViewList = listOf(dtvMulti1, dtvMulti2, dtvMulti3, dtvMulti4, dtvMulti5)
            dealData(strList, dragTextLst, textViewList, imageViewList, dragTextViewList)
        }
    }

    private fun dealData(
        strList: List<String>,
        dragTextLst: List<String>,
        textViewList: List<TextView>,
        imageViewList: List<ImageView>,
        dragTextViewList: List<DragTextView>
    ) {
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