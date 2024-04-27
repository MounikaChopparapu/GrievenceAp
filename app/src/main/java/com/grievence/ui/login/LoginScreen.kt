package com.grievence.ui.login

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.grievence.routing.Screen
import com.grievence.ui.grievence_database.GrievencePreference
import com.grievence.ui.theme.*
import com.grievence.utils.GrievenceBorderDeild
import com.grievence.utils.RoundedButton
import com.grievence.utils.isValidEmail

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val preference = remember {
        GrievencePreference(context)
    }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginUser by remember { mutableStateOf(false) }
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
                        "Hey!",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(color = black, fontSize = 16.sp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        "Welcome , Please Login\nto continue.",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(color = black, fontSize = 16.sp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        "Email",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(color = black)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    GrievenceBorderDeild(
                        value = email,
                        onValueChange = { text ->
                            email = text
                        },
                        placeholder = "Enter email",
                        keyboardType = KeyboardType.Email,
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        "Password",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(color = black)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    GrievenceBorderDeild(
                        value = password,
                        onValueChange = { text ->
                            password = text
                        },
                        placeholder = "Enter Password",
                        keyboardType = KeyboardType.Password,
                        visualTransformation = PasswordVisualTransformation(),
                    )

                    Spacer(modifier = Modifier.height(30.dp))
                    Row(
                        modifier = Modifier
                    ) {
                        RoundedButton(
                            text = "Login",
                            textColor = white,
                            onClick = {
                                if (email.isNotEmpty()) {
                                    if (!isValidEmail(email.toString())) {
                                        if (password.isNotEmpty()) {
                                            loginUser = true
                                            firebaseAuth.signInWithEmailAndPassword(email.lowercase(), password)
                                                .addOnCompleteListener { task ->
                                                    if (task.isSuccessful) {
                                                        preference.saveData(
                                                            "isLogin", true
                                                        )
                                                        Toast.makeText(
                                                            context, "Login successfully.", Toast.LENGTH_SHORT
                                                        ).show()
                                                        navController.navigate(
                                                            Screen.MainScreen.route
                                                        ) {
                                                            popUpTo(Screen.LoginScreen.route) {
                                                                inclusive = true
                                                            }
                                                        }
                                                        loginUser = false
                                                    } else {
                                                        Toast.makeText(
                                                            context, task.exception?.message.toString(), Toast.LENGTH_SHORT
                                                        ).show()
                                                        loginUser = false
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
                                            "Please enter valid email.",
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
                                "Don't have an account?",
                                textAlign = TextAlign.End,
                                style = TextStyle(color = gray)
                            )

                            Text(
                                " Register", modifier = Modifier.clickable {
                                    navController.navigate(Screen.RegisterScreen.route)
                                }, textAlign = TextAlign.End,
                                style = TextStyle(color = green)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }


            }
            if (loginUser) {
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