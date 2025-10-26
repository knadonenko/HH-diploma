package ru.practicum.android.diploma.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.LocalTypography
import ru.practicum.android.diploma.ui.theme.blue
import ru.practicum.android.diploma.ui.theme.bottomBarDividerThickness
import ru.practicum.android.diploma.ui.theme.bottomBarHeight
import ru.practicum.android.diploma.ui.theme.emptyDimen
import ru.practicum.android.diploma.ui.theme.grey200
import ru.practicum.android.diploma.ui.theme.grey500

@Composable
fun BottomNavigationBar(
    modifier: Modifier,
    navController: NavController,
    currentDestination: NavDestination?
) {
    val barItems = listOf(
        BottomNavigationItem(
            Routes.MAIN,
            stringResource(R.string.navigation_bottom_bar_label_main),
            ImageVector.vectorResource(R.drawable.ic_main)
        ),
        BottomNavigationItem(
            Routes.FAVOURITES,
            stringResource(R.string.navigation_bottom_bar_label_favourites),
            ImageVector.vectorResource(R.drawable.ic_favourites)
        ),
        BottomNavigationItem(
            Routes.TEAM,
            stringResource(R.string.navigation_bottom_bar_label_team),
            ImageVector.vectorResource(R.drawable.ic_team)
        )
    )

    Column(
        modifier = modifier
    ) {
        HorizontalDivider(
            thickness = bottomBarDividerThickness,
            color = grey200
        )

        NavigationBar(
            modifier = modifier
                .navigationBarsPadding()
                .height(bottomBarHeight),
            containerColor = MaterialTheme.colorScheme.background
        ) {
            barItems.forEach { barItem ->
                val isSelected = currentDestination?.route == barItem.route

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        navController.navigate(barItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(emptyDimen),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = barItem.icon,
                                contentDescription = barItem.title
                            )
                            Text(
                                text = barItem.title,
                                style = LocalTypography.current.body12Regular
                            )
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = blue,
                        unselectedIconColor = grey500,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}
