package uz.elmurod.rickandmortyapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.elmurod.rickandmortyapi.databinding.LoadStateViewBinding

class RAMLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<RAMLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding =
            LoadStateViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

    inner class LoadStateViewHolder(private val binding: LoadStateViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.loadStateRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                loadStateProgress.isVisible = loadState is LoadState.Loading
                loadStateErrorMessage.isVisible = loadState !is LoadState.Loading
                loadStateRetry.isVisible = loadState !is LoadState.Loading
            }
            if (loadState is LoadState.Error) {
                binding.loadStateErrorMessage.text = loadState.error.localizedMessage
            }

        }

    }

}