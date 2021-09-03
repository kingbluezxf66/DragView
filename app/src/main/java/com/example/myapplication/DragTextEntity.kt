package com.example.myapplication

import com.chad.library.adapter.base.entity.MultiItemEntity

const val TEXTTYPE = 1
const val IMGTYPE = 2
data class DragTextEntity(
    val dragTex: String, override val itemType: Int
):MultiItemEntity