package com.example.biblify.network

import com.example.biblify.model.Book
import com.example.biblify.model.Item
import com.example.biblify.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton()
interface BiblifyAPI {

    @GET("volumes")
    suspend fun getAllBooks(
        @Query("q") query: String,
        @Query("key") key: String = Constants.API_KEY
    ): Book

    @GET("volumes/{bookID}")
    suspend fun getBookInfo(@Path("bookID") bookID: String): Item
}