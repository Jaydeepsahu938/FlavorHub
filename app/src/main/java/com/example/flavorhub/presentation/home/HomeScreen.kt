package com.example.flavorhub.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flavorhub.R
import com.example.flavorhub.core.AppDatabase
import com.example.flavorhub.core.RetrofitClient
import com.example.flavorhub.data.RestaurantRepoImpl
import com.example.flavorhub.domain.entity.RestaurantEntity
import com.example.flavorhub.presentation.registration.CustomEditText

@Composable
fun HomeScreen(modifier: Modifier,navigateToRestaurantDetailsScreen:(id:String)->Unit) {
    val context = LocalContext.current
    val repo = RestaurantRepoImpl(AppDatabase.getInstance(context).restaurantDao(), RetrofitClient.apiService)
    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(repo)
    )
    val searchText = remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()

    when {

        state.isLoading -> {
            CircularProgressIndicator()
        }

        state.error != null -> {
            Text(text = state.error!!)
        }

        else -> {

            Scaffold(
                modifier = modifier,
                topBar = {
                    TopBar(Modifier.fillMaxWidth())
                }
            ) { innerPadding ->
                Column(Modifier.fillMaxWidth().padding(innerPadding)) {
                    LazyColumn {
                        item {
                            CustomEditText(
                                Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                                "",
                                "Search restaurant... ",
                                R.drawable.outline_search_24,
                                onTextChange = {}
                            )
                        }
                        item {
                            Text(
                                text = "Popular Restaurants",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                color = Color(0xff1A1C1C),
                                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                            )
                        }
                        items(state.restaurants) {item ->
                            RestroItemCardView(
                                Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
                                item
                            ){
                                viewModel.OnIntent(HomeIntent.itemClicked(item.id))
                            }
                        }
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {
                is HomeScreenEffect.NavigateToRestaurantDetailsScreen -> {
                    navigateToRestaurantDetailsScreen(it.id)
                }

                is HomeScreenEffect.showError -> {

                }
            }
        }
    }
}

@Composable
fun TopBar(modifier: Modifier) {
    Row(modifier = modifier.padding(top = 20.dp)) {
        Text(
            text = "FlavorHub",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color(0xffA43700),
            modifier = Modifier.padding(start = 20.dp)
        )
    }
}

@Composable
fun RestroItemCardView(modifier: Modifier, item: RestaurantEntity,onCardClicked:()->Unit) {
    Card(modifier = modifier.clickable{ onCardClicked()},
        colors =  CardDefaults.cardColors(containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = getImage(item.image)),
                    contentDescription = " item image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(192.dp),
                    contentScale = ContentScale.FillWidth
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(horizontal = 30.dp, vertical = 16.dp)
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
                        text = item.rating,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color(0xffA43700),
                    )
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(horizontal = 30.dp, vertical = 25.dp)
                        .background(color = Color(0xffeeeeee), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "30-40 min",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color(0xffA43700),
                    )
                }
            }
            Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = item.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = Color(0xff1A1C1C),
                )
                Text(
                    text = "$"+(100..1000).random().toString(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = Color(0xff5f5e5e),
                )
            }
            Text(
                text =item.dishes,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                color = Color(0xff5f5e5e),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                maxLines = 2
            )
        }
    }
}

fun getImage(id: String):Int{
    val list = listOf(R.drawable.dish1,R.drawable.dish2,R.drawable.dish3,R.drawable.dish4,R.drawable.dish5,R.drawable.dish6,R.drawable.dish7,R.drawable.dish8,R.drawable.dish9,R.drawable.dish10,R.drawable.dish11,R.drawable.dish12,R.drawable.dish13,R.drawable.dish14,R.drawable.dish15,R.drawable.dish16,R.drawable.dish17,R.drawable.dish18,R.drawable.dish19)
    return list[id.toInt()-1]
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
//    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//        HomeScreen(modifier = Modifier.padding(innerPadding), backStack = rememberNavBackStack(Welcome))
//    }
}