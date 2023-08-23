package app.resketchware.ui.projects

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import app.resketchware.R
import app.resketchware.ui.core.component.ProjectEntry
import app.resketchware.ui.core.component.RsTopAppBar
import app.resketchware.ui.core.model.Project
import app.resketchware.ui.util.drawHorizontalStroke

@Composable
fun ProjectsRoute(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    onNewProjectClick: () -> Unit,
    onProjectClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    ProjectsScreen(
        modifier = modifier,
        scope = scope,
        onNewProjectClick = onNewProjectClick,
        onProjectClick = onProjectClick,
        onSettingsClick = onSettingsClick
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProjectsScreen(
    modifier: Modifier,
    scope: CoroutineScope,
    onNewProjectClick: () -> Unit,
    onProjectClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val lazyListState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()

    val fakeProjectsList = listOf(
        Project(name = "Fake project 1", path = "/storage/emulated/0/Android/data/app.resketchware/projects/FakeProject1"),
        Project(name = "Fake project 2", path = "/storage/emulated/0/Android/data/app.resketchware/projects/FakeProject2"),
        Project(name = "Fake project 3", path = "/storage/emulated/0/Android/data/app.resketchware/projects/FakeProject3"),
        Project(name = "Fake project 4", path = "/storage/emulated/0/Android/data/app.resketchware/projects/FakeProject4"),
        Project(name = "Fake project 5", path = "/storage/emulated/0/Android/data/app.resketchware/projects/FakeProject5"),
        Project(name = "Fake project 6", path = "/storage/emulated/0/Android/data/app.resketchware/projects/FakeProject6"),
        Project(name = "Fake project 7", path = "/storage/emulated/0/Android/data/app.resketchware/projects/FakeProject7"),
        Project(name = "Fake project 8", path = "/storage/emulated/0/Android/data/app.resketchware/projects/FakeProject8"),
        Project(name = "Fake project 9", path = "/storage/emulated/0/Android/data/app.resketchware/projects/FakeProject9"),
        Project(name = "Fake project 10", path = "/storage/emulated/0/Android/data/app.resketchware/projects/FakeProject10")
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        drawerContent = {
            Text("Not implemented yet")
        },
        topBar = {
            RsTopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawHorizontalStroke(height = 1.dp),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(R.string.app_name))
                    }
                },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Rounded.Settings,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Bottom + WindowInsetsSides.End)),
                onClick = onNewProjectClick
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
                .padding(contentPadding),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = WindowInsets.navigationBars.only(WindowInsetsSides.Bottom).asPaddingValues().calculateBottomPadding()),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(fakeProjectsList) {
                ProjectEntry(
                    project = it,
                    onEntryClick = onProjectClick
                )
            }
        }
    }
}
