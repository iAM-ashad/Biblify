package com.example.biblify.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.biblify.data.DataOrException
import com.example.biblify.model.BiblifyBooks
import com.example.biblify.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject
    constructor (private val  repository: FireRepository): ViewModel() {
    val data: MutableState<DataOrException<List<BiblifyBooks>, Boolean, Exception>>
            = mutableStateOf(DataOrException(listOf(), true,Exception("")))
    val singleBookData: MutableState<DataOrException<BiblifyBooks, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception("")))

    init {
        getAllBooksFromDatabase()
    }

    private fun getAllBooksFromDatabase() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllBooksFromDB()
            if (!data.value.data.isNullOrEmpty()) data.value.loading = false
            Log.d("GET", "getAllBooksFromDatabase: ${data.value.data?.toList().toString()}")
        }
    }
    fun getBookByGoogleId(googleBookId: String) {
        viewModelScope.launch {
            singleBookData.value = repository.getBookByGoogleId(googleBookId)
        }
    }
}