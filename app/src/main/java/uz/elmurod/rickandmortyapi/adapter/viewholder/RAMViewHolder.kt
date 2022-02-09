package uz.elmurod.rickandmortyapi.adapter.viewholder

import com.bumptech.glide.Glide
import uz.elmurod.rickandmortyapi.R
import uz.elmurod.rickandmortyapi.base.BaseViewHolder
import uz.elmurod.rickandmortyapi.data.RAM
import uz.elmurod.rickandmortyapi.databinding.ItemViewRamBinding

class RAMViewHolder(
    private val binding: ItemViewRamBinding,
    val onClick: (ram: RAM) -> Unit
) : BaseViewHolder<RAM>(binding.root) {
    override fun bind(item: RAM) = with(binding) {
        super.bind(item)
        nameRam.text = item.name
        statusRam.text = item.status
        Glide.with(context).load(item.image)
            .centerCrop()
            .error(R.drawable.not_image_url)
            .into(imageRam)
        root.setOnClickListener {
            onClick(item)
        }
    }
}