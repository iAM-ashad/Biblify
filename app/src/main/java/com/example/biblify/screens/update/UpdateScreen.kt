package com.example.biblify.screens.update
import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.biblify.component.BiblifyTopBar
import com.example.biblify.component.RatingBar
import com.example.biblify.component.showToast
import com.example.biblify.model.BiblifyBooks
import com.example.biblify.navigation.BiblifyScreens
import com.example.biblify.screens.home.HomeScreenViewModel
import com.example.biblify.utils.LoadImageWithGlide
import com.example.biblify.utils.customFonts
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun UpdateScreen(
    navController: NavController,
    googleBookId: String,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(googleBookId) {
        viewModel.getBookByGoogleId(googleBookId)
    }
    val bookInfo = viewModel.singleBookData.value

    Scaffold(
        topBar = {
            BiblifyTopBar(
                title = "Update Screen",
                navController = navController,
                showProfile = false,
                navIcon = Icons.AutoMirrored.Default.ArrowBack,
                navIconPressed = {
                    navController.popBackStack()
                }
            )
        }
    ) { contentPadding ->
        if (!bookInfo.loading!!) {
            Box(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
                    .padding(top = 20.dp)
            ) {
               UpdateScreenContent(
                   navController = navController,
                   bookInfo = bookInfo.data!!
               )
            }
        } else {
            LinearProgressIndicator()
        }
    }
}

@Composable
fun UpdateScreenContent(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    bookInfo: BiblifyBooks
) {
    BookSection(
        navController = navController,
        viewModel = viewModel,
        book = bookInfo
    )
}

@Composable
fun BookSection(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    book: BiblifyBooks
) {
    val context = LocalContext.current
    val ratingVal = remember {
        mutableIntStateOf(book.rating!!)
    }
    val isFinishedReading = remember {
        mutableStateOf(false)
    }
    val notesText = remember {
        mutableStateOf("")
    }
    val isStartedReading = remember {
        mutableStateOf(false)
    }

    val changedNotes = book.notes != notesText.value
    val changedRating = book.rating?.toInt() != ratingVal.value
    val isFinishedTimeStamp = if (isFinishedReading.value) Timestamp.now() else book.finishedReading
    val isStartedTimeStamp = if (isStartedReading.value) Timestamp.now() else book.startedReading

    val bookUpdate = changedNotes || changedRating || isStartedReading.value || isFinishedReading.value

    val bookToUpdate = hashMapOf(
        "finished_reading_at" to isFinishedTimeStamp,
        "started_reading_at" to isStartedTimeStamp,
        "rating" to ratingVal.intValue,
        "notes" to notesText.value).toMap()
    Box (
        modifier = Modifier
            .padding(top = 40.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Card (
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 25.dp
            )
        ) {
            Column (
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color.Transparent)
                    .padding(top = 175.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    book.rating?.let {
                        RatingBar(
                            modifier = Modifier
                                .padding(bottom = 15.dp),
                            rating = it,
                        ) { rating->
                            ratingVal.intValue = rating
                            Log.d("RATING_Tag", "${ratingVal.intValue}")
                        }
                    }
                }
                Text(
                    text = book.title!!,
                    fontSize = 20.sp,
                    fontFamily = customFonts(GoogleFont("DM Serif Text")),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                )
                Text(
                    text = "[${book.publishedDate!!}]",
                    fontSize = 20.sp,
                    fontFamily = customFonts(GoogleFont("DM Serif Text"))
                )
                ThoughtBox(
                    book = book,
                    viewModel = viewModel,
                ) {
                    notesText.value = it
                    showToast(context, "Thoughts added: $it")
                }
                ReadingOrStart(
                    book = book,
                    isStarted = isStartedReading.value,
                    started = {
                        isStartedReading.value = true
                        showToast(context, "You started reading: ${book.title}")
                    },
                    finished = {
                        isFinishedReading.value = true
                        showToast(context, "You finished reading: ${book.title}")
                    }
                )
                UpdateDeleteButtonRow(
                    onUpdateClicked = {FirebaseFirestore.getInstance()
                        .collection("books")
                        .document(book.id!!)
                        .update(bookToUpdate)
                        .addOnCompleteListener {
                            showToast(context, "Book Updated Successfully!")
                            navController.navigate(BiblifyScreens.HomeScreen.name)
                        }.addOnFailureListener{
                            Log.w("Error", "Error updating document" , it)
                        }
                    },
                    onDeleteClicked = {
                        FirebaseFirestore.getInstance()
                            .collection("books")
                            .document(book.id!!)
                            .delete()
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    navController.navigate(BiblifyScreens.HomeScreen.name)
                                }
                            }

                    }
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            val imgUrl = book.photoURL.toString()
            LoadImageWithGlide(
                imageScale = ImageView.ScaleType.CENTER_CROP,
                imageUrl = imgUrl,
                modifier = Modifier
                    .width(125.dp)
                    .height(175.dp)
                    .offset(y = (-60).dp)
            )
        }
    }
}

@Composable
fun ThoughtBox(
    book: BiblifyBooks,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onDone: (String)-> Unit = {}
) {
    val thought = remember {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    ThoughtField(
        savedThoughts = book.notes!!,
        valueState = thought,
        onAction = KeyboardActions {
            onDone(thought.value)
            keyboardController?.hide()
            ImeAction.Done
        }
    )
}

@Composable
fun ThoughtField(
    savedThoughts: String,
    valueState: MutableState<String>,
    onAction: KeyboardActions
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        shape = RoundedCornerShape(15.dp),
        label = {
            Text(
                text = "Thoughts?"
            )
        },
        placeholder = {
            Text(
                text = savedThoughts
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = onAction,
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth()
            .padding(25.dp)

    )
}

@Composable
fun ReadingOrStart(
    book: BiblifyBooks,
    isStarted: Boolean,
    started: ()-> Unit,
    finished: ()-> Unit
) {
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(bottom = 20.dp)
    ){
       if (book.startedReading == null) {
           if (!isStarted) {
               Text(
                   text = "Start Reading",
                   color = Color.Blue,
                   fontWeight = FontWeight.Bold,
                   modifier = Modifier
                       .clickable {
                           started.invoke()
                       }
                       .padding(5.dp)
               )
           }
           else{
               Text(
                   text = "Started Reading",
                   color = Color.Blue,
                   fontWeight = FontWeight.Bold,
                   modifier = Modifier
                       .clickable {
                           finished.invoke()
                       }
                       .padding(5.dp)
               )
           }
       } else {
           Text(
               text = "Finish Reading",
               color = Color.Blue,
               fontWeight = FontWeight.Bold,
               modifier = Modifier
                   .clickable {
                       started.invoke()
                   }
                   .padding(5.dp)
           )
       }
    }
}
@Composable
fun UpdateDeleteButtonRow(
    onUpdateClicked: ()-> Unit,
    onDeleteClicked: ()-> Unit,
) {
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(bottom = 20.dp)
    ) {
        Button(
            onClick = onUpdateClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF009688)
            )
        ) {
            Text(
                text = "Update",
                modifier = Modifier
                    .padding(5.dp)
            )
        }
        Spacer(modifier = Modifier.padding(horizontal = 15.dp))
        Button(
            onClick = onDeleteClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF009688)
            )
        ) {
            Text(
                text = "Delete",
                modifier = Modifier
                    .padding(5.dp)
            )
        }
    }
}