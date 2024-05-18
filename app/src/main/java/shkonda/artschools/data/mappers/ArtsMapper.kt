package shkonda.artschools.data.mappers

import shkonda.artschools.data.data_source.arts.category.entity.ArtCategoriesDto
import shkonda.artschools.data.data_source.arts.category.entity.ArtCategoryDto
import shkonda.artschools.data.data_source.arts.genre.entity.ArtGenresDto
import shkonda.artschools.data.data_source.arts.type.entity.ArtTypesDto
import shkonda.artschools.domain.model.arts.ArtCategories
import shkonda.artschools.domain.model.arts.ArtCategory
import shkonda.artschools.domain.model.arts.ArtGenre
import shkonda.artschools.domain.model.arts.ArtGenres
import shkonda.artschools.domain.model.arts.ArtType
import shkonda.artschools.domain.model.arts.ArtTypes

fun ArtCategoryDto.toArtCategory(): ArtCategory {
    return ArtCategory(
        id = this.id,
        categoryName = this.categoryName,
        imageUrl = this.categoryImageUrl
    )
}

fun ArtCategoriesDto.toArtCategories(): ArtCategories {
    return ArtCategories(
        artCategories = this.artCategories.map { dto ->
            ArtCategory(
                id = dto.id,
                categoryName = dto.categoryName,
                imageUrl = dto.categoryImageUrl
            )
        }
    )
}

fun ArtTypesDto.toArtTypes(): ArtTypes {
    return ArtTypes(
        types = this.artTypes.map { dto ->
            ArtType(
                id = dto.id,
                typeName = dto.typeName,
                imageName = dto.imageName,
                artCategory = dto.artCategory
            )
        }
    )
}

fun ArtGenresDto.toArtGenres(): ArtGenres {
    return ArtGenres(
        genres = this.artTypes.map { dto ->
            ArtGenre(
                id = dto.id,
                genreName = dto.genreName,
                imageNameUrl = dto.typeNameUrl,
                artTypeId = dto.artTypeId
            )
        }
    )
}