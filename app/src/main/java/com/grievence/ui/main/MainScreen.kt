package com.grievence.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.grievence.R
import com.grievence.routing.Screen
import com.grievence.ui.model.RestaurantModel
import com.grievence.ui.theme.GrievenceAppTheme
import com.grievence.ui.theme.white
import com.grievence.ui.theme.green
import com.grievence.utils.RoundedButton

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var search by remember { mutableStateOf("") }
    val list = arrayListOf<RestaurantModel>().apply {
        add(RestaurantModel(name = "Test Restaurant", image = ""))
        add(RestaurantModel(name = "Test Restaurant", image = ""))
        add(RestaurantModel(name = "Test Restaurant", image = ""))
        add(RestaurantModel(name = "Test Restaurant", image = ""))
        add(RestaurantModel(name = "Test Restaurant", image = ""))
        add(RestaurantModel(name = "Test Restaurant", image = ""))
        add(RestaurantModel(name = "Test Restaurant", image = ""))
        add(RestaurantModel(name = "Test Restaurant", image = ""))
        add(RestaurantModel(name = "Test Restaurant", image = ""))
        add(RestaurantModel(name = "Test Restaurant", image = ""))
        add(RestaurantModel(name = "Test Restaurant", image = ""))
        add(RestaurantModel(name = "Test Restaurant", image = ""))
        add(RestaurantModel(name = "Test Restaurant", image = ""))
        add(RestaurantModel(name = "Test Restaurant", image = ""))
        add(RestaurantModel(name = "Test Restaurant", image = ""))

    }
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
                            text = "Home", color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = green,
                        titleContentColor = Color.White
                    )
                )
                Spacer(Modifier.height(10.dp))
                Column {
                    list.forEachIndexed { index, saloonModel ->
                        Card(
                            modifier = Modifier
                                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
                                .fillMaxWidth()
                                .height(200.dp)
                                .clickable {
                                    navController.navigate(Screen.Detail.route)
                                },
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_restaurant),
                                contentDescription = "Image",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(160.dp)
                            )
                            Text(
                                saloonModel.name ?: "",
                                fontSize = 14.sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(vertical = 5.dp, horizontal = 10.dp)
                            )
                        }
                    }
                }
            }
        }


    }

}