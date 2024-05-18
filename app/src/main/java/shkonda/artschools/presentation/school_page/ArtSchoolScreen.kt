package shkonda.artschools.presentation.school_page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import shkonda.artschools.R
import shkonda.artschools.core.common.loadImage
import shkonda.artschools.core.ui.theme.Black
import shkonda.artschools.core.ui.theme.Grapefruit
import shkonda.artschools.core.ui.theme.LightGray
import shkonda.artschools.core.ui.theme.Sunset

data class ArtSchool(
    val id: Long,
    val name: String,
    val description: String,
    val location: String,
    val imageUrl: String,
    val type: ArtSchoolType,
    val programs: List<String>
)

enum class ArtSchoolType(val displayName: String) {
    All("Все"),
    Visual("Изобразительное искусство"),
    Music("Музыкальное искусство")
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArtSchoolScreen(modifier: Modifier = Modifier, artSchools: List<ArtSchool>) {
    var selectedType by remember { mutableStateOf(ArtSchoolType.All) }
    var selectedLocation by remember { mutableStateOf("Все") }
    var selectedPrograms by remember { mutableStateOf(listOf<String>()) }
    var searchQuery by remember { mutableStateOf("") }
    var filtersVisible by remember { mutableStateOf(false) }
    var searchVisible by remember { mutableStateOf(false) }

    var selectedSchool by remember { mutableStateOf<ArtSchool?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val locations = listOf("Все") + artSchools.map { it.location }.distinct()
    val programs = artSchools.flatMap { it.programs }.distinct()

    Column(modifier = modifier.fillMaxSize()) {
        Row {
            Button(onClick = {
                filtersVisible = !filtersVisible
                if (filtersVisible) searchVisible = false  // Скрыть поиск при открытии фильтров
            }) {
                Text(if (filtersVisible) "Скрыть фильтры" else "Показать фильтры")
            }
            Button(onClick = {
                searchVisible = !searchVisible
                if (searchVisible) filtersVisible = false  // Скрыть фильтры при открытии поиска
            }) {
                Text(if (searchVisible) "Скрыть поиск" else "Показать поиск")
            }
        }

        AnimatedVisibility(
            visible = filtersVisible,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column {
                FilterChips(
                    selectedType = selectedType,
                    selectedLocation = selectedLocation,
                    selectedPrograms = selectedPrograms,
                    onRemoveType = { selectedType = ArtSchoolType.All },
                    onRemoveLocation = { selectedLocation = "Все" },
                    onRemoveProgram = { program ->
                        selectedPrograms = selectedPrograms.filterNot { it == program }
                    }
                )
                ArtSchoolFilter(
                    selectedType = selectedType,
                    onTypeSelected = { selectedType = it },
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { searchQuery = it }
                )
                LocationSelector(
                    locations,
                    selectedLocation,
                    onLocationSelected = { selectedLocation = it }
                )
                ProgramSelector(
                    programs,
                    selectedPrograms,
                    onProgramSelected = { selectedPrograms = it }
                )
            }
        }

        AnimatedVisibility(
            visible = searchVisible,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Поиск школ") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val filteredSchools = artSchools.filter { school ->
            (selectedLocation == "Все" || school.location == selectedLocation) &&
                    (selectedPrograms.isEmpty() || selectedPrograms.any { it in school.programs }) &&
                    (selectedType == ArtSchoolType.All || school.type == selectedType) &&
                    // Добавляем фильтрацию по поисковому запросу
                    (searchQuery.isBlank() ||
                            school.name.lowercase().contains(searchQuery.lowercase()) ||
                            school.description.lowercase().contains(searchQuery.lowercase()) ||
                            school.location.lowercase().contains(searchQuery.lowercase()))
        }


        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredSchools, itemContent = { school ->
                ArtSchoolCard(
                    modifier = modifier
                        .animateItemPlacement(spring(Spring.DampingRatioMediumBouncy))
                        .clickable {
                            selectedSchool = school
                            showDialog = true
                        },
                    artSchool = school
                )
            })
        }

        if (showDialog && selectedSchool != null) {
            ArtSchoolDialog(
                modifier = modifier,
                school = selectedSchool!!,
                onDismiss = { showDialog = false })
        }
    }
}

