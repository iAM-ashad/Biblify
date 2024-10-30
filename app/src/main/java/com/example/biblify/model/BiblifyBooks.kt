package com.example.biblify.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName

data class BiblifyBooks (
    @Exclude var id: String? = null,
    var title: String? = null,
    var author: String? = null,
    var notes: String? = null,
    @get:PropertyName("book_photo_url")
    @set:PropertyName("book_photo_url")
    var photoURL: String? = null,
    var categories: String? = null,
    @get:PropertyName("published_date")
    @set:PropertyName("published_date")
    var publishedDate: String? = null,
    var rating: Int? = null,
    var description: String? = null,
    @get:PropertyName("page_count")
    @set:PropertyName("page_count")
    var pageCount: String? = null,
    @get:PropertyName("started_reading_at")
    @set:PropertyName("started_reading_at")
    var startedReadingAt: Timestamp? = null,
    @get:PropertyName("finished_reading_at")
    @set:PropertyName("finished_reading_at")
    var finishedReadingAt: Timestamp? = null,
    @get:PropertyName("started_reading")
    @set:PropertyName("started_reading")
    var startedReading: Boolean? = null,
    @get:PropertyName("finished_reading")
    @set:PropertyName("finished_reading")
    var finishedReading: Boolean? = null,
    @get:PropertyName("user_id")
    @set:PropertyName("user_id")
    var userID: String? = null,
    @get:PropertyName("google_books_id")
    @set:PropertyName("google_books_id")
    var googleBookID: String? = null,
)
