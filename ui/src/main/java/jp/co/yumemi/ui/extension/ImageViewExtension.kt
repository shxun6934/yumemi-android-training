package jp.co.yumemi.ui.extension

import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.databinding.BindingAdapter

@BindingAdapter("tint")
fun ImageView.setTint(@ColorRes color: Int) {
    setColorFilter(resources.getColor(color, null))
}
