package ru.practicum.android.diploma.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import ru.practicum.android.diploma.R

@Composable
fun BottomNavigationBar(
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
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )

        NavigationBar(modifier = Modifier.background(MaterialTheme.colorScheme.secondary)) {
            barItems.forEach { batItem ->
                val isSelected = currentDestination?.route == batItem.route

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        navController.navigate(batItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = batItem.icon,
                            contentDescription = batItem.title
                        )
                    },
                    label = { Text(batItem.title) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onSecondary,
                        selectedTextColor = MaterialTheme.colorScheme.onSecondary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                )
            }
        }
    }
}
