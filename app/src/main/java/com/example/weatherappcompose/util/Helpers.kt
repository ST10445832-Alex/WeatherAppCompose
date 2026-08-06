package com.example.weatherappcompose.util

import com.example.weatherappcompose.R

object Helpers {
    fun getWeatherIconResourceId(iconId: Int?) : Int {
        when (iconId) {
            1 -> return R.drawable.one
            2 -> return R.drawable.two
            3 -> return R.drawable.three
            4 -> return R.drawable.four
            5 -> return R.drawable.five
            6 -> return R.drawable.six
            7 -> return R.drawable.seven
            8 -> return R.drawable.eight
            11 -> return R.drawable.eleven
            12 -> return R.drawable.twelve
            13 -> return R.drawable.thirteen
            14 -> return R.drawable.fourteen
            15 -> return R.drawable.fifteen
            16 -> return R.drawable.sixteen
            17 -> return R.drawable.seventeen
            18 -> return R.drawable.eighteen
            19 -> return R.drawable.nineteen
            20 -> return R.drawable.twenty
            21 -> return R.drawable.twentyone
            22 -> return R.drawable.twentytwo
            23 -> return R.drawable.twentythree
            24 -> return R.drawable.twentyfour
            25 -> return R.drawable.twentyfive
            26 -> return R.drawable.twentysix
            29 -> return R.drawable.twentynine
            30 -> return R.drawable.thirty
            31 -> return R.drawable.thirtyone
            32 -> return R.drawable.thirtytwo
            33 -> return R.drawable.thirtythree
            34 -> return R.drawable.thirtyfour
            35 -> return R.drawable.thirtyfive
            36 -> return R.drawable.thirtysix
            37 -> return R.drawable.thirtyseven
            38 -> return R.drawable.thirtyeight
            39 -> return R.drawable.thirtynine
            40 -> return R.drawable.forty
            41 -> return R.drawable.fortyone
            42 -> return R.drawable.fortytwo
            43 -> return R.drawable.fortythree
            44 -> return R.drawable.fortyfour
            else -> return R.drawable.sun
        }
    }
}