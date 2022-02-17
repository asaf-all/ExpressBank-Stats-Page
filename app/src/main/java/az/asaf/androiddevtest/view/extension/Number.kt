package az.asaf.androiddevtest.view.extension

import android.content.Context
import android.util.TypedValue

fun Number.dpToPx(context: Context): Float {
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics)
}