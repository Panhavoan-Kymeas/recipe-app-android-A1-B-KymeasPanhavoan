package com.example.cooksy.ui.onboarding

import androidx.annotation.DrawableRes


data class OnBoardingPage(
    val title: String,
    val description: String,
    @get:DrawableRes val image: Int
)