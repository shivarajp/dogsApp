package com.shapegames.animals.utils

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shapegames.animals.utils.Util.imageUrls
import java.util.*

fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun View.hide(){
    this.visibility = View.GONE
}

fun View.show(){
    this.visibility = View.VISIBLE
}

fun ImageView.load(uri:String){
    Glide.with(this)
        .load(uri)
        .apply(RequestOptions().centerCrop())
        .into(this)
}

fun ImageView.load(uri:Int){
    Glide.with(this)
        .load(uri)
        .apply(RequestOptions().centerCrop())
        .into(this)
}

fun ImageView.load(){
    val rand = Random()
    val url = rand.nextInt(10)
    Glide.with(this)
        .load(imageUrls[url])
        .apply(RequestOptions().centerCrop())
        .into(this)
}
