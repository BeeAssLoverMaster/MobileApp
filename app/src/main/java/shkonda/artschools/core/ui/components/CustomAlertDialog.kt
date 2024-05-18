//package shkonda.artschools.core.ui.components
//
//import android.widget.Toast
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.AlertDialog
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import shkonda.artschools.presentation.main_page.home.ImageTextButton
//import shkonda.artschools.presentation.main_page.home.states.AllArtCategoriesState
//
//@Composable
//fun CustomAlertDialog(
//    modifier: Modifier = Modifier,
//    showDialog: Boolean,
//    allArtCategoryState: AllArtCategoriesState
//) {
//    AlertDialog(
//        onDismissRequest = { showDialog = false },
//        title = { Text(text = "Смените искусство!") },
//        text = { Text("Выберите что вы хотите изучать сегодня!") },
//        buttons = {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//            ) {
//                ImageTextButton(
//                    text = allArtCategoryState.categoriesData.artCategories[0].categoryName,
//                    iconUrl = allArtCategoryState.categoriesData.artCategories[0].imageUrl,
//                    onClick = {
//                        Toast.makeText(context, "Опция 1 выбрана", Toast.LENGTH_SHORT).show()
//                        showDialog = false
//                    }
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                ImageTextButton(
//                    text = allArtCategoryState.categoriesData.artCategories[1].categoryName,
//                    iconUrl = allArtCategoryState.categoriesData.artCategories[1].imageUrl,
//                    onClick = {
//                        Toast.makeText(context, "Опция 2 выбрана", Toast.LENGTH_SHORT).show()
//                        showDialog = false
//                    }
//                )
//            }
//        }
//    )
//}