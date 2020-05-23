package com.example.marvelworld.popoutimage

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import com.example.marvelworld.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image_popout.popout_image
import kotlinx.android.synthetic.main.back_details.popout_image_back
import kotlinx.android.synthetic.main.back_details.characterName

class ImagePopOutActivity : Activity() {

    lateinit var front_anim: AnimatorSet
    lateinit var back_anim: AnimatorSet
    var isFront = true

    companion object {
        const val URL = "URL"
        const val NAME= "NAME"
        const val COMICS_DETAILS = "COMICS_DETAILS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_popout)
        // set the activity height and width
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        window.setLayout((width * (0.8)).toInt(), (height * 0.7).toInt())

        // Get the URL
        val url = intent?.getStringExtra(URL)
        val name = intent?.getStringExtra(NAME)
        val comics  = intent?.getStringArrayListExtra(COMICS_DETAILS)
        Picasso.with(this).load(url).into(popout_image)

        characterName.text = name


        front_anim = AnimatorInflater.loadAnimator(applicationContext, R.animator.front_animator) as AnimatorSet
        back_anim = AnimatorInflater.loadAnimator(applicationContext, R.animator.back_animator) as AnimatorSet
        popout_image.setOnClickListener {
            if (isFront) {
                front_anim.setTarget(popout_image)
                back_anim.setTarget(popout_image_back)
                front_anim.start()
                back_anim.start()
                isFront = false
            } else {
                front_anim.setTarget(popout_image_back)
                back_anim.setTarget(popout_image)
                front_anim.start()
                back_anim.start()
                isFront = true
            }
        }
    }
}
