package com.grievence.ui.register

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.grievence.R
import com.grievence.routing.Screen
import com.grievence.utils.GrievenceBorderDeild
import com.grievence.utils.RoundedButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.grievence.ui.grievence_database.GrievencePreference
import com.grievence.ui.theme.*
import com.grievence.utils.isValidEmail
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    val preference = remember {
        GrievencePreference(context)
    }
    var registerUser by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val firebaseAuth = FirebaseAuth.getInstance()
    GrievenceAppTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(green)
            ) {
                Spacer(
                    modifier = Modifier
                        .background(green)
                        .height(45.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(white)
                        .padding(15.dp)
                ) {
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        "Welcome",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(color = black, fontSize = 16.sp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        "Start creating account, Grievence.",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(color = black, fontSize = 16.sp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        "Name",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(color = black)
                    )
                    GrievenceBorderDeild(
                        value = name,
                        onValueChange = { text ->
                            name = text
                        },
                        placeholder = "Enter name",
                        keyboardType = KeyboardType.Text,
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "Email",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(color = black)
                    )
                    GrievenceBorderDeild(
                        value = email,
                        onValueChange = { text ->
                            email = text
                        },
                        placeholder = "Enter email",
                        keyboardType = KeyboardType.Email,
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "Password",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(color = black)
                    )
                    GrievenceBorderDeild(
                        value = password,
                        onValueChange = { text ->
                            password = text
                        },
                        placeholder = "Enter Password",
                        keyboardType = KeyboardType.Password,
                        visualTransformation = PasswordVisualTransformation(),
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier
                    ) {
                        RoundedButton(
                            text = "Register",
                            textColor = white,
                            onClick = {
                                if (name.isNotEmpty()) {
                                        if (email.isNotEmpty()) {
                                            if (!isValidEmail(email)) {
                                                if (password.isNotEmpty()) {
                                                    registerUser = true
                                                    firebaseAuth.createUserWithEmailAndPassword(
                                                        email.lowercase(),
                                                        password
                                                    )
                                                        .addOnCompleteListener { task ->
                                                            if (task.isSuccessful) {
                                                                preference.saveData(
                                                                    "isLogin", true
                                                                )
                                                                Toast.makeText(
                                                                    context,
                                                                    "Register successfully.",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                                navController.navigate(
                                                                    Screen.MainScreen.route
                                                                ) {
                                                                    popUpTo(Screen.RegisterScreen.route) {
                                                                        inclusive = true
                                                                    }
                                                                }
                                                                registerUser = false
                                                            } else {
                                                                Toast.makeText(
                                                                    context,
                                                                    task.exception?.message.toString(),
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                                registerUser = false
                                                            }
                                                        }
                                                } else {
                                                    Toast.makeText(
                                                        context,
                                                        "Please enter password.",
                                                        Toast.LENGTH_LONG
                                                    ).show()

                                                }
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Please enter email.",
                                                    Toast.LENGTH_LONG
                                                ).show()

                                            }
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Please enter valid email.",
                                                Toast.LENGTH_LONG
                                            ).show()

                                        }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Please enter name.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 20.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Already have an account?",
                                textAlign = TextAlign.End,
                                style = TextStyle(color = gray)
                            )

                            Text(
                                " Login", modifier = Modifier.clickable {
                                    navController.navigate(Screen.LoginScreen.route) {
                                        popUpTo(Screen.RegisterScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                }, textAlign = TextAlign.End,
                                style = TextStyle(color = green)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }


            }
            if (registerUser) {
                Dialog(
                    onDismissRequest = { },
                    DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(100.dp)
                            .background(white, shape = RoundedCornerShape(8.dp))
                    ) {
                        CircularProgressIndicator(color = green)
                    }
                }
            }


        }
    }
}