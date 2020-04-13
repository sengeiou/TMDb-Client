package com.example.feature_pincode.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import com.example.feature_pincode.R

class PincodeBubblesView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_BUBBLES_COUNT = 4
    }

    private val bubblesCount: Int

    private val activeBubbleDrawable = ContextCompat.getDrawable(
        context,
        R.drawable.pincode_active_bubble_bg
    )

    private val inactiveBubbleDrawable = ContextCompat.getDrawable(
        context,
        R.drawable.pincode_inactive_bubble_bg
    )

    private val errorBubbleDrawable = ContextCompat.getDrawable(
        context,
        R.drawable.pincode_error_bubble_bg
    )

    init {
        orientation = HORIZONTAL
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PincodeBubblesView)
            bubblesCount = typedArray.getInteger(
                R.styleable.PincodeBubblesView_bubbles_count,
                DEFAULT_BUBBLES_COUNT
            )
            typedArray.recycle()
        } else {
            bubblesCount = DEFAULT_BUBBLES_COUNT
        }

        dividerDrawable = ContextCompat.getDrawable(context, R.drawable.pincode_bubble_divider)
        showDividers = SHOW_DIVIDER_MIDDLE

        val bubbleSize = resources.getDimensionPixelSize(R.dimen.pincode_bubble_size)

        (0 until bubblesCount).forEach { _ ->
            val imageView = ImageView(context).apply {
                layoutParams = ViewGroup.LayoutParams(bubbleSize, bubbleSize)
                setImageDrawable(inactiveBubbleDrawable)
            }
            this.addView(imageView)
        }
    }

    fun setActiveBubblesCount(count: Int) {
        this.forEachIndexed { index, view ->
            (view as? ImageView)?.let { imageView ->
                if (index < count) {
                    imageView.setImageDrawable(activeBubbleDrawable)
                } else {
                    imageView.setImageDrawable(inactiveBubbleDrawable)
                }
            }
        }
    }

    fun setErrorState() {
        this.forEach { (it as? ImageView)?.setImageDrawable(errorBubbleDrawable) }
    }
}
