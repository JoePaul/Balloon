/*
 * Copyright (C) 2019 skydoves
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package com.skydoves.balloon

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.method.MovementMethod
import android.view.Gravity
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.MainThread
import androidx.annotation.StringRes
import com.skydoves.balloon.annotations.Sp
import com.skydoves.balloon.extensions.contextColor
import com.skydoves.balloon.extensions.dimen
import com.skydoves.balloon.extensions.px2Sp

@DslMarker
internal annotation class TextFormDsl

/** creates an instance of [TextForm] from [TextForm.Builder] using kotlin dsl. */
@MainThread
@TextFormDsl
@JvmSynthetic
inline fun textForm(context: Context, crossinline block: TextForm.Builder.() -> Unit): TextForm =
  TextForm.Builder(context).apply(block).build()

/**
 * TextFrom is an attribute class what has some attributes about TextView
 * for customizing popup texts easily.
 */
class TextForm private constructor(
  builder: Builder
) {

  val text: CharSequence = builder.text

  @Sp
  val textSize: Float = builder.textSize

  @ColorInt
  val textColor: Int = builder.textColor

  val textIsHtml: Boolean = builder.textIsHtml

  val movementMethod: MovementMethod? = builder.movementMethod

  val textStyle: Int = builder.textTypeface

  val textTypeface: Typeface? = builder.textTypefaceObject

  val textGravity: Int = builder.textGravity

  /** Builder class for [TextForm]. */
  @TextFormDsl
  class Builder(val context: Context) {
    @JvmField
    @set:JvmSynthetic
    var text: CharSequence = ""

    @Sp
    @JvmField
    @set:JvmSynthetic
    var textSize: Float = 12f

    @ColorInt
    @JvmField
    @set:JvmSynthetic
    var textColor = Color.WHITE

    @JvmField
    @set:JvmSynthetic
    var textIsHtml: Boolean = false

    @JvmField
    @set:JvmSynthetic
    var movementMethod: MovementMethod? = null

    @JvmField
    @set:JvmSynthetic
    var textTypeface = Typeface.NORMAL

    @JvmField
    @set:JvmSynthetic
    var textTypefaceObject: Typeface? = null

    @JvmField
    @set:JvmSynthetic
    var textGravity: Int = Gravity.CENTER

    /** sets the content text of the form. */
    fun setText(value: CharSequence): Builder = apply { this.text = value }

    /** sets the content text of the form using string resource. */
    fun setTextResource(@StringRes value: Int): Builder = apply {
      this.text = context.getString(value)
    }

    /** sets the size of the text. */
    fun setTextSize(@Sp value: Float): Builder = apply { this.textSize = value }

    /** sets the size of the main text content using dimension resource. */
    fun setTextSizeResource(@DimenRes value: Int): Builder = apply {
      this.textSize = context.px2Sp(context.dimen(value))
    }

    /** sets the color of the text. */
    fun setTextColor(@ColorInt value: Int): Builder = apply { this.textColor = value }

    /** sets whether the text will be parsed as HTML (using Html.fromHtml(..)) */
    fun setTextIsHtml(value: Boolean): Builder = apply { this.textIsHtml = value }

    /** sets the movement method for TextView. */
    fun setMovementMethod(value: MovementMethod): Builder = apply {
      this.movementMethod = value
    }

    /** sets the color of the text using resource. */
    fun setTextColorResource(@ColorRes value: Int): Builder = apply {
      this.textColor = context.contextColor(value)
    }

    /** sets the [Typeface] of the text. */
    fun setTextTypeface(value: Int): Builder = apply { this.textTypeface = value }

    /** sets the [Typeface] of the text. */
    fun setTextTypeface(value: Typeface?): Builder = apply { this.textTypefaceObject = value }

    /** sets gravity of the text. */
    fun setTextGravity(value: Int): Builder = apply {
      this.textGravity = value
    }

    fun build() = TextForm(this)
  }
}
