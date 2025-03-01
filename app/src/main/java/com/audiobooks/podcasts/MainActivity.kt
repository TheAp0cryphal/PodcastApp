package com.audiobooks.podcasts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.audiobooks.podcasts.model.Podcast
import com.audiobooks.podcasts.navigation.CustomNavType
import com.audiobooks.podcasts.navigation.PodcastDetails
import com.audiobooks.podcasts.navigation.PodcastList
import com.audiobooks.podcasts.ui.PodcastDetailsScreen
import com.audiobooks.podcasts.ui.PodcastListScreen
import com.audiobooks.podcasts.ui.theme.PodcastsTheme
import kotlin.reflect.typeOf


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            PodcastsTheme {
                NavHost(
                    navController = navController,
                    startDestination = PodcastList,
                    modifier = Modifier.safeDrawingPadding()
                ) {
                    composable<PodcastList> {
                        PodcastListScreen(
                            onShowDetails = { podcast ->
                                navController.navigate(PodcastDetails(podcast))
                            }
                        )
                    }
                    composable<PodcastDetails>(
                        typeMap = mapOf(typeOf<Podcast>() to CustomNavType.PodcastType)
                    ) { backStackEntry ->

                        val route = backStackEntry.toRoute<PodcastDetails>()
                        PodcastDetailsScreen(route.podcast)
                    }
                }
            }
        }
    }
}
