package jano.net.proyecto_final
import android.text.style.BackgroundColorSpan
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import jano.net.proyecto_final.ui.theme.Typography
import androidx.navigation.NavHostController

@Composable
fun HomePageScreen(navHostController: NavHostController,modifier: Modifier = Modifier.fillMaxWidth()) {

    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(35.dp, 40.dp)
    ){
        Row(modifier = Modifier.padding(start = 28.dp)) {
            Image(
                painter = painterResource(id = R.drawable.usuario),
                contentDescription = "ImagenUsuario",
                modifier = Modifier.size(60.dp)
            )
            Text(
                text = "Bienvenido",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 15.dp)
            )
        }
        Text(
            text = "Homepage",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = Typography.titleLarge
        )
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceEvenly){
            Button(
                onClick = {navHostController.navigate("tareas")},
                modifier = Modifier
                    .width(130.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Text(text = "Tareas", color = Color.Black)
            }

            Button(
                onClick = {navHostController.navigate("notas")},
                modifier = Modifier
                    .width(130.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Text(text = "Notas", color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.height(28.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .border(BorderStroke(2.dp, Color.Gray))
        ) {
            Column {
                Text(
                    text = "Proximos eventos este mes",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        FloatingActionButton(
            onClick = {  },
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.CenterHorizontally)
                .border(BorderStroke(2.dp, Color.Magenta), shape = CircleShape),
            containerColor = MaterialTheme.colorScheme.primary,
            shape = CircleShape
        ) {
            Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.Magenta)
        }
    }
}

