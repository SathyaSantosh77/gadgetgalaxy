package com.example.gadgetgalaxy.ui.main.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gadgetgalaxy.common.Resource
import com.example.gadgetgalaxy.data.model.ProductUI
import com.example.gadgetgalaxy.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private var _detailState = MutableLiveData<DetailState>()
    val detailState: LiveData<DetailState>
        get() = _detailState


    fun getProductDetail(id: Int) {
        viewModelScope.launch {
            _detailState.value = DetailState.Loading
            val result = productRepository.getProductDetail(id)
            if (result is Resource.Success) {
                _detailState.value = DetailState.Data(result.data)
            } else if (result is Resource.Error) {
                _detailState.value = DetailState.Error(result.throwable)
            }
        }
    }


}

sealed interface DetailState {
    object Loading : DetailState
    data class Data(val product: ProductUI) : DetailState
    data class Error(val throwable: Throwable) : DetailState
}