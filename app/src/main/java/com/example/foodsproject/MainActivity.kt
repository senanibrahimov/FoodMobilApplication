package com.example.foodsproject

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodsproject.ui.theme.FoodsProjectTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodsProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PagePass()
                }
            }
        }
    }
}
@Composable
fun PagePass() {
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = "MainActivity" ){

        composable("MainActivity"){

            Main(navController = navController)
        }
        composable("FoodDetails/{Yemekler}", arguments = listOf(
            navArgument(name = "Yemekler"){
                type= NavType.StringType
            }
        )){

          val yemekjson=it.arguments?.getString("Yemekler")
            val yemeks=Gson().fromJson(yemekjson,Yemekler::class.java)
            FoodDatails(yemekler = yemeks)

        }

    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun Main(navController: NavController) {
     val model:FoodsViewmodel= viewModel()
    val foods= model.foods.observeAsState()




    Scaffold(
        topBar = { TopAppBar(title = { Column {
            Text(text = "Yemekler")
            Text(text = "Leziz mekan")
        } } ,colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = colorResource(id = R.color.Orange),
            titleContentColor = Color.White,
        ))

        },
        content = {innerpadding->

            LazyColumn(modifier = Modifier.consumeWindowInsets(innerpadding), contentPadding = innerpadding){

                items(count = foods.value!!.count(),
                      itemContent = {
                             val yemek=foods.value!![it]
                          Card(modifier = Modifier
                              .fillMaxWidth()
                              .padding(5.dp)) {
                              Row(modifier = Modifier.clickable {
                                  val jsonyemek=Gson().toJson(yemek)
                                  navController.navigate("FoodDetails/$jsonyemek")

                              }) {//clickleme row u

                                  Row(modifier = Modifier.fillMaxWidth(),Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                       val activity=(LocalContext.current as Activity)
                                      Image(bitmap = ImageBitmap.imageResource(id = activity.resources.getIdentifier("${yemek.pictureNAme}","drawable",activity.packageName) ),
                                          contentDescription = "",
                                          modifier = Modifier
                                              .size(60.dp)
                                              .clip(CircleShape))
                                      Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween ,modifier = Modifier
                                          .fillMaxSize()
                                          .padding(5.dp)) {
                                       //   Image(painter = painterResource(id =R.drawable.terlan), contentDescription ="" )

                                          Column(verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxHeight(),horizontalAlignment = Alignment.CenterHorizontally) {
                                                Text(text = yemek.name, fontSize = 16.sp)
                                              Spacer(modifier = Modifier.size(17.dp))
                                                 Text(text = "${yemek.price}$", color = Color.Blue, fontSize = 16.sp)
                                          }
                                          Image(painter = painterResource(id = R.drawable.baseline_arrow_forward_24), contentDescription ="" )

                                      }

                                  }
                              }
                          }
                      })
            }

        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FoodsProjectTheme {
      PagePass()
    }
}