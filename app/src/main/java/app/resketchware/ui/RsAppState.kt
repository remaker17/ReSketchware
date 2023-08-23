package app.resketchware.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import app.resketchware.ui.projects.navigation.projectsRoute
import app.resketchware.ui.projects.navigation.navigateToProjects
//import app.resketchware.ui.settings.navigation.settingsRoute
//import app.resketchware.ui.settings.navigation.navigateToSettings
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberRsAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): RsAppState {
    return remember(
        navController,
        coroutineScope
    ) {
        RsAppState(
            navController,
            coroutineScope
        )
    }
}

@Stable
class RsAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    fun navigateBack() {
        navController.popBackStack()
    }

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     */
    fun navigateToProjects() {
        val projectsNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // navigating the same screen
            launchSingleTop = true
            restoreState = true
        }

        navController.navigateToProjects(projectsNavOptions)
    }

    //fun navigateToSettings() {
        //navController.navigateToSettings()
    //}
}