@Composable
fun ArtSchoolTypeSelector(selectedType: ArtSchoolType, onTypeSelected: (ArtSchoolType) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextButton(onClick = { expanded = true }) {
            Text(selectedType.displayName)
            Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ArtSchoolType.entries.forEach { type ->
                DropdownMenuItem(
                    text = {
                        Text(text = type.displayName)
                    },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun LocationSelector(
    locations: List<String>,
    selectedLocation: String,
    onLocationSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        TextButton(onClick = { expanded = true }) {
            Text(selectedLocation)
            Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            locations.forEach { location ->
                DropdownMenuItem(
                    text = {
                        Text(location)
                    },
                    onClick = {
                        onLocationSelected(location)
                        expanded = false
                    })
            }
        }
    }
}

@Composable
fun ProgramSelector(
    programs: List<String>,
    selectedPrograms: List<String>,
    onProgramSelected: (List<String>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        TextButton(onClick = { expanded = true }) {
            Text(
                if (selectedPrograms.isEmpty()) "Выберите программы" else selectedPrograms.joinToString(
                    ", "
                )
            )
            Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            programs.forEach { program ->
                val isSelected = program in selectedPrograms
                androidx.compose.material.DropdownMenuItem(
                    onClick = {
                        onProgramSelected(
                            if (isSelected) selectedPrograms - program else selectedPrograms + program
                        )
                    }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = isSelected,
                            onCheckedChange = null // We'll handle changing in the onClick of the MenuItem
                        )
                        Text(text = program)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Поиск") },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Поиск") },
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
    )
}

@Composable
fun ArtSchoolFilter(
    selectedType: ArtSchoolType,
    onTypeSelected: (ArtSchoolType) -> Unit,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit
) {
    Column {
        ArtSchoolTypeSelector(selectedType, onTypeSelected)
//        SearchBar(searchQuery, onSearchQueryChanged)
    }
}

@Composable
fun ArtSchoolCard(
    modifier: Modifier,
    artSchool: ArtSchool
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(330.dp)
            .animateContentSize(),  // Добавлено для анимации изменения размера
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column() {
            ImageCard(
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
                    .height(210.dp),
                imageUrl = artSchool.imageUrl
            )
            Column(modifier = modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)) {
                Text(
                    text = artSchool.name,
                    style = MaterialTheme.typography.h6,
                    fontSize = 28.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Text(
                    text = artSchool.location,
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun ImageCard(modifier: Modifier, imageUrl: String) {
    /*AsyncImage(
            model = imageUrl,
            contentDescription = "School Image",
            modifier = modifier,
            contentScale = ContentScale.Crop
        )*/
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.first_school),
        contentDescription = "Школа",
        contentScale = ContentScale.Crop
    )
}

val fakeArtSchoolList = listOf(
    ArtSchool(
        id = 0L,
        name = "Художественная школа имения Цивелёва Г.А.",
        description = "Прекрасная школа, которая обучает искусству по методике Дани Трэгхо, великого искусствоведа",
        location = "Комсомольск-на-Амуре, ул. Пушкина, дом Колотушкина",
        imageUrl = "books.png",
        type = ArtSchoolType.Music,
        programs = listOf("violin", "piano", "guitar")
    ),
    ArtSchool(
        id = 1L,
        name = "Danny Trejo Art School No. 1",
        description = "Прекрасная школа, которая обучает искусству по методике Дани Трэгхо, великого искусствоведа",
        location = "Хабаровск, ул. Пушкина, дом Колотушкина",
        imageUrl = "books.png",
        type = ArtSchoolType.Music,
        programs = listOf("violin", "piano", "guitar", "balalaika")
    ),
    ArtSchool(
        id = 2L,
        name = "Danny Trejo Art School No. 2",
        description = "Прекрасная школа, которая обучает искусству по методике Дани Трэгхо, великого искусствоведа",
        location = "Комсомольск-на-Амуре, ул. Пушкина, дом Колотушкина",
        imageUrl = "books.png",
        type = ArtSchoolType.Music,
        programs = listOf("violin", "piano", "guitar", "gormoshka")
    ),
    ArtSchool(
        id = 3L,
        name = "Danny Trejo Art School No. 3",
        description = "Прекрасная школа, которая обучает искусству по методике Дани Трэгхо, великого искусствоведа",
        location = "Владивосток, ул. Пушкина, дом Колотушкина",
        imageUrl = "books.png",
        type = ArtSchoolType.Music,
        programs = listOf("violin", "piano")
    ),
    ArtSchool(
        id = 4L,
        name = "Школа Искусств №1 имени Макси Ляхо",
        description = "Прекрасная школа, которая обучает искусству по методике Дани Трэгхо, великого искусствоведа",
        location = "Комсомольск-на-Амуре, ул. Пушкина, дом Колотушкина",
        imageUrl = "books.png",
        type = ArtSchoolType.Visual,
        programs = listOf("paint")
    )
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ArtScoolScreenPrev() {
    ArtSchoolScreen(modifier = Modifier, artSchools = fakeArtSchoolList)
}

@Composable
fun FilterChips(
    selectedType: ArtSchoolType,
    selectedLocation: String,
    selectedPrograms: List<String>,
    onRemoveType: () -> Unit,
    onRemoveLocation: () -> Unit,
    onRemoveProgram: (String) -> Unit
) {
    Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.Start) {
        if (selectedType != ArtSchoolType.All) {
            Chip(label = selectedType.displayName, onRemove = onRemoveType, isSelected = true)
        }
        if (selectedLocation != "Все") {
            Chip(label = selectedLocation, onRemove = onRemoveLocation, isSelected = true)
        }
        selectedPrograms.forEach { program ->
            Chip(label = program, onRemove = { onRemoveProgram(program) }, isSelected = true)
        }
    }
}

@Composable
fun Chip(label: String, onRemove: () -> Unit, isSelected: Boolean) {
    val backgroundColor =
        animateColorAsState(targetValue = if (isSelected) MaterialTheme.colors.primary else Color.Transparent)
    Row(
        modifier = Modifier
            .padding(4.dp)
            .animateContentSize()
            .background(backgroundColor.value, shape = RoundedCornerShape(8.dp))
    ) {
        Text(text = label, modifier = Modifier.padding(8.dp))
        IconButton(onClick = onRemove) {
            Icon(Icons.Default.Close, contentDescription = "Удалить")
        }
    }
}

@Composable
fun ArtSchoolDialog(modifier: Modifier, school: ArtSchool, onDismiss: () -> Unit) {
    var schoolPrograms: String = ""
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)  // Устанавливаем отступы со всех сторон в 16.dp
                .background(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),  // Добавляем вертикальный скролл
            ) {
                ImageCard(
                    modifier = modifier
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                        .fillMaxWidth()
                        .align(Alignment.Start)
                        .height(210.dp),
                    imageUrl = ""
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)){
                    Text(text = school.name, fontSize = 24.sp, color = Black)
                    Text(text = school.location, fontSize = 15.sp, color = LightGray)

                    Text(text = "Описание", fontSize = 16.sp, color = Black)
                    Text(text = school.description, color = Black)

                    Text(text = "Направления", fontSize = 16.sp, color = Black)
                    school.programs.forEach { program ->
                        schoolPrograms += " " + program
                    }
                    Text(text = schoolPrograms)

                    Text(text = "Работы школы")
                    ArtWorksGallery()

                    Text(text = "Преподавательский состав")
                    TeachingStaffGallery()
                }
            }
        }
    }
}

