package com.aristidevs.cursofirebaselite.presentation.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.aristidevs.cursofirebaselite.R
import com.aristidevs.cursofirebaselite.presentation.model.Artist
import com.aristidevs.cursofirebaselite.presentation.model.Player
import com.aristidevs.cursofirebaselite.ui.theme.Black
import com.aristidevs.cursofirebaselite.ui.theme.Purple40

@Composable
fun HomeScreen(viewmodel: HomeViewmodel = HomeViewmodel()) {

    val artists: State<List<Artist>> = viewmodel.artist.collectAsState()
    val player by viewmodel.player.collectAsState()
    val blockVersion by viewmodel.blockVersion.collectAsState()

    if (blockVersion) {
        val context = LocalContext.current
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                        .height(300.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ACTUALIZA",
                        fontSize = 22.sp,
                        color = Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Para poder disfrutar de todo nuestro contenido actualice la app",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = { navigateToPlayStore(context) }) {
                        Text(text = "¡Actualizar!")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        Text(
            "Popular artist",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(16.dp)
        )

        LazyRow {
            items(artists.value) {
                ArtistItem(artist = it, onItemSelected = { viewmodel.addPlayer(it) })
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        player?.let {
            PlayerComponent(player = it,
                onCancelSelected = { viewmodel.onCancelSelected() },
                onPlaySelected = { viewmodel.onPlaySelected() })
        }

    }
}

@Composable
fun PlayerComponent(player: Player, onPlaySelected: () -> Unit, onCancelSelected: () -> Unit) {

    val icon = if (player.play == true) R.drawable.ic_pause else R.drawable.ic_play

    Row(
        Modifier
            .height(50.dp)
            .fillMaxWidth()
            .background(Purple40),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = player.artist?.name.orEmpty(),
            modifier = Modifier.padding(horizontal = 12.dp),
            color = Color.White
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(painter = painterResource(id = icon),
            contentDescription = "play/pause",
            modifier = Modifier
                .size(40.dp)
                .clickable { onPlaySelected() })
        Image(painter = painterResource(id = R.drawable.ic_close),
            contentDescription = "Close",
            modifier = Modifier
                .size(40.dp)
                .clickable { onCancelSelected() })
    }
}


@Composable
fun ArtistItem(artist: Artist, onItemSelected: (Artist) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onItemSelected(artist) }) {
        AsyncImage(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            model = artist.image,
            contentDescription = "Artists image",
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = artist.name.orEmpty(), color = Color.White)
    }
}

@Preview
@Composable
fun ArtistItemPreview() {
    val artist = Artist(
        "Pepe",
        "El mejor",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTwyXeKDN29AmZgZPLS7n0Bepe8QmVappBwZCeA3XWEbWNdiDFB",
//        emptyList()
    )
    ArtistItem(artist = artist) {}
}


//fun createArtist(db: FirebaseFirestore) {
//    val random = (1..10000).random()
//    val artist = Artist(name = "Random $random", numberOfSongs = random)
//    db.collection("artists")
//        .add(artist)
//        .addOnSuccessListener {
//            Log.i("Aris", "SUCCESS")
//        }
//        .addOnFailureListener {
//            Log.i("Aris", "FAILURE")
//        }
//        .addOnCompleteListener {
//            Log.i("Aris", "COMPLETE")
//        }
//
//}

fun navigateToPlayStore(context: Context) {
    val appPackage = context.packageName
    try {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$appPackage")
            )
        )
    } catch (e: Exception) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$appPackage")
            )
        )

    }
}









