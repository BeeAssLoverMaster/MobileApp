package shkonda.artschools.data.mappers

import shkonda.artschools.data.data_source.user.entity.UpdateProfileBodyDto
import shkonda.artschools.data.data_source.user.entity.UserProfileDto
import shkonda.artschools.domain.model.user.UpdateProfileBody
import shkonda.artschools.domain.model.user.UserProfile

//fun UserProfileDto.toUserProfile(): UserProfile {
//    return UserProfile(
//        username = username,
////        firstName = firstName,
////        lastName = lastName,
//        profilePictureUrl = profilePictureUrl,
////        score = score,
////        biography = biography
//    )
//}

fun UserProfileDto.toUserProfile(): UserProfile {
    return UserProfile(
        username = username,
        bio = bio,
        profileImage = image,
        artCategory = artCategory,
        points = points.toIntOrNull() ?: -1
    )
}

//fun UpdatePasswordBody.toUpdatePasswordBodyDto(): UpdatePasswordBodyDto {
//    return UpdatePasswordBodyDto(
//        oldPassword = oldPassword,
//        newPassword = newPassword
//    )
//}

fun UpdateProfileBody.toUpdateProfileBodyDto(): UpdateProfileBodyDto {
    return UpdateProfileBodyDto(
        firstName = firstName,
        lastName = lastName,
        biography = biography
    )
}

//fun ArrayList<LeaderboardDto>.toLeaderboard(): ArrayList<Leaderboard> {
//    val leaderboard = arrayListOf<Leaderboard>()
//
//    forEach {
//        leaderboard.add(
//            Leaderboard(
//                id = it.id,
//                userName = it.userName,
//                userImage = it.userImage,
//                score = it.score
//            )
//        )
//    }
//
//    return leaderboard
//}