@Composable
fun ArtWorksGallery(modifier: Modifier = Modifier) {
    /* LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
         items(bioArticleImageList) { index ->
             Surface(
                 shape = RoundedCornerShape(8.dp),
                 modifier = modifier.padding(vertical = 8.dp)
             ) {
                 AsyncImage(
                     modifier = modifier
                         .size(125.dp)
                         .border(
                             border = BorderStroke(
                                 width = 2.dp, brush = Brush.horizontalGradient(
                                     colors = list.of(Sunset, Grapefruit)
                                 )
                             ),
                             shape = RoundedCornerShape(8.dp)
                         )
                         .clickable {
                             selectedImage = index
                             showDialog = true
                         },
                     model = loadImage(context = LocalContext.current, imageUrl = index.imageName),
                     contentDescription = index.imageDescription,
                     contentScale = ContentScale.Crop
                 )
             }
         }
     }*/
}

@Composable
fun TeachingStaffGallery(modifier: Modifier = Modifier) {
    /* LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
       items(bioArticleImageList) { index ->
           Surface(
               shape = RoundedCornerShape(8.dp),
               modifier = modifier.padding(vertical = 8.dp)
           ) {
               AsyncImage(
                   modifier = modifier
                       .size(125.dp)
                       .border(
                           border = BorderStroke(
                               width = 2.dp, brush = Brush.horizontalGradient(
                                   colors = list.of(Sunset, Grapefruit)
                               )
                           ),
                           shape = RoundedCornerShape(8.dp)
                       )
                       .clickable {
                           selectedImage = index
                           showDialog = true
                       },
                   model = loadImage(context = LocalContext.current, imageUrl = index.imageName),
                   contentDescription = index.imageDescription,
                   contentScale = ContentScale.Crop
               )
           }
       }
   }*/
}
