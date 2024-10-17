package com.example.biblify.repository

import com.example.biblify.data.DataOrException
import com.example.biblify.model.Item
import com.example.biblify.network.BiblifyAPI
import javax.inject.Inject

class BiblifyRepository @Inject constructor(private val api: BiblifyAPI) {

    private val dataOrException =
        DataOrException<List<Item>, Boolean, Exception>()

    private val bookInfoDataOrException = DataOrException<Item, Boolean, Exception>()

    suspend fun getBooks(searchQuery: String): DataOrException<List<Item>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllBooks(searchQuery).items
            if (dataOrException.data!!.isNotEmpty()) {
                dataOrException.loading = false
            }
        } catch (e: Exception) {
            dataOrException.e = e
        }
        return dataOrException
    }
    suspend fun getBookInfo(bookID: String): DataOrException<Item, Boolean, Exception> {
        val response = try {
            bookInfoDataOrException.loading = true
            bookInfoDataOrException.data = api.getBookInfo(bookID)

            if (bookInfoDataOrException.data.toString().isNotEmpty()) {
                bookInfoDataOrException.loading = false
            } else  {
            }

        } catch (e:Exception) {
            bookInfoDataOrException.e = e
        }
        return bookInfoDataOrException
    }

}