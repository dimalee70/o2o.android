package kz.dragau.larek.ui.adapters.images

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

abstract class BaseImageAdapter<T>(private var context: Context, private var list: Array<T>): BaseAdapter() {

    override fun getItem(position: Int): Any {
        return list[position]!!
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return list.size
    }
}