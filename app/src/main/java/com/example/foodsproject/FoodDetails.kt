package com.example.foodsproject

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.foodsproject.Room.Yemekler
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun FoodDatails(yemekler: Yemekler){
    val snackbarhost= remember {
        SnackbarHostState()
    }
    val scope= rememberCoroutineScope()
     Scaffold(snackbarHost = {
         SnackbarHost(hostState =snackbarhost)
     },
         topBar = {
                  TopAppBar(title = { Text(text = "Sifrais et") }, colors = TopAppBarDefaults.largeTopAppBarColors(
                      containerColor = colorResource(id = R.color.Orange)
                  ))

         },
         content = {
              val activity=(LocalContext.current as Activity)
             Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
                  Image(bitmap = ImageBitmap.imageResource(id = activity.resources.getIdentifier("${yemekler.pictureNAme}","drawable",activity.packageName)), contentDescription ="" , modifier = Modifier.size(250.dp))

                 Text(text = "${yemekler.price}$")
                 Button(onClick = {
                     scope.launch {
                         snackbarhost.showSnackbar("sifaris edilir.....")

                     }
                 }, shape = RoundedCornerShape(4.dp)) {
                     Text(text = "Sifaris et")
                     
                 }

             }
         }
     )
 }