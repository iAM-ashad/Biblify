package com.example.biblify.screens.detail

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.biblify.R
import com.example.biblify.component.BiblifyTopBar
import com.example.biblify.data.Resource
import com.example.biblify.model.BiblifyBooks
import com.example.biblify.model.Item
import com.example.biblify.ui.theme.BiblifyTheme
import com.example.biblify.utils.customFonts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun DetailsScreen(
    bookID: String,
    navController: NavController,
    viewModel: DetailScreenViewModel = hiltViewModel()
) {
    val bookInfo = produceState<Resource<Item>>(initialValue = Resource.Loading()) {
        value = viewModel.getBookInfo(bookID)
    }.value
    val googleBookID = bookInfo.data?.id

    if (bookInfo.data == null) {
        LinearProgressIndicator (
            color = ProgressIndicatorDefaults.circularColor,
            trackColor = ProgressIndicatorDefaults.linearTrackColor
        )
    }
    else {Scaffold (
        topBar = {
            BiblifyTopBar(
                title = "",
                navController = navController,
                showProfile = false,
                navIcon = Icons.AutoMirrored.Default.ArrowBack,
                navIconPressed = {
                    navController.popBackStack()
                }
            )
        },
        floatingActionButton = {
            IconButton(
                onClick =
                {
                    val book = BiblifyBooks(
                        title = bookInfo.data.volumeInfo.title,
                        author = bookInfo.data.volumeInfo.authors.toString(),
                        description = bookInfo.data.volumeInfo.description,
                        categories = bookInfo.data.volumeInfo.categories.toString(),
                        notes = "",
                        photoURL = bookInfo.data.volumeInfo.imageLinks.thumbnail,
                        publishedDate = bookInfo.data.volumeInfo.publishedDate,
                        pageCount = bookInfo.data.volumeInfo.pageCount.toString(),
                        rating = 0,
                        googleBookID = googleBookID,
                        userID = FirebaseAuth.getInstance().currentUser?.uid.toString()

                    )
                    saveToFireStore(book, navController)
                }) {
                Icon (
                    painter = painterResource(R.drawable.bookmarksave),
                    contentDescription = "Save this book",
                    modifier = Modifier
                        .scale(.8f)
                )
            }
        }
    ) {contentPadding->
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            Column (
                modifier = Modifier
            ) {
                Box (
                    contentAlignment = Alignment.TopEnd,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Card (
                        modifier = Modifier
                            .height(200.dp)
                            .width(150.dp)
                            .padding(end = 10.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        )
                    ) {
                        /*Image (
                            painter = painterResource(R.drawable.dummybookcover),
                            contentDescription = "Book Image",
                            modifier = Modifier
                                .padding(7.dp)
                                .fillMaxSize()
                        )*/
                        val imageURL = bookInfo.data?.volumeInfo?.imageLinks?.smallThumbnail
                        AsyncImage (
                            model = imageURL,
                            contentDescription = null,
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = 30.dp))
                Text(
                    text = bookInfo.data?.volumeInfo?.title.toString(),
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = customFonts(GoogleFont("Teko")),
                    lineHeight = 35.sp,
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp )
                )
                Text(
                    text = bookInfo.data?.volumeInfo?.authors.toString(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = customFonts(GoogleFont("Anton")),
                    color = Color.LightGray,
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp, top = 10.dp)
                )
                HorizontalDivider (
                    modifier = Modifier
                        .padding(20.dp)
                )
                Text(
                    text = "About",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    fontFamily = customFonts(GoogleFont("Bebas Neue")),
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp, top = 10.dp)
                )
                val description = HtmlCompat.fromHtml(bookInfo.data.volumeInfo.description,HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                val scrollState = rememberScrollState()
                Box(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 10.dp)
                ) {
                    Text(
                        text = description,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = customFonts(GoogleFont("Dosis")),
                        color = Color(150, 147, 147, 255),
                        modifier = Modifier

                    )
                }
            }
        }
    }}

}

fun saveToFireStore(
    book: BiblifyBooks,
    navController: NavController
) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")
    if (book.toString().isNotEmpty()) {
        dbCollection.add(book)
            .addOnSuccessListener {docRef->
                val docID = docRef.id
                dbCollection.document(docID)
                    .update(hashMapOf("id" to docID) as Map<String, Any>)
                    .addOnCompleteListener {task->
                        if (task.isSuccessful) {
                            navController.popBackStack()
                        }
                    }
                    .addOnFailureListener {
                        Log.w("Error", "Saving to Firebase: Error updating doc")
                    }
            }
    }else {}
}

@Preview
@Composable
fun PreviewThis() {
    BiblifyTheme {
        val navController = rememberNavController()
        //DetailsScreen(navController = navController)
    }
}