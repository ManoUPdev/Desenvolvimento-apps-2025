package com.example.nubankcryptostela

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue


data class Crypto(val name: String, val price: Double, val variation: Double)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            val navController = rememberNavController()
            navHost(navController, startDestination = "login") {
                composable("login") { LoginScreen(navController) }
                composable("list")  { CryptoListScreen(navController) }
                composable("detail/{crypto}") { backStackEntry ->
                    val cryptoName = backStackEntry.arguments?.getString("crypto") ?: ""
                    CryptoDetailScreen(navController, cryptoName)
                }
                composable("buySell/{crypto}") { backStackEntry ->
                    val cryptoName = backStackEntry.arguments?.getString("crypto") ?: ""
                    BuySellScreen(navController, cryptoName)
                }
            }
        }
    }
}

@Composable
fun loginScreen(navController: NavController){
    val context = LocalContext.current
    var email by remember { mutableStateOf(TextFieldValue(""))}
    var password by remember { mutableStateOf(TextFieldValue(""))}

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
    }
}