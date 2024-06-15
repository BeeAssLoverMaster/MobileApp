package shkonda.artschools

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import shkonda.artschools.core.common.getToken
import shkonda.artschools.core.navigation.NavGraph
import shkonda.artschools.core.navigation.NavScreen
import shkonda.artschools.core.ui.theme.ArtSchoolsTheme
import javax.inject.Inject

/* Аннотация @AndroidEntryPoint указывает на то,
   что данный класс будет точкой входа для Dagger Hilt.
   Это позволяет осуществлять внедрение зависимостей
*/
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Внедрение зависимости SharedPreferences, которое будет инициализировано Dagger Hilt
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    // Метод, вызываемый при создании активности
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Устанавливаем содержимое экрана с помощью функции setContent
        setContent {
            // Применяем тему ArtSchoolsTheme к содержимому
            ArtSchoolsTheme {
                // Создаем поверхность (Surface), которая занимает весь экран
                // и использует фоновый цвет темы.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Проверяем, есть ли сохраненный токен в SharedPreferences
                    // Если токен отсутствует или пуст, отображаем начальный граф навигации
                    if (sharedPreferences.getToken().isNullOrEmpty()) {
                        NavGraph()
                    } else {
                        // Если токен существует, устанавливаем начальной точкой графа экран HomeScreen
                        NavGraph(startDestination = NavScreen.HomeScreen.route)
                    }
                }
            }
        }
    }
}




