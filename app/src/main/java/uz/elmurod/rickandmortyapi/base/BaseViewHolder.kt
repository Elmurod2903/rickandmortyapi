package uz.elmurod.rickandmortyapi.base

import android.content.Context
import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    val context: Context = view.context

    @CallSuper
    open fun bind(item: T) {

    }
}