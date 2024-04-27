package com.grievence.ui.rating

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.grievence.R
import com.grievence.ui.theme.*
import com.grievence.utils.RoundedButton

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalAnimationApi::class
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RatingScreen(navController: NavController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var isSubmit by remember {
        mutableStateOf(false)
    }
    var isCriteria by rememberSaveable {
        mutableStateOf(false)
    }
    val criteria = arrayListOf(
        "Food", "Service",
        "Atmosphere/ambience"
    )
    var selectedCriteria by remember { mutableStateOf("") }

    var isRestaurant by rememberSaveable {
        mutableStateOf(false)
    }
    val restaurant = arrayListOf(
        "The Southfield", "The Massala",
        "The Twisted Lip", "The Tea House Middlesbrough",
        "Baker Street Kitchen",
        "Dovecot Bar & Kitchen"
    )

    var rating by remember { mutableStateOf(0F) }
    var selectedRestaurant by remember { mutableStateOf("") }

    var review by remember { mutableStateOf("") }
    GrievenceAppTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = green)
                    .padding(top = 40.dp)
            ) {
                SmallTopAppBar(
                    title = {
                        Text(
                            text = "Rate our Restaurant", color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.navigateUp()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                tint = Color.White,
                                contentDescription = "Back"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = green,
                        titleContentColor = Color.White
                    )
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = offWhite)
                        .padding(20.dp)
                ) {

                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        value = if (selectedCriteria != "") selectedCriteria else "Select criteria",
                        onValueChange = { selectedCriteria = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable { isCriteria = !isCriteria },
                        enabled = false,
                        trailingIcon = {
                            Icon(
                                if (isCriteria)
                                    Icons.Filled.KeyboardArrowUp
                                else
                                    Icons.Filled.KeyboardArrowDown,
                                "contentDescription",

                                )
                        },
                        textStyle = TextStyle(color = black)
                    )
                    Box {
                        if (isCriteria) {
                            Popup(
                                alignment = Alignment.TopCenter,
                                properties = PopupProperties(
                                    excludeFromSystemGesture = true,
                                ),
                                onDismissRequest = { isCriteria = false }
                            ) {

                                Column(
                                    modifier = Modifier
                                        .heightIn(max = 150.dp)
                                        .verticalScroll(state = scrollState)
                                        .border(width = 1.dp, color = Color.Gray)
                                        .background(white),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {

                                    criteria.onEachIndexed { index, item ->
                                        if (index != 0) {
                                            Divider(thickness = 1.dp, color = Color.LightGray)
                                        }
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dp)
                                                .clickable {
                                                    selectedCriteria = item
                                                    isCriteria = !isCriteria
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(text = item, style = TextStyle(color = black))
                                        }
                                    }

                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        value = if (selectedRestaurant != "") selectedRestaurant else "Select restaurant",
                        onValueChange = { selectedRestaurant = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable { isRestaurant = !isRestaurant },
                        enabled = false,
                        trailingIcon = {
                            Icon(
                                if (isRestaurant)
                                    Icons.Filled.KeyboardArrowUp
                                else
                                    Icons.Filled.KeyboardArrowDown,
                                "contentDescription",

                                )
                        },
                        textStyle = TextStyle(color = black)
                    )
                    Box {
                        if (isRestaurant) {
                            Popup(
                                alignment = Alignment.TopCenter,
                                properties = PopupProperties(
                                    excludeFromSystemGesture = true,
                                ),
                                onDismissRequest = { isRestaurant = false }
                            ) {

                                Column(
                                    modifier = Modifier
                                        .verticalScroll(state = scrollState)
                                        .border(width = 1.dp, color = Color.Gray)
                                        .background(white),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {

                                    restaurant.onEachIndexed { index, item ->
                                        if (index != 0) {
                                            Divider(thickness = 1.dp, color = Color.LightGray)
                                        }
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dp)
                                                .clickable {
                                                    selectedRestaurant = item
                                                    isRestaurant = !isRestaurant
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(text = item, style = TextStyle(color = black))
                                        }
                                    }

                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    Text(
                        "Rate to Restaurant option",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(color = black)
                    )
                    Text(
                        "One star=Eek! Methinks not!\n" +
                                "Two stars=Meh I’ve experienced better\n" +
                                "Three stars=A-OK\n" +
                                "Four stars=Yea! I’m a fan\n" +
                                "Five stars=Woohoo! As good as it gets!",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(color = black)
                    )
                    Spacer(Modifier.height(10.dp))
                    StarRatingBar(
                        maxStars = 5,
                        rating = rating,
                        onRatingChanged = {
                            rating = it
                        }
                    )
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(start = 15.dp, top = 10.dp, end = 15.dp)
                            .background(Color.White, RoundedCornerShape(5.dp)),
                        shape = RoundedCornerShape(5.dp),
                        value = review,
                        placeholder = {
                            Text("Enter review", color = gray, fontSize = 16.sp)
                        },
                        onValueChange = { review = it },
                        keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
                        maxLines = 3
                    )
                    Spacer(Modifier.height(30.dp))
                    Row(
                        modifier = Modifier
                    ) {
                        RoundedButton(
                            text = "Submit rating",
                            textColor = white,
                            onClick = {
                                if (selectedCriteria.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Please select criteria.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (selectedRestaurant.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Please select restaurant.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (rating <= 0F) {
                                    Toast.makeText(
                                        context,
                                        "Please select rating.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (review.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Please enter review.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    isSubmit = true
                                }

                            }
                        )
                    }


                    Spacer(modifier = Modifier.height(10.dp))

                }
            }
        }
        if (isSubmit) {
            AlertDialog(
                onDismissRequest = {
                    isSubmit = false
                },
                title = { Text(stringResource(id = R.string.app_name)) },
                text = { Text("your rating has been successfully submitted!!!") },
                confirmButton = {
                    RoundedButton(
                        text = "Ok",
                        textColor = white,
                        onClick = {
                            navController.navigateUp()
                            isSubmit = false
                        }
                    )
                },
                dismissButton = {}
            )
        }


    }
}

@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Float,
    onRatingChanged: (Float) -> Unit
) {
    val density = LocalDensity.current.density
    val starSize = (12f * density).dp
    val starSpacing = (0.5f * density).dp

    Row(
        modifier = Modifier.selectableGroup(), horizontalArrangement = Arrangement.Start
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Filled.Star else Icons.Default.Star
            val iconTintColor = if (isSelected) Color(0xFFFFC700) else black
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            onRatingChanged(i.toFloat())
                        }
                    )
                    .width(starSize)
                    .height(starSize)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}