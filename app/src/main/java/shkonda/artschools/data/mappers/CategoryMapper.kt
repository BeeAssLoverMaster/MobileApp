package shkonda.artschools.data.mappers

import shkonda.artschools.data.data_source.category.entity.CategoriesDto
import shkonda.artschools.data.data_source.category.entity.CategoryDto
import shkonda.artschools.domain.model.category.Categories
import shkonda.artschools.domain.model.category.Category

//fun CategoriesDto.toCategories(): Categories {
//    return Categories(
//        categories = categories.map {
//            Category(
//                id = it.id,
//                categoryName = it.categoryName
//            )
//        } as ArrayList<Category>
//    )
//}

fun List<CategoryDto>.toCategories(): Categories {
    return Categories(
        categories = this.map { dto ->
            Category(
                id = dto.id,
                categoryName = dto.categoryName,
                categoryImage = dto.categoryImage
            )
        } as ArrayList<Category>
    )
}