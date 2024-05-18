package shkonda.artschools.data.mappers

import shkonda.artschools.data.data_source.articles.entity.ArticlesDto
import shkonda.artschools.data.data_source.articles.entity.ArtistArticleDto
import shkonda.artschools.data.data_source.articles.entity.BioArticleImageDto
import shkonda.artschools.domain.model.article.Article
import shkonda.artschools.domain.model.article.BioArticleImage
import shkonda.artschools.domain.model.article.Articles
import shkonda.artschools.domain.model.article.ArtistArticle

fun ArticlesDto.toArticle(): Articles {
    return Articles(
        articlesList = this.articlesList.map { dto ->
            Article(
                id = dto.id,
                title = dto.title,
                text = dto.text,
                articleCategoryId = dto.articleCategoryId,
                artGenreId = dto.artGenreId
            )
        }
    )
}

fun ArtistArticleDto.toBioArtistArticle(): ArtistArticle {
    return ArtistArticle(
        artistId = this.artistId,
        artistName = this.artistName,
        artistImage = this.artistImage,
        articleId = this.articleId,
        title = this.title,
        text = this.text,
        bioArticleImages = this.articleImages.map { it.toBioArticleImage() }
    )
}

fun BioArticleImageDto.toBioArticleImage(): BioArticleImage {
    return BioArticleImage(
        imageName = this.imageName,
        imageDescription = this.imageDescription
    )
}
