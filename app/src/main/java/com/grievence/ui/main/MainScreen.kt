package com.grievence.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.grievence.R
import com.grievence.drawer.DrawerBody
import com.grievence.drawer.DrawerHeader
import com.grievence.drawer.TopBar
import com.grievence.routing.Screen
import com.grievence.ui.grievence_database.GrievencePreference
import com.grievence.ui.model.RestaurantModel
import com.grievence.ui.theme.GrievenceAppTheme
import com.grievence.ui.theme.green
import com.grievence.ui.theme.white
import com.grievence.utils.GradientButton
import com.grievence.utils.RoundedButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController) {
    val context = LocalContext.current
    val preference = remember {
        GrievencePreference(context)
    }
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var isLogout by remember { mutableStateOf(false) }
    val list = arrayListOf<RestaurantModel>().apply {
        add(
            RestaurantModel(
                name = "The Southfield ",
                image = R.drawable.ic_southfield,
                address = "18 Southfield Road\n0.1 miles from University of Teesside",
                detail = "Situated in the centre of Teesside University campus, we offer a great selection of craft beers and ciders which are regularly updated. The Southfield offers everything from Live sports down to simple board games and retro gaming. Make sure you check us out when the sun is shining as we have one of the biggest beer gardens Middlesbrough has to offer! \nWe have a packed programme of regular events to provide you with plenty of excuses to visit the Southfield. Check out our events for the most up to date information on all our upcoming activities."
            )
        )
        add(
            RestaurantModel(
                name = "The Massala",
                image = R.drawable.ic_masala,
                address = "#16 of 261 Restaurants in Middlesbrough",
                detail = "The Massala is an exquisite Indian Resataurant in Middlesbrough. Here at Massala Ltd we are constant.Lift your home dining standards with our menu’s new additions. Order for pickup and cherish the gourmet flavours."
            )
        )
        add(
            RestaurantModel(
                name = "Baker Street Kitchen",
                image = R.drawable.ic_backer,
                address = "#24 of 261 Restaurants in Middlesbrough\nVictoria House 159 Albert Road Victoria House",
                detail = "Informal eatery offering hearty comfort food dishes for all-day breakfast, brunch & lunch.\n" +
                        "Service options: Has outdoor seating · Doesn't accept reservations · Serves vegan dishes\n"
            )
        )
        add(
            RestaurantModel(
                name = "The Twisted Lip",
                image = R.drawable.ic_twisted,
                address = "11-13 Baker Street",
                detail = "Based on Baker Street in Middlesbrough's cultural quarter, The Twisted Lip is a gem of a place, tucked away from the mainstream on Baker Street, bang in the centre of the town."
            )
        )
        add(
            RestaurantModel(
                name = "The Tea House Middlesbrough",
                image = R.drawable.ic_tea,
                address = "86-88 Grange Road",
                detail = "Voted the nation's Independent Shop of the Year 2014 - The Olde Young Tea House blends and brews up"
            )
        )
        add(
            RestaurantModel(
                name = "Dovecot Bar & Kitchen",
                image = R.drawable.ic_devcot,
                address = "117-119 Linthorpe Road",
                detail = "A relaxed all day bar and terrace for early birds and night owls. In stylish, warm, and welcoming surroundings, have a drink and a bite to eat at Dovecot; two birds, one stone."
            )
        )

    }
    GrievenceAppTheme {
        androidx.compose.material.Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopBar(
                    navController = navController,
                    onNavigationIconClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                )
            },
            modifier = Modifier.background(color = green),
            drawerContent = {
                DrawerHeader()
                DrawerBody(onRate = {
                    navController.navigate(Screen.RatingScreen.route)
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                }, onGrevience = {
                    navController.navigate(Screen.GrievenceScreen.route)
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                }, onLogout = {
                    isLogout = true
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                })
            },
            backgroundColor = green,
            contentColor = green,
            drawerBackgroundColor = green
        ) { paddingValues ->
            Modifier.padding(
                bottom = paddingValues.calculateBottomPadding()
            )
            Column(
                modifier = Modifier
                    .background(color = green)
                    .verticalScroll(scrollState)
            ) {

                Column {
                    list.forEachIndexed { index, model ->
                        Card(
                            modifier = Modifier
                                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
                                .fillMaxWidth()
                                .height(350.dp),
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
                                model.name ?: "",
                                fontSize = 14.sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(vertical = 5.dp, horizontal = 10.dp)
                            )
                            Text(
                                model.address ?: "",
                                fontSize = 14.sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(vertical = 5.dp, horizontal = 10.dp)
                            )
                            Spacer(Modifier.height(10.dp))
                            Row(
                                modifier = Modifier
                            ) {
                                RoundedButton(
                                    text = "Click for detail",
                                    textColor = white,
                                    onClick = {
                                        navController.navigate(Screen.Detail.route + "/${model.name}" + "/${model.image}" + "/${model.address}"+ "/${model.detail}" )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        if (isLogout) {
            AlertDialog(
                onDismissRequest = {
                    isLogout = false
                },
                title = { Text(stringResource(id = R.string.app_name)) },
                text = { Text("Are you sure you want to logout ?") },
                confirmButton = {
                    RoundedButton(
                        text = "Cancel",
                        textColor = green,
                        onClick = { isLogout = false }
                    )
                },
                dismissButton = {

                    RoundedButton(
                        text = "Logout",
                        textColor = green,
                        onClick = {
                            preference.saveData("isLogin", false)
                            navController.navigate(
                                Screen.LoginScreen.route
                            ) {
                                popUpTo(Screen.MainScreen.route) {
                                    inclusive = true
                                }
                            }
                            isLogout = false
                        }
                    )

                }
            )
        }
    }

}