package kz.dragau.larek.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import java.util.jar.Attributes

class SquareImageView : ImageView {

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet): super(context,attrs){}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle){}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredWidth)
    }
}