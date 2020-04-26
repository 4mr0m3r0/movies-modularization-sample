package com.tzion.ui_components.view

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.tzion.ui_components.R

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