package com.example.flavorhub.presentation.restaurant_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.flavorhub.R
import com.example.flavorhub.core.AppDatabase
import com.example.flavorhub.core.RetrofitClient
import com.example.flavorhub.data.RestaurantRepoImpl
import com.example.flavorhub.domain.entity.RestaurantEntity
import com.example.flavorhub.presentation.home.TopBar
import com.example.flavorhub.presentation.home.getImage
import com.example.flavorhub.presentation.welcome.CustomButton

@Composable
fun RestaurantDetailsScreen(modifier: Modifier, id: String) {
    val context = LocalContext.current
    val repo = RestaurantRepoImpl(AppDatabase.getInstance(context).restaurantDao(), RetrofitClient.apiService)
    val viewModel: RestaurantDetailsViewModel = viewModel(
        factory = RestaurantDetailsViewModelFactory(repo)
    )
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(RestaurantDetailsIntent.fetchRestaurant(id))
    }
    when{
        state.isLoading -> {
            CircularProgressIndicator()
        }

        state.error != null -> {
            Text(text = state.error!!)
        }

        else -> {
            state.restaurants?.let {
                Scaffold(
                    modifier = modifier,
                    topBar = {
                        TopBar(Modifier.fillMaxWidth())
                    }
                ) { innerPadding ->
                    Column(Modifier.fillMaxWidth().padding(innerPadding)) {
                        RestroItemView(
                            Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),state.restaurants
                        )
                        Row(modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                            .clickable{
                                viewModel.onIntent(RestaurantDetailsIntent.onThumbDownClicked(state.restaurants!!.copy(isHidden = !(state.restaurants!!.isHidden ?: false))))
                            }
                            .background(color = Color(0xfff3f3f3), shape = RoundedCornerShape(8.dp))
                            .border(width = 1.3.dp, color = Color(0xffE3BFB2), shape = RoundedCornerShape(8.dp))
                            .padding(bottom = 8.dp, top = 8.dp, start = 16.dp, end = 16.dp)
                        )
                        {
                            Image(
                                painter = painterResource(id = R.drawable.outline_thumb_down_24),
                                contentDescription = "Down thumb icon",
                                modifier = Modifier
                                    .size(22.dp)
                                    .padding(end = 4.dp),
                                contentScale = ContentScale.FillWidth,
                                colorFilter = if(!(state.restaurants?.isHidden ?: false)) ColorFilter.tint(Color.Black) else ColorFilter.tint(Color.Red)
                            )
                            Text(
                                text = "Hide Restaurant",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                color = if(!(state.restaurants?.isHidden ?: false)) Color(0xff1A1C1C) else Color.Red,
                            )
                        }
                        WriteReviewView(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp))
                        LazyColumn {
                            item {
                                Text(
                                    text = "Recent Reviews",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center,
                                    color = Color(0xff1A1C1C),
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }
                            items(10) {
                                ReviewItemCardView(
                                    Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

    }

}


@Composable
fun ReviewItemCardView(modifier: Modifier) {
    Card(modifier = modifier,
        colors =  CardDefaults.cardColors(containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            Row(Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text(
                        text = "Artisan Kitchen",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        color = Color(0xff1A1C1C),
                    )
                    Text(
                        text = "October 24, 2023",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        color = Color(0xff5f5e5e),
                    )
                }
                Row(
                    modifier = Modifier
                        .background(color = Color(0xffeeeeee), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.star_icon),
                        contentDescription = " star icon",
                        modifier = Modifier
                            .size(14.dp)
                            .padding(end = 4.dp),
                        contentScale = ContentScale.FillWidth
                    )
                    Text(
                        text = "4.8",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color(0xffA43700),
                    )
                }
            }
            Text(
                text = "The steak was cooked to absolute perfection. It\n" +
                        "arrived warm and the packaging was top-notch.\n" +
                        "Definitely ordering from here again!",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                color = Color(0xff5A4138),
            )
        }
    }
}
@Composable
fun WriteReviewView(modifier: Modifier) {
    var review = remember { mutableStateOf("") }
    Card(modifier = modifier,
        colors =  CardDefaults.cardColors(containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp)) {
            Text(
                text = "Write a Review",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xff1A1C1C),
            )
            LazyRow{
                items(5){
                    Image(
                        painter = painterResource(id = R.drawable.star_icon),
                        contentDescription = " star icon",
                        modifier = Modifier
                            .size(26.dp)
                            .padding(end = 4.dp),
                        colorFilter = ColorFilter.tint(Color(0xffA43700))
                    )
                }
            }
            TextField(
                value = review.value,
                onValueChange = {
                    review.value = it
                },
                placeholder = {
                    Text("Share your experience with this restaurant...")
                },
                modifier = Modifier.padding(top = 12.dp).fillMaxWidth().heightIn(min = 150.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color(0xfff3f3f3),
                    unfocusedContainerColor = Color(0xfff3f3f3),
                ),
            )
            CustomButton(
                Modifier.fillMaxWidth().padding(top = 16.dp),
                "Submit Review",
                Color(0xffA43700),
                Color.White
            ) { }
        }
    }}

@Composable
fun RestroItemView(modifier: Modifier, restaurants: RestaurantEntity?) {
        Column(modifier = modifier) {
            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = painterResource(id = getImage(restaurants?.image?:"10")),
                    contentDescription = " item image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(192.dp),
                    contentScale = ContentScale.FillBounds,
                )
                Column(Modifier.align(alignment = Alignment.BottomStart).fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalAlignment = Alignment.Start,) {
                    Text(
                        text = restaurants?.name?:"Unknown",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.star_icon),
                            contentDescription = " star icon",
                            modifier = Modifier
                                .size(20.dp)
                                .padding(end = 4.dp),
                            contentScale = ContentScale.FillWidth,
                            colorFilter = ColorFilter.tint(Color.Yellow)
                        )
                        Text(
                            text = restaurants?.rating?:"5",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                        )
                        Text(
                            text = "(1.2K Reviews)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    }


//@Preview(showBackground = true)
//@Composable
//fun RestaurantDetailsScreenPreview() {
//    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//        RestaurantDetailsScreen(modifier = Modifier.padding(innerPadding), backStack = rememberNavBackStack(Welcome))
//    }
//}