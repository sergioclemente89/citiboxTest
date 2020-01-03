package com.clementecastillo.citiboxtest.extension

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity


fun AppCompatActivity.hideKeyboard() {
    currentFocus?.let {
        val imm = it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}