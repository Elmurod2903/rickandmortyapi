package uz.elmurod.rickandmortyapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import uz.elmurod.rickandmortyapi.adapter.viewholder.RAMViewHolder
import uz.elmurod.rickandmortyapi.base.BaseViewHolder
import uz.elmurod.rickandmortyapi.base.OnActionListener
import uz.elmurod.rickandmortyapi.data.RAM
import uz.elmurod.rickandmortyapi.databinding.ItemViewRamBinding

class RAMAdapter :
    PagingDataAdapter<RAM, BaseViewHolder<RAM>>(RAM.Companion.Comparator) {
    var onOrderClickListener: OnActionListener<RAM>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<RAM> {
        return RAMViewHolder(
            ItemViewRamBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) {
            onOrderClickListener?.onClick(it)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<RAM>, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}