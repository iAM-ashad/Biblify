package com.example.biblify.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.biblify.R
import com.example.biblify.component.BiblifyFAB
import com.example.biblify.component.BiblifyTopBar
import com.example.biblify.component.BookListArea
import com.example.biblify.component.ListCard
import com.example.biblify.component.TitleSection
import com.example.biblify.model.BiblifyBooks
import com.example.biblify.navigation.BiblifyScreens
import com.example.biblify.ui.theme.BiblifyTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold (
        topBar = {
            BiblifyTopBar(
                title = stringResource(R.string.app_name),
                navController = navController,
                actionIcon = painterResource(R.drawable.logout),
                actionOnClick = {
                    Firebase.auth.signOut().run {
                        navController.navigate(BiblifyScreens.LoginScreen.name)
                    }
                },
                showProfile = true
            )
        },
        floatingActionButton = {
            BiblifyFAB () {
                navController.navigate(BiblifyScreens.SearchScreen.name)
            }
        }
    ) {contentPadding->
        Box(modifier = Modifier.padding(contentPadding)) {
            HomeContent(
                modifier = Modifier.fillMaxSize(),
                navController = navController
            )
        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val listOfBooks = listOf(
        BiblifyBooks(id = "1", title = "Winners are NEVER Quitters", author = "Ashad Ansari"),
        BiblifyBooks(id = "2", title = "Proudly Gay", author = "Ayush Raj"),
        BiblifyBooks(id = "3", title = "Why Veg is better: 100 Lies", author = "Debashish Nayak"),
        BiblifyBooks(id = "4", title = "Valorant...", author = "Sohan Saha"),
        BiblifyBooks(id = "5", title = "Rich Dad, Richer Dad", author = "Ankit Raj"),
        BiblifyBooks(id = "6", title = "Dihadi Majdoor", author = "Average Bihari Boy")
    )


    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName = if (!email.isNullOrEmpty()) {
        email.split("@")[0]
    }else {
        "N/A"
    }
    Column (
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(2.dp)
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TitleSection(
                label = "Your Reading Activity",
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp)
            )
            Box(
                modifier = Modifier
                    .weight(.3f),
            ) {
               Column (
                   horizontalAlignment = Alignment.CenterHorizontally,
                   modifier = Modifier
               ) {
                   Image(
                       painter = painterResource(R.drawable.userpfp),
                       contentDescription = "User Profile Picture",
                       contentScale = ContentScale.Crop,
                       modifier = Modifier
                           .aspectRatio(1f)
                           .scale(0.7f)
                           .clip(CircleShape)
                           .clickable {
                               navController.navigate(BiblifyScreens.StatsScreen.name)
                           }
                   )
                   Text (
                       text = currentUserName.capitalize(locale = Locale.current),
                       fontSize = 15.sp,
                       fontWeight = FontWeight.Bold,
                       maxLines = 1,
                       overflow = TextOverflow.Clip
                   )
               }
            }
        }
        ListCard()
        CurrentActivity(books = listOf(), navController = navController)
        TitleSection(label = "Reading List")
        BookListArea(listOfBooks = listOfBooks, navController = navController)

    }
}



@Composable
fun CurrentActivity(
    books: List<BiblifyBooks>,
    navController: NavController
) {

}

@Preview(showBackground = true)
@Composable
fun Preview() {
    BiblifyTheme {
        val navController = rememberNavController()
        HomeContent(navController = navController)
    }
}
