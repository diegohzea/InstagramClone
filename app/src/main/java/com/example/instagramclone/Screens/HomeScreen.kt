package com.example.instagramclone.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.instagramclone.api.posts
import com.example.instagramclone.api.users
import com.example.instagramclone.widgets.History
import com.example.instagramclone.widgets.PostWidget
import compose.icons.TablerIcons
import compose.icons.tablericons.*

@Composable
fun HomeScreen(navController: NavHostController) {
    val currentTab = remember { mutableStateOf(0) }

    Scaffold(backgroundColor = Color.Black,
        topBar = {
            BuildAppBar()
        }, bottomBar = {
            BuildTaps(currentTab, navController)
        }) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BuildPosts(navController)
        }
    }
}

@Composable
fun BuildAppBar() {
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = Color.Black
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Instagram",
                    color = Color.White,
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(5.dp),
                )
                Icon(
                    TablerIcons.ChevronDown,
                    contentDescription = "ChevronDown icon",
                    tint = Color.White,
                    modifier = Modifier.size(25.dp)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    TablerIcons.Heart,
                    contentDescription = "Heart icon",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Icon(
                    TablerIcons.BrandMessenger,
                    contentDescription = "BrandMessenger icon",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(15.dp))
            }

        }
    }
}

@Composable
fun BuildHistories() {
    LazyRow(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(users) { user ->
            History(
                imgUrl = user.avatar,
                userName = user.firstName,
                alreadySeen = user.alreadySeen,
                showUserName = true
            )
        }
    }
}

@Composable
fun BuildPosts(navController: NavHostController) {
    LazyColumn() {
        item {
            BuildHistories()
        }
        items(posts) { post ->
            Box(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                PostWidget(post, navController)
            }
        }
    }
}

@Composable
fun BuildTaps(currentTab: MutableState<Int>, navController: NavHostController) {
    val tabs = listOf(
        "Home" to TablerIcons.Home,
        "Search" to TablerIcons.Search,
        "Upload" to TablerIcons.SquarePlus,
        "Reels" to TablerIcons.Movie,
        "Account" to TablerIcons.UserCheck
    )
    TabRow(
        selectedTabIndex = 0,
        backgroundColor = Color.Black,
        modifier = Modifier.height(100.dp)
    ) {
        tabs.forEachIndexed { index, _ ->
            val selected = index == currentTab.value
            Tab(
                selected = selected,
                onClick = { currentTab.value = index },
                modifier = Modifier
                    .padding(bottom = 25.dp)
            ) {
                if (tabs[index].first == "Account") {
                    History(
                        imgUrl = users[0].avatar,
                        userName = users[0].firstName,
                        alreadySeen = true,
                        width = 30,
                        height = 30,
                        modifier = Modifier.clickable {
                            navController.navigate("accountScreen")
                        }
                    )
                } else {
                    Icon(
                        imageVector = tabs[index].second,
                        contentDescription = tabs[index].first,
                        tint = Color.White,
                        modifier = if (selected) Modifier.size(30.dp) else Modifier.size(33.dp)
                    )
                }
            }
        }
    }
}