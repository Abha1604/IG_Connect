package com.example.ig_connect.Listeners

import com.example.ig_connect.models_chat.User

interface ConversionListener {
    fun onConversionClicked(user: User)
}