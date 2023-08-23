package app.resketchware.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import app.resketchware.ui.RsAppState
import app.resketchware.ui.projects.navigation.projectsRoute
import app.resketchware.ui.projects.navigation.projectsScreen
// import app.resketchware.ui.settings.navigation.settingsScreen
// import app.resketchware.ui.settings.navigation.navigateToPreference
// import app.resketchware.ui.settings.page.about.navigation.aboutScreen
// import app.resketchware.ui.settings.page.compiler.navigation.compilerScreen
// import app.resketchware.ui.settings.page.editor.navigation.editorScreen
// import app.resketchware.ui.settings.page.general.navigation.generalScreen
// import app.resketchware.ui.settings.page.plugins.navigation.pluginsScreen

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 */
@Composable
fun RsNavHost(
    appState: RsAppState,
    modifier: Modifier = Modifier,
    startDestination: String = projectsRoute,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        projectsScreen(
            scope = appState.coroutineScope,
            onNewProjectClick = {},
            onProjectClick = {},
            onSettingsClick = {} // appState::navigateToSettings
        )
        // settingsScreen(
            // onBackClick = appState::navigateBack,
            // onPreferenceClick = navController::navigateToPreference,
            // nestedGraphs = {
                // aboutScreen(onBackClick = appState::navigateBack)
                // compilerScreen(onBackClick = appState::navigateBack)
                // editorScreen(onBackClick = appState::navigateBack)
                // generalScreen(onBackClick = appState::navigateBack)
                // pluginsScreen(onBackClick = appState::navigateBack)
            // }
        // )
    }
}
