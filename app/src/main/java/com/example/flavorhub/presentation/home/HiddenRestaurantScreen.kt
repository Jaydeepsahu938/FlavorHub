package com.example.flavorhub.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.example.flavorhub.R
import com.example.flavorhub.presentation.navigation.Welcome
import com.example.flavorhub.presentation.registration.CustomEditText

@Composable
fun HiddenRestaurantScreen(modifier: Modifier, backStack: NavBackStack<NavKey>) {
    val searchText = remember { mutableStateOf("") }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(Modifier.fillMaxWidth())
        }
    ) { innerPadding ->
        Column(Modifier.fillMaxWidth().padding(innerPadding)) {
            LazyColumn {
                item {
                    Text(
                        text = "Hidden Restaurants",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color(0xff1A1C1C),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Text(
                        text = "Manage the restaurants you've thumbed down.\n" +
                                "Restoring them will allow them to appear in your\n" +
                                "search results again.",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        color = Color(0xff1A1C1C),
                        modifier = Modifier.padding(top = 4.dp, start = 16.dp)
                    )
                }
                item {
                    CustomEditText(
                        Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        "",
                        "Search hidden resta... ",
                        R.drawable.outline_search_24,
                        onTextChange = {}
                    )
                }
                items(10) {
                    HiddenItemCardView(
                        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp)
                    )
                }
            }
        }
    }
}
@Composable
fun HiddenItemCardView(modifier: Modifier) {
    Card(modifier = modifier,
        colors =  CardDefaults.cardColors(containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
                Image(
                    painter = painterResource(id = R.drawable.registation_screen_image),
                    contentDescription = " item image",
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .size(84.dp)
                        .alpha(0.4f),
                    contentScale = ContentScale.FillWidth,
                )
            Column(Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp),
            ) {
                Text(
                    text = "Artisan Kitchen",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = Color(0xff1A1C1C),
                )
                Row(verticalAlignment = Alignment.CenterVertically){
                    Box(
                        Modifier
                            .size(5.dp)
                            .background(color = Color(0xff5f5e5e), shape = CircleShape)
                    )
                    Text(
                        text = " American",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        color = Color(0xff5f5e5e),
                    )
                    Box(
                        Modifier
                            .padding(start = 4.dp)
                            .size(5.dp)
                            .background(color = Color(0xff5f5e5e), shape = CircleShape)
                    )
                    Text(
                        text = " 30-40 min",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color(0xff5f5e5e),
                    )
                }
            }
            Column(Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(
                    painter = painterResource(id = R.drawable.restore_icon),
                    contentDescription = " star icon",
                    modifier = Modifier
                        .size(22.dp)
                        .padding(end = 4.dp, bottom = 3.dp),
                    contentScale = ContentScale.FillWidth
                )
                Text(
                    text = "RESTORE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color(0xffA43700),
                )
            }
        }
    }
}
@Preview
@Composable
fun HiddenRestaurantScreenPreview() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        HiddenRestaurantScreen(modifier = Modifier.padding(innerPadding), backStack = rememberNavBackStack(Welcome))
    }
}