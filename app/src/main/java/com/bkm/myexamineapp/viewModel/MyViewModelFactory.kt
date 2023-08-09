package com.bkm.myexamineapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bkm.myexamineapp.repository.MyRepository

class MyViewModelFactory(private val myRepository: MyRepository) :ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            return ProductViewModel(myRepository) as T
        }
        throw IllegalArgumentException("Error")
    }
}