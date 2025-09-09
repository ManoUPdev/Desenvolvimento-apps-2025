package com.seunome.empresa.projetoosorio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seunome.empresa.projetoosorio.ui.theme.ProjetoOsorioTheme

class NovaLista : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

        }
    }
}

@Preview
@Composable
fun TelaDeTarefas(){

    var tarefas by remember {
        mutableStateOf(listOf("1", "2", "3"))
    }

    Scaffold {
        innerPadding ->
        
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 5.dp)
        ) {

            Cabecalhoo()
            Spacer(modifier = Modifier.height(5.dp))

            Formularioo(
                aoAdicionar = {
                    novaNota ->
                    tarefas = tarefas + novaNota
                }
            )

            Spacer(modifier = Modifier.height(5.dp))

            LazyColumn {
                items(tarefas){
                    tar ->
                    Tarefa(tar)
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}


@Preview
@Composable
fun Cabecalhoo(){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ){

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = "Icone perfil",
                modifier = Modifier.size(60.dp)
            )

            Column { 
                
                Text(
                    text = "José Silveira",
                    style = MaterialTheme.typography.headlineLarge
                )

                Text(
                    text = "Melhor Programador",
                    style = MaterialTheme.typography.bodySmall
                )

            }
        }

    }

}

// @ Preview
@Composable
fun Formularioo(aoAdicionar: (String) -> Unit){

    var textoInput by remember {
        mutableStateOf("")
    }

    Row {

        TextField(
            value = textoInput,
            onValueChange = { textoInput = it }
        )

        Spacer(modifier = Modifier.width(5.dp))

        Button(
            onClick = {
                // pegar a var textoInput
                // add ela no array
                aoAdicionar(textoInput)
                textoInput = ""
            }
        ) {

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Icone Add"
            )

            Text("Add")

        }

    }

}

@Preview
@Composable
fun Tarefa(texto: String = "Uma Tarefa..."){

    // onClick -> apagar
    // onHold -> mudar ---- classe Tarefa(string, icon)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = {

        },
    ) {

        Row (
            modifier = Modifier.fillMaxHeight().padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){

            Icon(
                imageVector = Icons.Outlined.Favorite,
                contentDescription = "Icon Fav"
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = texto,
                style = MaterialTheme.typography.bodyLarge
            )
        }

    }

}