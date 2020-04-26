package com.tzion.testing

import android.text.SpannableString
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object RandomFactory {

    fun generateString(): String = UUID.randomUUID().toString()
    fun generateDouble(): Double = Math.random()
    fun generateInt(): Int = ThreadLocalRandom.current().nextInt(0, 1000 + 1)
    fun generateBoolean(): Boolean = Math.random() < 0.5
    fun generateInt(min: Int = 0, max: Int): Int = ThreadLocalRandom.current().nextInt(min, max)
    fun generateLong(): Long = Random().nextLong()
    fun generateLong(min: Long = 0, max: Long): Long = ThreadLocalRandom.current().nextLong(min, max)
    fun generateSpannableString() = SpannableString(generateString())

}