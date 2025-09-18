package com.example.nubankcryptostela

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost


data class Crypto(
    val id: Int,
    val name: String,
    val symbol: String,
    val price: Double,
    val variation: Double,
)

val cryptoList = mutableListOf(
    Crypto(1, "Bitcoin", "BTC", 123458.78, 2.5),
    Crypto(2, "Ethereum", "ETH", 9876.54, -1.2),
    Crypto(3, "Polygon", "MATIC", 5.12, 3.1)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NubankCrypto(){
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "cryptoList") {
                    composable("cryptoList"){ CryptoListScreen(navController) }
                    composable(
                        "cryptoDetail/{cryptoId}",
                        arguments = listOf(navArgument("cryptoId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val cryptoId = backStackEntry.arguments?.getInt("cryptoId") ?: 0
                        val crypto = cryptoList.first { it.id == cryptoId }
                        CryptoDetailScreen(navController, crypto)
            }
                    composable(
                        "sell/{cryptoId}",
                        arguments = listOf(navArgument("cryptoId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val cryptoId = backStackEntry.arguments?.getInt("cryptoId") ?: 0
                        val crypto = cryptoList.first { it.id == cryptoId }
                        TransactionScreen(navController, crypto, false)

                    }

                }
            }
        }
    }
}
@Composable
fun CryptoListScreen(navController: NavHost) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Criptos") }) },

    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cryptoList) { crypto ->
                CryptoCard(crypto) {
                    navController.navigate("cryptoDetail/${crypto.id}")
                }
            }
        }

    }
}

@Composable
fun CryptoCard(crypto: Crypto, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = crypto.name, fontWeight = FontWeight.Bold,fontSize = 18.sp)
                Text(text = crypto.symbol)
            }
            Column {
                Text(text = "R$ ${crypto.price}", fontWeight = FontWeight.Bold,fontSize = 18.sp)
                Text(
                    text = "${crypto.variation}%",
                    color = if (crypto.variation >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
            }

        }

    }
}
@Composable
fun CryptoDetailScreen(navController: NavHost, crypto: Crypto) {
    Scaffold(topBar = { TopAppBar(title = { Text(crypto.name) }) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            . padding (16.dp),
        verticalArrangement = Arrangement.spaceBy(20.dp)
        ) {
        Text(text = "Preço Atual: R$ ${crypto.price}", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text(
            text = "Variação: ${crypto.variation}%",
            fontSize = 18.sp,
            color = if (crypto.variation >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
        )
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ActionButton(text = "Comprar") {
                navController.navigate("buy/${crypto.id}")
            }
            ActionButton(text = "Vender") {
                navController.navigate("sell/${crypto.id}")
            }
         }
      }
    }
}
