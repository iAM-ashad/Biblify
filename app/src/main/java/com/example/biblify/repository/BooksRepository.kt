package com.example.biblify.repository

import com.example.biblify.data.Resource
import com.example.biblify.model.Item
import com.example.biblify.network.BiblifyAPI
import javax.inject.Inject

class BooksRepository @Inject constructor(private val api: BiblifyAPI) {

    suspend fun getBooks(searchQuery: String): Resource<List<Item>> {
        return try {
            Resource.Loading(data = "Loading...")
            val itemList = api.getAllBooks(searchQuery).items
            if (itemList.isNotEmpty()) {
                Resource.Loading(false)
            }
            Resource.Success(data = itemList)
        } catch (ex: Exception) {
            Resource.Error(ex.message.toString())
        }
    }
    suspend fun getBookInfo(bookID: String): Resource<Item> {
        val response = try {
            Resource.Loading(data = true)
            api.getBookInfo(bookID)
        } catch (ex: Exception) {
            return Resource.Error(ex.message.toString())
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }
}