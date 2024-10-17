package com.example.biblify.screens.detail

import androidx.lifecycle.ViewModel
import com.example.biblify.data.Resource
import com.example.biblify.model.Item
import com.example.biblify.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor
    (private val repository: BooksRepository): ViewModel() {
        suspend fun getBookInfo(bookID: String): Resource<Item> {
            return repository.getBookInfo(bookID)
        }
}