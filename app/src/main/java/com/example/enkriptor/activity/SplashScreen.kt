package com.example.enkriptor.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.enkriptor.R


class SplashScreen : AppCompatActivity() {

    lateinit var topAnim: Animation
    lateinit var txtFoodZest: TextView
    lateinit var imgAppIcon: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash_screen)

        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash_screen)

        //set animation for logo
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        txtFoodZest = findViewById(R.id.txtFoodZest)
        imgAppIcon = findViewById(R.id.imgAppIcon)

        txtFoodZest.animation = topAnim
        imgAppIcon.animation = topAnim


        Handler().postDelayed({
            val mainIntent =
                Intent(this@SplashScreen, Register::class.java)
            finish()
            startActivity(mainIntent)
        }, 2000)
    }

}