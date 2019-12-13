package kz.dragau.larek.custom

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.widget.GridView

class ImageGridView: GridView {

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet): super(context,attrs){}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle){}

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        numColumns = if(newConfig!!.orientation == Configuration.ORIENTATION_LANDSCAPE) 6 else 3
    }
}