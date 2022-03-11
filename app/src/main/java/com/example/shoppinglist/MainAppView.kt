package com.example.shoppinglist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


const val HOME_ROUTE = "home"
const val LIST_ROUTE = "list"
const val NEWS_ROUTE = "news"

@Composable
fun MainView() {
    val userVM = viewModel<UserViewModel>()

    if(userVM.username.value.isEmpty()){
        LoginView(userVM)
    }else {
        MainScaffoldView()
    }
}

@Composable
fun MainScaffoldView() {

    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBarView() },
        bottomBar = { BottomBarView(navController) },
        content = { MainContentView(navController) }
    )
}

@Composable
fun MainContentView(navController: NavHostController) {
    val listVM = viewModel<ShoppingListViewModel>()

    NavHost(navController = navController, startDestination = HOME_ROUTE ){
        composable( route = HOME_ROUTE ){ HomeView() }
        composable( route = LIST_ROUTE){ ListView(listVM) }
        composable( route = NEWS_ROUTE){ NewsView() }
    }
}

@Composable
fun HomeView() {

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF6F6F6)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {
        Text(
            text = "Welcome to your shopping list and news!")
    }
}

@Composable
fun ListView(listVM: ShoppingListViewModel) {

    var itemText by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFFFFFFF))
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = itemText ,
            onValueChange = { itemText = it },
            label = { Text(text = "Type here") })

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = { listVM.addItem( Item(itemText) ) }
        ) {
            Text(text = "Add new item")
        }

        Spacer(modifier = Modifier.height(10.dp))

        listVM.items.value.forEach {
            Divider(thickness = 3.dp)
            Text(text = it.message)
        }
        Divider(thickness = 3.dp)
    }
}

@Composable
fun NewsView() {

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF6F6F6)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {
        Text(
            text = "Here is the news.")
    }
}

@Composable
fun BottomBarView(navController: NavHostController) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFFE5E5E5)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = R.drawable.ic_home),
            contentDescription = "home",
            modifier = Modifier.clickable {  navController.navigate(HOME_ROUTE)  })
        Icon(
            painter = painterResource(id = R.drawable.ic_cart),
            contentDescription = "list",
            modifier = Modifier.clickable {  navController.navigate(LIST_ROUTE)  })
        Icon(
            painter = painterResource(id = R.drawable.ic_news),
            contentDescription = "news",
            modifier = Modifier.clickable {  navController.navigate(NEWS_ROUTE)  })
    }
}

@Composable
fun TopBarView() {
    val userVM = viewModel<UserViewModel>()

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFFE5E5E5))
        .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = userVM.username.value)
        Icon(
            painter = painterResource(id = R.drawable.ic_logout),
            contentDescription = "logout",
            modifier = Modifier.clickable { userVM.logoutUser() })
        }
}


@Composable
fun LoginView(userVM: UserViewModel) {
    var email by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email , 
            onValueChange = { email = it },
            label = { Text(text = "Email") })
        OutlinedTextField(
            value = pw ,
            onValueChange = { pw = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation())
        OutlinedButton(onClick = { userVM.loginUser(email,pw) }) {
            Text(text = "Login")
        }
    }
}