package com.example.biblify.model

data class Item(
    val accessInfo: AccessInfo,
    val eTag: String,
    val id: String,
    val kind: String,
    val saleInfo: SaleInfo,
    val searchInfo: SearchInfo,
    val selfLink: String,
    val volumeInfo: VolumeInfo
)