package com.example.biblify.repository

import android.util.Log
import com.example.biblify.data.DataOrException
import com.example.biblify.model.BiblifyBooks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireRepository @Inject
    constructor(
        private val queryBook: Query
    ) {
        suspend fun getAllBooksFromDB(): DataOrException<List<BiblifyBooks>, Boolean, Exception> {
            val dataOrException = DataOrException<List<BiblifyBooks>, Boolean, Exception>()

            try {
                dataOrException.loading = true
                dataOrException.data = queryBook.get().await().documents.map { documentSnapshot ->
                    documentSnapshot.toObject(BiblifyBooks::class.java)!!
                }
                Log.d("DEBUGSHIT", dataOrException.data.toString())
                if (!dataOrException.data.isNullOrEmpty()) {
                    dataOrException.loading = false
                }
            } catch (ex: FirebaseFirestoreException) {
                dataOrException.e = ex
                Log.e("FIREBASE", "Error fetching books: ${ex.message}")
            }
            return dataOrException
        }
    suspend fun getBookByGoogleId(googleBookId: String): DataOrException<BiblifyBooks, Boolean, Exception> {
        val dataOrException = DataOrException<BiblifyBooks, Boolean, Exception>()

        try {
            dataOrException.loading = true
            // Firestore query to find the book by its Google Book ID
            val result = FirebaseFirestore.getInstance().collection("books")
                .whereEqualTo("google_books_id", googleBookId) // Query based on Google Book ID
                .get()
                .await()

            if (!result.isEmpty) {
                dataOrException.data = result.documents[0].toObject(BiblifyBooks::class.java)
                dataOrException.loading = false
            } else {
                // If no document was found with the given Google Book ID
                dataOrException.loading = false
                dataOrException.data = null
                Log.e("FIREBASE", "Book not found for Google Book ID: $googleBookId")
            }
        } catch (ex: FirebaseFirestoreException) {
            dataOrException.e = ex
            Log.e("FIREBASE", "Error fetching book: ${ex.message}")
        }

        return dataOrException
    }
}
