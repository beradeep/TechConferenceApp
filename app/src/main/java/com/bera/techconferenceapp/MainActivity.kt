package com.bera.techconferenceapp

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.bera.techconferenceapp.presentation.navigation.Navigation
import com.bera.techconferenceapp.ui.theme.TechConferenceAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TechConferenceAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    val connectivityManager = remember(context) {
                        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    }

                    val isConnected = remember(connectivityManager) {
                        val networkInfo = connectivityManager.activeNetworkInfo
                        if (networkInfo != null && networkInfo.isConnectedOrConnecting) {
                            // For API 29 and above, use NetworkCapabilities to check internet access.
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                val capabilities =
                                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                                capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
                            } else {
                                true
                            }
                        } else {
                            false
                        }
                    }
                    when (isConnected) {
                        true -> {
                            Navigation()
                        }

                        false -> {
                            AlertDialog(
                                onDismissRequest = { },
                                confirmButton = { },
                                title = {
                                    Text(
                                        text = "Oops, internet unavailable!",
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                },
                                text = { Text(text = "Please ensure proper internet connection and restart.") },
                                dismissButton = { TextButton(onClick = { (context as Activity).finish() }) {
                                    Text(text = "OK")
                                } }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TechConferenceAppTheme {
        Greeting("Android")
    }
}