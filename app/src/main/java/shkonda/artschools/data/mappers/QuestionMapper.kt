package shkonda.artschools.data.mappers

import shkonda.artschools.data.data_source.question.entity.AnswerDto
import shkonda.artschools.data.data_source.question.entity.QuestionDto
import shkonda.artschools.data.data_source.question.entity.QuestionImageDto
import shkonda.artschools.data.data_source.question.entity.QuestionListDto
import shkonda.artschools.domain.model.question.Answer
import shkonda.artschools.domain.model.question.Question
import shkonda.artschools.domain.model.question.QuestionImage
import shkonda.artschools.domain.model.question.QuestionList

fun QuestionListDto.toQuestionList(): QuestionList {
    return QuestionList(
        questionList = this.questionList.map { it.toQuestion() }
    )
}

fun QuestionDto.toQuestion(): Question {
    return Question(
        question = this.question,
        questionValue = this.questionValue,
        questionImageList = this.questionImageList.map { it.toQuestionImage() },
        answerList = this.answerList.map { it.toAnswer() }
    )
}

fun QuestionImageDto.toQuestionImage(): QuestionImage {
    return QuestionImage(
        questionImage = this.questionImage
    )
}

fun AnswerDto.toAnswer(): Answer {
    return Answer(
        answer = this.answer,
        isCorrect = this.isCorrect
    )
}