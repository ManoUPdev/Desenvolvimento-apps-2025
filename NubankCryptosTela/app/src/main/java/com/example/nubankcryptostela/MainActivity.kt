package com.example.nubankcryptostela

import android.os.Bundle
import android.widget.Button
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.compose.rememberNavController


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
        ) {
            TextField(
                value = email,
                onValueChange = { email = it},
                label = { Text("Email ou CPF")}
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label =  { Text("Senha") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if(email.text.isNotEmpty() && password.text.isNotEmpty()) {
                    navController.navigate("list")
                } else {
                    Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Entrar")
            }
        }
    }
}

@Composable
fun CryptoListScreen(navController: NavHostController) {
    val cryptos = listOf(
        Crypto("Bitcoin", 120000.0, +1.5),
        Crypto("Ethereum", 9000.0, -0.5),
        Crypto("Solana", 600.0, +3.2)
    )
    Scaffold(topBar = {
        TopAppBar(title = { Text("Criptomoedas") })
    }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(cryptos) {crypto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    onClick = {
                        navController.navigate("detail/${crypto.name}")
                    }
                ) }
                    Row(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(crypto.name, style = MaterialTheme.typography.titleMedium)
                            Text("R$ ${crypto.price}")
                        }
                        Text("${crypto.variation}%")

                    }
        }
    }
}

@Composable
fun CryptoDetailScreen(navController: NavController, cryptoName: String) {
    Scaffold(topBar ={
        TopAppBar(title = { Text("Detalhes: $cryptoName")})
    }) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment() = Alignment.Center
                ) {
                    Text("[Grafico do $cryptoName aqui]", style = MaterialTheme.typography.bodyLarge)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(onClick = { navController.navigate ("buySell/$cryptoName") }) {
                        Text("Comprar")
                    }
                    Space(modifier = Modifier.width(16.dp))
                    Button(onClick = { navController.navigate("buySell/$cryptoName") }) {
                        Text("Vender")
                    }
                }

            }
        }
    }
}

@Composable
fun BuySellScreen(navController: NavController, cryptoName: String) {
    val context = LocalContext.current
    var amount by remember { mutableStateOf(TextFieldValue("")) }


    Scaffold(topBar = {
        TopAppBar(title = { Text("$cryptoName - Comprar/Vender") })
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Digite o valor em R$") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (amount.text.isNotEmpty()) {
                    Toast.makeText(context, "Operação realizada com sucesso!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                } else {
                    Toast.makeText(context, "Digite um valor", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Confirmar")
            }
        }
    }
}