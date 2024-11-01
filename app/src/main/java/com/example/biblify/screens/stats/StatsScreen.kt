package com.example.biblify.screens.stats

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.biblify.model.BiblifyBooks
import com.example.biblify.navigation.BiblifyScreens
import com.example.biblify.screens.home.HomeScreenViewModel
import com.example.biblify.utils.LoadImageWithGlide
import com.example.biblify.utils.customFonts
import com.google.firebase.auth.FirebaseAuth

@Composable
fun StatsScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    var listOfBooks = emptyList<BiblifyBooks>()
    val currentUser = FirebaseAuth.getInstance().currentUser
    if (!viewModel.data.value.data.isNullOrEmpty()) {
        listOfBooks = viewModel.data.value.data!!.toList().filter { biblifyBook ->
            biblifyBook.userID == currentUser?.uid.toString()
        }

        Column(
            modifier = Modifier
        ) {
            StatsScreenTopBar(
                navController
            )
            StatsScreenContent(
                navController = navController,
                books = listOfBooks
            )
        }
    }
}

@Composable
fun StatsScreenTopBar(
    navController: NavController
) {
    Box (
        contentAlignment = Alignment.TopStart,
    ){
        Row (
            modifier = Modifier
                .padding(top = 30.dp, start = 5.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .padding(top = 30.dp, start = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back Arrow",
                    tint = Color.DarkGray
                )
            }
            Text(
                text = "Reading History",
                fontSize = 30.sp,
                fontFamily = customFonts(GoogleFont("Satisfy")),
                color = Color(255, 193, 7, 255),
                modifier = Modifier
                    .padding(top = 30.dp, start = 10.dp)
            )
        }
    }
}

@Composable
fun StatsScreenContent (
    books: List<BiblifyBooks>,
    navController: NavController
) {
    Column (
        modifier = Modifier
            .padding(top = 10.dp)
    ) {
        UserSection ()
        UserData(books)
        ReadBooks(books, navController)
    }
}

@Composable
fun UserSection() {
    val displayName = FirebaseAuth.getInstance().currentUser?.email?.split('@')?.get(0)
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
                Text (
                    text = "Hi, ${displayName?.capitalize()}!",
                    fontSize = 30.sp,
                    fontFamily = customFonts(GoogleFont("DM Serif Text")),
                    modifier = Modifier
                        .padding(top = 15.dp, bottom = 15.dp)
                )
            }
    }
}

@Composable
fun UserData(
    books: List<BiblifyBooks>
) {
    val booksRead = books.filter { book ->
        book.finishedReading == true
    }
    val currentlyReading = books.filter { book->
        book.startedReading == true && book.finishedReading != true
    }
    val numberOfBooksRead = booksRead.size
    val numberOfBooksReading = currentlyReading.size
    Card (
        modifier = Modifier
            .padding(5.dp),
        shape = CircleShape
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Your Stats",
                fontSize = 25.sp,
                fontFamily = customFonts(GoogleFont("Bebas Neue")),
                modifier = Modifier
                    .padding(5.dp)
            )
            HorizontalDivider(
                color = Color.LightGray,
                thickness = 1.dp,
            )
            Text(
                text = "You have read: $numberOfBooksRead",
                fontSize = 15.sp,
                fontFamily = customFonts(GoogleFont("Bebas Neue")),
                modifier = Modifier
                    .padding(top = 5.dp)
            )
            Text(
                text = "You are reading: $numberOfBooksReading",
                fontSize = 15.sp,
                fontFamily = customFonts(GoogleFont("Bebas Neue")),
                modifier = Modifier
                    .padding(5.dp)
            )
        }
    }
}

@Composable
fun ReadBooks(
    books: List<BiblifyBooks>,
    navController: NavController
) {
    val booksRead = books.filter { book ->
        book.finishedReading == true
    }

    LazyColumn {
        items(booksRead) {book->
            BookItem(
                book = book,
                navController = navController
            )
        }
    }
}

@Composable
fun BookItem(
    book: BiblifyBooks,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(start = 10.dp, end = 20.dp, top = 20.dp, bottom = 5.dp)
            .clickable {
                navController.navigate(BiblifyScreens.DetailsScreen.name + "/${book.googleBookID}")
            },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 25.dp
        )
    ) {
        Row(
            modifier = Modifier
        ) {
            val imageURL: String = if (book.photoURL!!.isEmpty()) {
                "https://images.unsplash.com/photo-1541963463532-d68292c34b19?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=80&q=80"
            } else {
                book.photoURL.toString()
            }
            LoadImageWithGlide(
                imageUrl = imageURL,
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxSize()
            )
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(0.7f)
            ) {
                Text(
                    text = book.title!!,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier
                        .padding(start = 5.dp, top = 3.dp, end = 5.dp)
                )
                Text(
                    text = "Author: ${book.author}",
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier
                        .padding(start = 3.dp)
                )
                Text(
                    text = "Date: ${book.publishedDate}",
                    fontSize = 13.sp,
                    fontStyle = FontStyle.Italic,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier
                        .padding(start = 3.dp)
                )
                Text(
                    text = "${book.categories}",
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier
                        .padding(start = 3.dp)
                )
            }
        }
    }
}