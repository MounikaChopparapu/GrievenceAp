package com.grievence.ui.detail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.grievence.R
import com.grievence.routing.Screen
import com.grievence.ui.theme.GrievenceAppTheme
import com.grievence.ui.theme.black
import com.grievence.ui.theme.green
import com.grievence.ui.theme.white
import com.grievence.utils.RoundedButton

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(navController: NavController) {
    val context = LocalContext.current
    var showDropdown by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val itemList = listOf("Pending", "Cancelled", "Completed")
    var status by remember { mutableStateOf("") }
    val icon = if (showDropdown)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    GrievenceAppTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .background(color = green)
                    .fillMaxSize()
                    .padding(top = 40.dp)
            ) {
                SmallTopAppBar(
                    title = {
                        Text(
                            text = "Detail", color = Color.White,
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
                            Icon(imageVector = Icons.Rounded.ArrowBack,
                                tint = Color.White,
                                contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = green,
                        titleContentColor = Color.White
                    )
                )

                Column(modifier = Modifier.fillMaxSize().background(color = white)) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_restaurant),
                        contentDescription = "Image",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        "Test Restaurant",
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 10.dp)
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        "Status",
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 10.dp)
                    )
                    Spacer(Modifier.height(5.dp))
                    OutlinedTextField(
                        value = if(status!="") status else "Select status",
                        onValueChange = { status = it },
                        modifier = Modifier
                            .fillMaxWidth().padding(10.dp).clickable { showDropdown = !showDropdown },
                        enabled = false,
                        trailingIcon = {
                            Icon(
                                icon, "contentDescription",

                            )
                        }
                    )
                    Box {
                        if (showDropdown) {
                            Popup(
                                alignment = Alignment.TopCenter,
                                properties = PopupProperties(
                                    excludeFromSystemGesture = true,
                                ),
                                onDismissRequest = { showDropdown = false }
                            ) {

                                Column(
                                    modifier = Modifier
                                        .heightIn(max = 90.dp)
                                        .verticalScroll(state = scrollState)
                                        .padding(10.dp)
                                        .border(width = 1.dp, color = Color.Gray),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {

                                    itemList.onEachIndexed { index, item ->
                                        if (index != 0) {
                                            Divider(thickness = 1.dp, color = Color.LightGray)
                                        }
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dp)
                                                .clickable {
                                                    status = item
                                                    showDropdown = !showDropdown
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
                    Column(
                        modifier = Modifier.fillMaxSize().padding(bottom = 20.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Box(Modifier.padding(15.dp)) {
                            RoundedButton(
                                text = "Submit",
                                textColor = white,
                                onClick = {
                                    Toast.makeText(context, "Status updated successfully.", Toast.LENGTH_SHORT).show()

                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

            }
        }


    }

}