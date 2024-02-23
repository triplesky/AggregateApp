package com.ll.aggregate.listscreen

/**
 ***************************************
 * 项目名称: AggregateApp
 * @Author ll
 * 创建时间: 2024/1/9    15:47
 * 用途
 ***************************************

 */
data class ImageBean(
    val frames: List<Frame>,
    val meta: Meta
)

data class Frame(
    val filename: String,
    val frame: FrameX,
    val rotated: Boolean,
    val sourceSize: SourceSize,
    val spriteSourceSize: SpriteSourceSize,
    val trimmed: Boolean
)

data class Meta(
    val app: String,
    val format: String,
    val image: String,
    val scale: String,
    val size: Size,
    val smartupdate: String,
    val version: String
)

data class FrameX(
    val h: Int,
    val w: Int,
    val x: Int,
    val y: Int
)

data class SourceSize(
    val h: Int,
    val w: Int
)

data class SpriteSourceSize(
    val h: Int,
    val w: Int,
    val x: Int,
    val y: Int
)

data class Size(
    val h: Int,
    val w: Int
)