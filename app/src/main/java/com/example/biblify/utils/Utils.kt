package com.example.biblify.utils

import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import java.text.DateFormat

fun formatDate(timestamp: com.google.firebase.Timestamp): String {
    return DateFormat.getDateInstance().format(timestamp.toDate()).toString().split(",")[0] // March 12
}

@Composable
fun LoadImageWithGlide(
    imageScale: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP,
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            ImageView(context).apply {
                scaleType = imageScale
            }
        },
        modifier = modifier,
        update = { imageView ->
            // Use Glide to load the image
            Glide.with(imageView.context)
                .load(imageUrl)
                .into(imageView)
        }
    )
}