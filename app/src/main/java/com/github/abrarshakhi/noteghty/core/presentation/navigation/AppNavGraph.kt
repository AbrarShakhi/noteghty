import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.github.abrarshakhi.noteghty.note.presentation.navigation.NoteRoute
import com.github.abrarshakhi.noteghty.note.presentation.navigation.noteNavGraph


@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NoteRoute.Graph.pattern) {
        noteNavGraph(navController)
    }
}