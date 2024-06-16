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
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import shkonda.artschools.R
import shkonda.artschools.core.common.loadImage
import shkonda.artschools.core.ui.theme.Black
import shkonda.artschools.core.ui.theme.Grapefruit
import shkonda.artschools.core.ui.theme.LightGray
import shkonda.artschools.core.ui.theme.Sunset
import shkonda.artschools.domain.model.schools.School
import shkonda.artschools.presentation.school_page.states.getAllSchools

data class ArtSchool(
    val id: Long,
    val name: String,
    val description: String,
    val location: String,
//    val imageUrl: String,
    val imageUrl: Int,
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
fun ArtSchoolScreen(
    modifier: Modifier = Modifier,
    viewModel: ArtSchoolViewModel = hiltViewModel()
) {
    val schoolList = getAllSchools(viewModel)
    var selectedType by remember { mutableStateOf(ArtSchoolType.All) }
    var selectedLocation by remember { mutableStateOf("Все") }
    var selectedPrograms by remember { mutableStateOf(listOf<String>()) }
    var searchQuery by remember { mutableStateOf("") }
    var filtersVisible by remember { mutableStateOf(false) }

    var selectedSchool by remember { mutableStateOf<School?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val locations = listOf("Все") + schoolList.map { it.city }.distinct()
    val programs = schoolList.flatMap { it.programList.map { it.programName } }.distinct()

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 24).value.sp

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(1.dp),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
            )
            Column(modifier = modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = {
                            Text(
                                text = "Поиск школ",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = textSize
                                ),
                            )
                        },
                        modifier = Modifier.weight(1f),
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = textSize
                        ),
                    )
                    IconButton(onClick = { filtersVisible = !filtersVisible }) {
                        Icon(
                            imageVector = Icons.Default.Create,
                            contentDescription = "Фильтр",
                            tint = Color.White
                        )
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

                Spacer(modifier = Modifier.height(16.dp))

                val filteredSchools = schoolList.filter { school ->
                    (selectedLocation == "Все" || school.city == selectedLocation) &&
                            (selectedPrograms.isEmpty() || selectedPrograms.any { it in school.programList.map { it.programName } }) &&
                            (selectedType == ArtSchoolType.All || school.artCategoryName == selectedType.displayName) &&
                            (searchQuery.isBlank() ||
                                    school.schoolName.contains(searchQuery, ignoreCase = true) ||
                                    school.description.contains(searchQuery, ignoreCase = true) ||
                                    school.city.contains(searchQuery, ignoreCase = true))
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
            ArtSchoolType.values().forEach { type ->
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
                            onCheckedChange = null
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
    }
}

@Composable
fun ArtSchoolCard(
    modifier: Modifier,
    artSchool: School
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSchoolNameSize = (screenWidth / 18).value.sp
    val textSchoolAddressSize = (screenWidth / 32).value.sp

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize(),  // Добавлено для анимации изменения размера
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            ImageCard(
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
                    .height(210.dp),
                imageUrl = artSchool.schoolImageName
            )
            Column(modifier = modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)) {
                Text(
                    text = artSchool.schoolName,
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        fontSize = textSchoolNameSize
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Text(
                    text = "${artSchool.city}, ${artSchool.street}",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        fontSize = textSchoolAddressSize
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
fun ImageCard(modifier: Modifier, imageUrl: String) {
    AsyncImage(
        modifier = modifier,
        model = loadImage(context = LocalContext.current, imageUrl),
        contentDescription = "Изображение категорий",
        contentScale = ContentScale.Crop
    )
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
fun ArtSchoolDialog(modifier: Modifier, school: School, onDismiss: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSchoolNameSize = (screenWidth / 14).value.sp
    val textSchoolAddressSize = (screenWidth / 24).value.sp
    val labelSize = (screenWidth / 16).value.sp
    val defaultTextSize = (screenWidth / 24).value.sp
    val primaryColor = Color(0xFF6200EE)  // Основной акцентный цвет
    val secondaryColor = Color(0xFFEEEEEE)  // Вторичный акцентный цвет

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
                .background(
                    color = Color.LightGray, shape = RoundedCornerShape(12.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = school.schoolName,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = primaryColor,
                        fontSize = textSchoolNameSize
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "${school.city}, ${school.street}",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        fontSize = textSchoolAddressSize
                    ),
                )

                Spacer(modifier = Modifier.height(8.dp))

                SectionTitle("Описание", primaryColor, labelSize)
                Text(
                    text = school.description,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = defaultTextSize
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                SectionTitle("Направления обучения", primaryColor, labelSize)
                Column {
                    school.programList.forEach { program ->
                        ProgramCard(programName = program.programName, secondaryColor)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

//                SectionTitle("Преподавательский состав", primaryColor, labelSize)
                // Здесь можно добавить информацию о преподавательском составе
            }
        }
    }
}

@Composable
fun SectionTitle(title: String, color: Color, fontSize: TextUnit) {
    Text(
        text = title,
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            color = color,
            fontSize = fontSize
        )
    )
}

@Composable
fun ProgramCard(programName: String, backgroundColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = programName,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}


@Composable
fun ArtWorksGallery(artWokrsImageList: String) {
    /*var showDialog by remember { mutableStateOf(false) }
    var selectedImage by remember { mutableStateOf<BioArticleImage?>(null) }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val imageSize = (screenWidth / 2)

    if (showDialog && selectedImage != null) {
        BioImageDialog(modifier = modifier, image = selectedImage) {
            showDialog = false
        }
    }

    LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        items(bioArticleImageList) { index ->
            AsyncImage(
                modifier = modifier
                    .size(imageSize)
                    .clip(RoundedCornerShape(4.dp))
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF8372DB), Color(0xFF79B27D)),
                            start = Offset(0f, 0f), // Top-center
                            end = Offset(0f, Float.POSITIVE_INFINITY)
                        ),
                        shape = RoundedCornerShape(4.dp)
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
