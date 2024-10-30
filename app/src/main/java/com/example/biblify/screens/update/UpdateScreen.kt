package com.example.biblify.screens.update
import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.biblify.component.RatingBar
import com.example.biblify.component.showToast
import com.example.biblify.model.BiblifyBooks
import com.example.biblify.navigation.BiblifyScreens
import com.example.biblify.screens.home.HomeScreenViewModel
import com.example.biblify.utils.LoadImageWithGlide
import com.example.biblify.utils.customFonts
import com.example.biblify.utils.formatDate
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

    Box (
        modifier = Modifier
            .fillMaxSize()
    ){
        if (!bookInfo.loading!!) {
            val imgUrl = bookInfo.data!!.photoURL.toString()
            LoadImageWithGlide(
                imageScale = ImageView.ScaleType.FIT_XY,
                imageUrl = imgUrl,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(20.dp)
            )
            Column (
                modifier = Modifier
                    .fillMaxSize()
            ) {
                UpdateScreenTopBar(
                    navController
                )
                    Box(
                        modifier = Modifier
                            .padding(top = 100.dp)
                            .fillMaxSize()
                    ) {
                        UpdateScreenContent(
                            navController = navController,
                            bookInfo = bookInfo.data!!
                        )
                    }
            }
        } else {
            LinearProgressIndicator()
        }
    }
}
@Composable
fun UpdateScreenTopBar(
    navController: NavController
) {
    Box (
        contentAlignment = Alignment.TopStart,
    ){
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
        mutableStateOf(book.finishedReading)
    }
    val notesText = remember {
        mutableStateOf("")
    }
    val isStartedReading = remember {
       mutableStateOf(book.startedReading)
    }

    val changedNotes = book.notes != notesText.value
    val changedRating = book.rating?.toInt() != ratingVal.intValue
    val isFinishedTimeStamp = if (isFinishedReading.value == true) Timestamp.now() else book.finishedReading
    val isStartedTimeStamp = if (isStartedReading.value == true) Timestamp.now() else book.startedReading

    val bookUpdate = changedNotes || changedRating || isStartedReading.value == true || isFinishedReading.value == true

    val bookToUpdate = hashMapOf(
        "finished_reading_at" to isFinishedTimeStamp,
        "started_reading_at" to isStartedTimeStamp,
        "started_reading" to isStartedReading.value,
        "finished_reading" to isFinishedReading.value,
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
            shape = RoundedCornerShape(
                topStart = 50.dp,
                topEnd = 50.dp
            ),
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
                    thoughts = if (book.notes.isNullOrEmpty()) {
                        ""
                    } else {
                        book.notes!!
                    }
                ) {thoughts->
                    notesText.value = thoughts
                }
                StartFinishRow(
                    book = book,
                    onStartClick = {
                        isStartedReading.value = true
                    },
                    onReadClick = {
                        isFinishedReading.value = true
                    }
                )
                UpdateDeleteButtonRow(
                    onUpdateClicked = {
                        if (bookUpdate) {
                        FirebaseFirestore.getInstance()
                        .collection("books")
                        .document(book.id!!)
                        .update(bookToUpdate)
                        .addOnCompleteListener {
                            showToast(context, "Book Updated Successfully!")
                            navController.navigate(BiblifyScreens.HomeScreen.name)
                        }.addOnFailureListener{
                            Log.w("Error", "Error updating document" , it)
                        } }
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
    thoughts: String = "Great Book",
    onDone: (String)-> Unit = {}
) {
    val thought = rememberSaveable {
        mutableStateOf(thoughts)
    }
    val valid = remember(thought.value) {
        thought.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    ThoughtField(
        thoughtValue = thought,
        onAction = KeyboardActions {
            if (!valid) return@KeyboardActions
            onDone(thought.value.trim())
            keyboardController?.hide()
            ImeAction.Done
        }
    )
}

@Composable
fun ThoughtField(
    thoughtValue: MutableState<String>,
    onAction: KeyboardActions
) {
    OutlinedTextField(
        value = thoughtValue.value,
        onValueChange = {
            thoughtValue.value = it
        },
        shape = RoundedCornerShape(15.dp),
        label = {
            Text(
                text = "Add thoughts"
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = onAction,
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()
            .padding(25.dp)

    )
}

@Composable
fun StartFinishRow (
    onStartClick: ()-> Unit,
    onReadClick: ()-> Unit,
    book: BiblifyBooks
) {
    val context = LocalContext.current
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(bottom = 10.dp)
    ){
        TextButton(
            onClick = {
                onStartClick.invoke()
                showToast(context, "Click on Update to Start Reading!")
            },
            enabled = book.startedReading == null
        ) {
            if (book.startedReading == null){
                Text(
                    text = "Start Reading",
                    fontSize = 20.sp,
                    color = Color(255, 193, 7, 255)
                )
            }else {
                Text(
                    text = "Started On: ${formatDate(book.startedReadingAt)}",
                    fontSize = 20.sp,
                    color = Color.DarkGray
                )
            }
        }
        TextButton(
            onClick = {
                onReadClick.invoke()
                showToast(context, "Click on Update to Mark as Read!")
            },
            enabled = book.finishedReading == null
        ) {
            if (book.finishedReading == null){
                Text(
                    text = "Mark as Read",
                    fontSize = 20.sp,
                    color = Color(255, 193, 7, 255)
                )
            }else {
                Text(
                    text = "Finished On: ${formatDate(book.finishedReadingAt)}",
                    fontSize = 20.sp,
                    color = Color.DarkGray
                )
            }
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
            .padding(bottom = 10.dp)
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