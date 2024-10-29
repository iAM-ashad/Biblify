package com.example.biblify.screens.search

import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.biblify.component.BiblifyTopBar
import com.example.biblify.component.SearchBar
import com.example.biblify.model.Item
import com.example.biblify.navigation.BiblifyScreens
import com.example.biblify.ui.theme.BiblifyTheme
import com.example.biblify.utils.LoadImageWithGlide

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            BiblifyTopBar(
                title = "Search for a Book",
                navController = navController,
                showProfile = false,
                navIcon = Icons.AutoMirrored.Default.ArrowBack,
                navIconPressed = {
                    navController.popBackStack()
                }
            )
        },

    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Column (
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
            ) {
                SearchBar(
                    viewModel,
                    loading = viewModel.isLoading
                ) { query->
                    viewModel.searchBooks(query)
                }
                SearchScreenContent(navController, viewModel)
            }
        }
    }
}

@Composable
fun SearchScreenContent (
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val listOfBooks = viewModel.list

    if (viewModel.isLoading) {
        Log.d("LOADING_TRUE", "BOOKS ARE LOADING!!!")
        LinearProgressIndicator()
    }
    else {
        LazyColumn (
            modifier = Modifier
        ) {
            items(listOfBooks) {book->
                SearchItem(book, navController)
            }
        }
    }

}

@Composable
fun SearchItem (
    book: Item,
    navController: NavController
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(start = 10.dp, end = 20.dp, top = 20.dp, bottom = 5.dp)
            .clickable {
                navController.navigate(BiblifyScreens.DetailsScreen.name+"/${book.id}")
            },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 25.dp
        )
    ) {
        Row (
            modifier = Modifier
        ) {
            val imageURL: String = if (book.volumeInfo.imageLinks?.thumbnail.isNullOrEmpty()) {
                "https://images.unsplash.com/photo-1541963463532-d68292c34b19?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=80&q=80"
            } else {
                book.volumeInfo.imageLinks.smallThumbnail
            }
            LoadImageWithGlide(
                imageUrl = imageURL,
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxSize()
            )
            Column (
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(0.7f)
            ) {
                Text(
                    text = book.volumeInfo.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier
                        .padding(start = 5.dp, top = 3.dp, end = 5.dp)
                )
                Text(
                    text = "Author: ${book.volumeInfo.authors}",
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier
                        .padding(start = 3.dp)
                )
                Text(
                    text = "Date: ${book.volumeInfo.publishedDate}",
                    fontSize = 13.sp,
                    fontStyle = FontStyle.Italic,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier
                        .padding(start = 3.dp)
                )
                Text(
                    text = "${book.volumeInfo.categories}",
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

@Preview(showBackground = true)
@Composable
fun PreviewSearch() {
    BiblifyTheme {
    }
}