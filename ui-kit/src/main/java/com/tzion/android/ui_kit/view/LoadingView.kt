package com.tzion.android.ui_kit.view

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.tzion.android.ui_kit.R

class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : ProgressBar(context, attrs, defStyleAttr) {

    init {
        indeterminateDrawable.setColorFilter(
            ContextCompat.getColor(context, R.color.grey_darker),
            PorterDuff.Mode.SRC_IN)
    }

}