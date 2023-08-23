package app.resketchware.ui.projects.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import app.resketchware.ui.core.common.animatedComposable
import app.resketchware.ui.projects.ProjectsRoute
import kotlinx.coroutines.CoroutineScope

const val projectsRoute = "projects_route"

fun NavController.navigateToProjects(navOptions: NavOptions? = null) {
    this.navigate(projectsRoute, navOptions)
}

fun NavGraphBuilder.projectsScreen(
    scope: CoroutineScope,
    onNewProjectClick: () -> Unit,
    onProjectClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    animatedComposable(route = projectsRoute) {
        ProjectsRoute(
            scope = scope,
            onNewProjectClick = onNewProjectClick,
            onProjectClick = onProjectClick,
            onSettingsClick = onSettingsClick
        )
    }
}
