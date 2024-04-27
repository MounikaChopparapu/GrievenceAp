package com.grievence.ui.grievence

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.grievence.R
import com.grievence.ui.theme.*
import com.grievence.utils.GrievenceBorderDeild
import com.grievence.utils.RoundedButton
import java.util.*

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalAnimationApi::class
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GrievenceScreen(navController: NavController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var isSubmit by remember {
        mutableStateOf(false)
    }
    var isType by rememberSaveable {
        mutableStateOf(false)
    }
    val type = arrayListOf(
        "Low-quality food or drink", "Order mix-up",
        "Slow service", "Poor customer service",
        "Uncleanliness", "Overpriced menu items",
        "General atmosphere", "Unavailable products or services",
        "Delivery arrived late or cold"
    )
    var selectedType by remember { mutableStateOf("") }

    var isRestaurant by rememberSaveable {
        mutableStateOf(false)
    }
    val restaurant = arrayListOf(
        "The Southfield", "The Massala",
        "The Twisted Lip", "The Tea House Middlesbrough",
        "Baker Street Kitchen",
        "Dovecot Bar & Kitchen"
    )
    var selectedRestaurant by remember { mutableStateOf("") }

    var selectedDate by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }

    var complaint by remember { mutableStateOf("") }

    val radioOptions = listOf("YES", "NO")
    var selectedOption by remember { mutableStateOf(radioOptions[0]) }
    GrievenceAppTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .background(color = green)
                    .padding(top = 40.dp)
                    .verticalScroll(scrollState)
            ) {
                SmallTopAppBar(
                    title = {
                        Text(
                            text = "Submit Grievance", color = Color.White,
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
                    modifier = Modifier.background(color = white)
                ) {

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
                    OutlinedTextField(
                        value = if (selectedType != "") selectedType else "Select type of Grievance",
                        onValueChange = { selectedType = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable { isType = !isType },
                        enabled = false,
                        trailingIcon = {
                            Icon(
                                if (isType)
                                    Icons.Filled.KeyboardArrowUp
                                else
                                    Icons.Filled.KeyboardArrowDown,
                                "contentDescription",

                                )
                        },
                        textStyle = TextStyle(color = black)
                    )
                    Box {
                        if (isType) {
                            Popup(
                                alignment = Alignment.TopCenter,
                                properties = PopupProperties(
                                    excludeFromSystemGesture = true,
                                ),
                                onDismissRequest = { isType = false }
                            ) {

                                Column(
                                    modifier = Modifier
                                        .verticalScroll(state = scrollState)
                                        .border(width = 1.dp, color = Color.Gray)
                                        .background(white),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {

                                    type.onEachIndexed { index, item ->
                                        if (index != 0) {
                                            Divider(thickness = 1.dp, color = Color.LightGray)
                                        }
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dp)
                                                .clickable {
                                                    selectedType = item
                                                    isType = !isType
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                            .background(Color.White, RoundedCornerShape(5.dp)),
                        shape = RoundedCornerShape(5.dp),
                        placeholder = {
                            Text("Enter complaint", color = gray, fontSize = 16.sp)
                        },
                        value = complaint,
                        onValueChange = { complaint = it },
                        keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
                        maxLines = 3,
                        textStyle = TextStyle(color = black)
                    )
                    Spacer(Modifier.height(10.dp))
                    GrievenceBorderDeild(
                        value = if (selectedDate != "") selectedDate else "Select Date",
                        onValueChange = { text ->
                            selectedDate = text
                        },
                        onClick = {
                            showDatePicker = true
                        },
                        isEnabled = false,
                        placeholder = "Select Date"
                    )
                    if (showDatePicker) {
                        context.DatePickerDialogBox(onDateSelect = {
                            selectedDate = it
                            showDatePicker = false
                        })
                    }
                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = " Would you like to visit our restaurant again if we improve",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    radioOptions.forEach { model ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = (model == selectedOption),
                                onClick = { selectedOption = model }
                            )
                            Text(
                                text = model,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                    Spacer(Modifier.height(30.dp))
                    Row(
                        modifier = Modifier
                    ) {
                        RoundedButton(
                            text = "Submit Grievance",
                            textColor = white,
                            onClick = {
                                if (selectedRestaurant.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Please select restaurant.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (selectedType.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Please select type of grievence.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (complaint.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Please enter complaint.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (selectedDate.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Please select date.",
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
                text = { Text("your grievence detail has been successfully submitted!!!") },
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
fun Context.DatePickerDialogBox(
    onDateSelect: (String) -> Unit
) {
    val year: Int
    val month: Int
    val day: Int
    val mCalendar = Calendar.getInstance()
    year = mCalendar.get(Calendar.YEAR)
    month = mCalendar.get(Calendar.MONTH)
    day = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()
    val mDatePickerDialog = DatePickerDialog(
        this,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            onDateSelect("$mDayOfMonth/${mMonth + 1}/$mYear")
        }, year, month, day
    )
    mDatePickerDialog.show()
}