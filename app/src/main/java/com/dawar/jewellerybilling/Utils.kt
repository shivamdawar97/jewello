package com.dawar.jewellerybilling

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

object Utils {

    fun TextView.onTextChanged(listener:(CharSequence)->Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener.invoke(s!!)
            }
        })
    }

    fun animationListener(listener : ()-> Unit) : Animation.AnimationListener {
        return object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?){}
            override fun onAnimationStart(animation: Animation?){}
            override fun onAnimationEnd(animation: Animation?) {
                listener.invoke()
            }
        }
    }

    fun hideKeyboard(view: View){
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}