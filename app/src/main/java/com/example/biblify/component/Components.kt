package com.example.biblify.component

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.biblify.R
import com.example.biblify.model.BiblifyBooks
import com.example.biblify.navigation.BiblifyScreens
import com.example.biblify.screens.home.HomeScreenViewModel
import com.example.biblify.screens.search.SearchViewModel
import com.example.biblify.ui.theme.BiblifyTheme
import com.example.biblify.utils.LoadImageWithGlide
import com.example.biblify.utils.customFonts

@Composable
fun EmailInputField(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    onAction: KeyboardActions = KeyboardActions.Default,
) {

    OutlinedTextField(
        value = emailState.value,
        onValueChange = {
            emailState.value = it
        },
        label = {
            Text(
                text = "Email"
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        keyboardActions = onAction,
        maxLines = 1,
        shape = RoundedCornerShape(35.dp),
        modifier = modifier
    )
}

@Composable
fun PasswordInputField(
    modifier: Modifier,
    passwordState: MutableState<String>,
    isVisible: MutableState<Boolean>,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {
            passwordState.value = it
        },
        label = {
            Text(
                text = "Password"
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null
            )
        },
        trailingIcon = {
            when (isVisible.value) {
                true -> Image(
                    painter = painterResource(R.drawable.eyeopen),
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            isVisible.value = !isVisible.value
                        }
                )
                else -> Image(
                    painter = painterResource(id = R.drawable.eyeclose),
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            isVisible.value = !isVisible.value
                        }
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        visualTransformation = when (isVisible.value) {
            true -> VisualTransformation.None
            else -> PasswordVisualTransformation()
        },
        keyboardActions = onAction,
        maxLines = 1,
        shape = RoundedCornerShape(35.dp),
        modifier = modifier
    )
}
@Composable
fun LoginRegisterButton(
    buttonText: String,
    validInputs: Boolean,
    modifier: Modifier,
    onButtonClicked: () -> Unit,
) {
    Button(
        onClick = onButtonClicked,
        shape = RoundedCornerShape(35.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 15.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(226,180,117,255),
            disabledContainerColor = Color(241,223,200,255)

        ),
        enabled = validInputs,
        modifier = modifier
    ) {
        Text(
            text = buttonText,
            color = Color.Black,
            fontSize = 20.sp
        )
    }
}

@Composable
fun ListCard(
    book: BiblifyBooks,
    onPressDetails: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels/displayMetrics.density
    val spacing = 10.dp

    Card (
        shape = RoundedCornerShape(29.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(16.dp)
            .height(242.dp)
            .width(202.dp)
            .clickable {
                onPressDetails.invoke(book.googleBookID.toString())
            }
    ) {
        Column (
            modifier = Modifier
                .width(screenWidth.dp - (2*spacing)),
            horizontalAlignment = Alignment.Start
        ) {
            Row (
                horizontalArrangement = Arrangement.Center
            ) {
                Card (
                    modifier = Modifier
                        .height(140.dp)
                        .width(100.dp),
                    shape = RoundedCornerShape(topStart = 29.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        LoadImageWithGlide (
                            imageUrl = book.photoURL.toString(),
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
                Spacer(modifier = Modifier.width(50.dp))
                Column (
                    modifier = Modifier
                        .padding(start = 10.dp, end = 5.dp, top = 35.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = "Add to favorites",
                        modifier = Modifier
                            .padding(start = 4.dp)
                    )
                    BookRating(
                        rating = book.rating!!
                    )
                }
            }
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = book.title.toString(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    modifier = Modifier.padding(start = 6.dp, top = 4.dp)
                )
                Text(
                    text = book.author.toString(),
                    fontStyle = FontStyle.Italic,
                    fontSize = 10.sp,
                    maxLines = 1,
                    modifier = Modifier.padding(start = 6.dp)
                )
                Card (
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding()
                        .width(80.dp)
                        .height(55.dp),
                    shape = RoundedCornerShape(topStart = 29.dp, bottomEnd = 29.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 20.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(188,140,100),
                        contentColor = Color(236,255,255)
                    )
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                    ){
                        Text(
                            text = "Reading",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BiblifyFAB (
    onFABClicked: () -> Unit
) {
    FloatingActionButton(
        onClick = onFABClicked,
        shape = CircleShape,
        containerColor = Color(229,164,76),
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 20.dp,
            hoveredElevation = 40.dp
        )
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add a book",
            tint = Color(242,244,224)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiblifyTopBar(
    modifier: Modifier = Modifier,
    title: String,
    navController: NavController,
    navIconPressed: () -> Unit = {},
    navIcon: ImageVector? = null,
    showProfile: Boolean,
    actionIcon: Painter? = null,
    actionOnClick: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Row (
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
            ) {
                if(showProfile) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Image(
                            painter = painterResource(R.drawable.appicon),
                            contentDescription = "App Icon",
                            contentScale = ContentScale.Crop,
                            modifier = modifier
                                //.scale(.8f)
                                //.aspectRatio(1f)
                                //.clip(CircleShape)

                        )
                    }
                }
            }
        },
        navigationIcon = {
            IconButton (
                onClick = navIconPressed
            ) {
                if (navIcon != null) {
                    Icon(
                        imageVector = navIcon,
                        contentDescription = ""
                    )
                }
            }
        },
        actions = {
            IconButton(
                onClick = actionOnClick
            ) {
                if (actionIcon != null) {
                    Image(
                        painter = actionIcon,
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .clip(CircleShape)
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun TitleSection(
    isTopSection: Boolean = false,
    modifier: Modifier = Modifier,
    label: String,
    onPfpClicked: () -> Unit = {}
) {
    Surface (
        modifier = modifier
            .padding(start = 5.dp, top = 1.dp)
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 25.sp,
                fontFamily = customFonts(GoogleFont("DM Serif Text")),
                textAlign = TextAlign.Left
            )
            if (isTopSection) {
                Image(
                    painter = painterResource(R.drawable.userpfp),
                    contentDescription = "User Account",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 10.dp)
                        .clickable {
                            onPfpClicked.invoke()
                        }
                )
            }
        }
    }
}

@Composable
fun BookListArea (
    listOfBooks: List<BiblifyBooks>,
    navController: NavController
) {
    val addedBooks = listOfBooks.filter { book->
        book.startedReading == null && book.finishedReading == null
    }
    HorizontalScrollableComponent(addedBooks){
        navController.navigate(BiblifyScreens.UpdateScreen.name+"/$it")
    }
}

@Composable
fun HorizontalScrollableComponent(
    listOfBooks: List<BiblifyBooks>,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onCardPressed: (String) -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Row (
        modifier = Modifier
            .horizontalScroll(scrollState)
            .fillMaxWidth()
            .heightIn(280.dp)
    ) {
        if (viewModel.data.value.loading == true){
            LinearProgressIndicator()
        } else {
            if (listOfBooks.isEmpty()){
                Text(text = "NO BOOKS FOUND!")
            }else {
                for (book in listOfBooks) {
                    ListCard(book) {
                        onCardPressed(book.googleBookID.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun BookRating(rating: Int = 4) {
    Card (
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .padding(end = 6.dp, top = 5.dp)
            .height(50.dp)
            .width(50.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(3.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Rating",
                modifier = Modifier
            )
            Text (
                text = rating.toString(),
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun SearchBar(
    viewModel: SearchViewModel = viewModel(),
    loading: Boolean? = null,
    onSearch: (String) -> Unit,
) {
    val searchQueryState = rememberSaveable {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQueryState.value) {
        searchQueryState.value.trim().isNotEmpty()
    }
    Column {
        SearchTextField(
            valueState = searchQueryState,
            placeholder = "Rich Dad, Poor Dad",
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            },

            )
    }
}
@Composable
fun SearchTextField(
    valueState: MutableState<String>,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        placeholder = {
            Text(text = placeholder)
        },
        label = {
            Text(text = "Any book on your mind?")
        },
        leadingIcon = {
            Image (
                painter = painterResource(id = R.drawable.trailingsearchicon),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                alignment = Alignment.CenterStart,
                modifier = Modifier
                    .scale(.5f)
            )
        },
        trailingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        keyboardActions = onAction,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color(234,182,118),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
            .fillMaxWidth()
            .height(65.dp)
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int,
    onPressRating: (Int) -> Unit
) {
    var ratingState by remember {
        mutableIntStateOf(rating)
    }

    var selected by remember {
        mutableStateOf(false)
    }
    val size by animateDpAsState(
        targetValue = if (selected) 42.dp else 34.dp,
        spring(Spring.DampingRatioMediumBouncy), label = ""
    )

    Row(
        modifier = Modifier.width(280.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            Icon(
                painter = painterResource(id = R.drawable.star),
                contentDescription = "star",
                modifier = modifier
                    .width(size)
                    .height(size)
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                selected = true
                                onPressRating(i)
                                ratingState = i
                            }
                            MotionEvent.ACTION_UP -> {
                                selected = false
                            }
                        }
                        true
                    },
                tint = if (i <= ratingState) Color(0xFFFFD700) else Color(0xFFA2ADB1)
            )
        }
    }
}
@Preview
@Composable
fun RoundedButton(
    label: String = "Reading",
    radius: Int = 29,
    onPress: () -> Unit = {}) {
    Surface(modifier = Modifier.clip(RoundedCornerShape(
        bottomEndPercent = radius,
        topStartPercent = radius)),
        color = Color(0xFF92CBDF)) {

        Column(modifier = Modifier
            .width(90.dp)
            .heightIn(40.dp)
            .clickable { onPress.invoke() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = label, style = TextStyle(color = Color.White,
                fontSize = 15.sp),)
        }
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {

    OutlinedTextField(value = valueState.value,
        onValueChange = { valueState.value = it},
        label = { Text(text = labelId)},
        singleLine = isSingleLine,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = Color.Transparent
        ),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction)


}

fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG)
        .show()
}


@Preview(showBackground = true)
@Composable
fun ComponentPreview() {
    val navController = rememberNavController()
    BiblifyTheme {
        SearchBar (onSearch = {})
    }
}



