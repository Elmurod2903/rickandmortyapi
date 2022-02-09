package uz.elmurod.rickandmortyapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.elmurod.rickandmortyapi.data.RAM
import uz.elmurod.rickandmortyapi.repository.RickAndMortyRepository
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val repository: RickAndMortyRepository) :
    ViewModel() {
    var job: Job? = null

    private val _getRickAndMorty = MutableLiveData<PagingData<RAM>>()
    val getRickAndMorty: LiveData<PagingData<RAM>> = _getRickAndMorty

    @ExperimentalPagingApi
    fun getRickAndMorty() {
        job?.cancel()
        job = viewModelScope.launch {
            repository.getRickAndMorty()?.cachedIn(viewModelScope)?.collectLatest {
                _getRickAndMorty.postValue(it)
            }
        }
    }
}