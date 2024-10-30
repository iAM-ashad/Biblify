package com.example.biblify.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.biblify.R
import com.example.biblify.component.BiblifyFAB
import com.example.biblify.component.BiblifyTopBar
import com.example.biblify.component.BookListArea
import com.example.biblify.component.HorizontalScrollableComponent
import com.example.biblify.component.TitleSection
import com.example.biblify.model.BiblifyBooks
import com.example.biblify.navigation.BiblifyScreens
import com.example.biblify.ui.theme.BiblifyTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun HomeScreen (
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    Scaffold (
        topBar = {
            BiblifyTopBar(
                title = stringResource(R.string.app_name),
                navController = navController,
                actionIcon = painterResource(R.drawable.logout__2_),
                actionOnClick = {
                    Firebase.auth.signOut().run {
                        navController.navigate(BiblifyScreens.LoginScreen.name)
                    }
                },
                showProfile = true
            )
        },
        floatingActionButton = {
            BiblifyFAB {
                navController.navigate(BiblifyScreens.SearchScreen.name)
            }
        }
    ) {contentPadding->
        Box(modifier = Modifier.padding(contentPadding)) {
            HomeContent(
                modifier = Modifier.fillMaxSize(),
                navController,
                viewModel
            )
        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeScreenViewModel
) {

    var listOfBooks = emptyList<BiblifyBooks>()
    val currentUser = FirebaseAuth.getInstance().currentUser

    if (!viewModel.data.value.data.isNullOrEmpty()) {
            listOfBooks = viewModel.data.value.data!!.toList().filter { biblifyBook->
                biblifyBook.userID == currentUser?.uid.toString()
        }
        Column (
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .padding(2.dp)
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TitleSection(
                    isTopSection = true,
                    label = "Your Reading Activity",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 5.dp)
                ) {
                    navController.navigate(BiblifyScreens.StatsScreen.name)
                }
            }
            CurrentActivity(books = listOfBooks, navController = navController)
            TitleSection(label = "Reading List", modifier = Modifier.padding(5.dp))
            BookListArea(listOfBooks = listOfBooks, navController = navController)
        }
    }
    else {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ){
            CircularProgressIndicator()
        }
    }
}



@Composable
fun CurrentActivity(
    books: List<BiblifyBooks>,
    navController: NavController
) {
    val readingNowList = books.filter { book->
        book.startedReading != null && book.finishedReading != true
    }
    HorizontalScrollableComponent(
        listOfBooks = readingNowList
    ) {
        navController.navigate(BiblifyScreens.UpdateScreen.name + "/$it")
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    BiblifyTheme {
    }
}
