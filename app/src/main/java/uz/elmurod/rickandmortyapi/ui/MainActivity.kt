package uz.elmurod.rickandmortyapi.ui

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.elmurod.rickandmortyapi.adapter.RAMAdapter
import uz.elmurod.rickandmortyapi.adapter.RAMLoadStateAdapter
import uz.elmurod.rickandmortyapi.base.OnActionListener
import uz.elmurod.rickandmortyapi.checkinternet.NetworkChangeListener
import uz.elmurod.rickandmortyapi.databinding.ActivityMainBinding
import uz.elmurod.rickandmortyapi.util.Constants
import uz.elmurod.rickandmortyapi.viewmodel.AppViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: AppViewModel
    private lateinit var adapter: RAMAdapter
    private var broadcastReceiver: BroadcastReceiver? = null


    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[AppViewModel::class.java]
        broadcastReceiver = NetworkChangeListener(this, this)
        initRv()

        adapter.onOrderClickListener = OnActionListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.url)))
        }
        binding.btnTry.setOnClickListener {
            adapter.retry()
        }
        adapter.addLoadStateListener { loadState ->
            binding.apply {
                loadStateProgress.isVisible = loadState.source.refresh is LoadState.Loading
                rvRam.isVisible = loadState.source.refresh is LoadState.Loading
                btnTry.isVisible = loadState.source.refresh is LoadState.Error
                errorMassage.isVisible = loadState.source.refresh is LoadState.Error
                // not found
                rvRam.isVisible = !(loadState.source.refresh is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached &&
                        adapter.itemCount < 1)
            }

        }
        lifecycleScope.launchWhenStarted {
            viewModel.getRickAndMorty()
        }
    }

    private fun initRv() {
        adapter = RAMAdapter()
        binding.rvRam.setHasFixedSize(true)
        binding.rvRam.adapter = adapter.withLoadStateHeaderAndFooter(
            header = RAMLoadStateAdapter { adapter.retry() },
            footer = RAMLoadStateAdapter { adapter.retry() }
        )
    }


    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModel.getRickAndMorty.observe(this@MainActivity, Observer {
                adapter.submitData(lifecycle, it)
            })
        }
    }

    override fun onStart() {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(broadcastReceiver, intentFilter)
        super.onStart()
    }

    override fun onStop() {
        unregisterReceiver(broadcastReceiver)
        super.onStop()
    }
}