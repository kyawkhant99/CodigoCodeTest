package com.kkh.codigocodetest.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.kkh.codigocodetest.R
import com.kkh.domain.model.MovieItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun DetailScreen(
    movieItem: MovieItem,
    viewModel: HomeViewModel = hiltViewModel(),
    onClickBackArrow: () -> Unit,
) {
    val isSave by viewModel.isSuccessUpdate.collectAsState()
    Log.d("isSave", "DetailScreen: $isSave")
    Scaffold(
        topBar = {
            TopBarDetail {
                onClickBackArrow()
            }
        },
        floatingActionButton = {
            SaveButton(movieItem = movieItem, isSaveSuccess = isSave, onClickSaved = {
                val isSaveCalled = isSave ?: movieItem.isSaved
                if (!isSaveCalled)
                    viewModel.saveMovie(it)
                else viewModel.unSaveMovie(it)
            })
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValues ->

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)

        ) {

            GlideImage(
                model = stringResource(id = R.string.image_base_url) + movieItem.backdropPath,
                contentDescription = "Detail Poster",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Text(
                text = movieItem.originalTitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = movieItem.releaseDate,
                fontSize = 14.sp
            )
            Text(
                text = movieItem.overview,
                fontSize = 16.sp
            )


        }

    }
}

@Composable
fun TopBarDetail(
    onClickBackArrow: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = "Back Arrow",
            modifier = Modifier
                .align(
                    Alignment.TopStart
                )
                .clickable {
                    onClickBackArrow()
                }
        )
        Text(
            text = "Detail",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.align(
                Alignment.Center
            )
        )
    }

}

@SuppressLint("UnrememberedMutableState")
@Composable
fun SaveButton(
    movieItem: MovieItem, onClickSaved: (Int) -> Unit, isSaveSuccess: Boolean?
) {
    val isSaved = mutableStateOf(isSaveSuccess ?: movieItem.isSaved)

    Icon(
        painter = painterResource(id = R.drawable.save_ic),
        contentDescription = "Save Icon",
        tint = if (isSaved.value) Color.Yellow else Color.Gray,
        modifier = Modifier.clickable {
            onClickSaved(movieItem.id)
        }
    )
}