package com.kkh.codigocodetest.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.kkh.codigocodetest.R
import com.kkh.codigocodetest.ui.theme.ItemBgLight
import com.kkh.domain.model.MovieItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onItemClick: (MovieItem) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val populars = viewModel.popularMovies.collectAsState(initial = emptyList())
    val upcomings = viewModel.upcomingMovies.collectAsState(initial = emptyList())

    Log.d("populars", "HomeScreen: ${populars.value}")

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                modifier = Modifier.size(50.dp)
            )
        }

        Text(
            text = "UPCOMING",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(items = upcomings.value, key = { it.id }) { movie ->
                MovieItem(movieItem = movie, onItemClick = onItemClick) {
                    if (!movie.isSaved)
                        viewModel.saveMovie(it)
                    else viewModel.unSaveMovie(it)
                }
            }
        }

        Text(
            text = "POPULAR",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = populars.value, key = { it.id }) { movie ->
                MovieItem(movieItem = movie, onItemClick = onItemClick) {
                    if (!movie.isSaved)
                        viewModel.saveMovie(it)
                    else viewModel.unSaveMovie(it)
                }
            }
        }

    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(movieItem: MovieItem,onItemClick: (MovieItem) -> Unit={}, onClickSave: (Int) -> Unit = {}) {
    Card(
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = ItemBgLight
        ),
        modifier = Modifier
            .width(125.dp)
            .height(250.dp)
            .clickable {
                onItemClick(movieItem)
            }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            GlideImage(
                model = stringResource(id = R.string.image_base_url) + movieItem.posterPath,
                contentDescription = "Poster Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp)
            )

            Text(
                text = movieItem.title,
                fontSize = 12.sp,
                maxLines = 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.save_ic),
                contentDescription = "Save Icon",
                tint = if (movieItem.isSaved) Color.Yellow else Color.Gray,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.End)
                    .clickable {
                        onClickSave(movieItem.id)
                    }
            )

        }
    }
}

@Composable
fun TopBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Home", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}