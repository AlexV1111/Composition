package com.example.composition.presentation

import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.composition.R
import com.example.composition.domain.entity.GameResult

interface OnOptionClickListener{
    fun onOptionClick(option: Int)
}

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.resources.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("scoreAnswers")
fun bindScoreAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.resources.getString(R.string.score_answers),
        count
    )
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.resources.getString(R.string.required_percentage),
        count
    )
}

@BindingAdapter("scorePercentage")
fun bindScorePercentage(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.resources.getString(R.string.score_percentage),
        if (gameResult.countOfQuestions == 0) 0
        else ((gameResult.countOfRightAnswers / gameResult.countOfQuestions.toDouble())
                * 100).toInt()
    )
}

@BindingAdapter("emojiResult")
fun bindEmojiResult(imageview: ImageView, isWinner: Boolean) {
    val rightDrawable = if (isWinner) {
        ContextCompat.getDrawable(imageview.context, R.drawable.ic_smile)
    } else {
        ContextCompat.getDrawable(imageview.context, R.drawable.ic_sad)
    }
    imageview.setImageDrawable(rightDrawable)
}

@BindingAdapter("enoughCountOfRightAnswers")
fun bindEnoughCountOfRightAnswers(textView: TextView, isEnough: Boolean) {
    val colorResId = if (isEnough) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    textView.setTextColor(ContextCompat.getColor(textView.context, colorResId))
}

@BindingAdapter("enoughCountOfRightAnswers")
fun bindEnoughPercentOfRightAnswers(progressBar: ProgressBar, isEnough: Boolean) {
    val colorResId = if (isEnough) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    progressBar.progressTintList = ColorStateList.valueOf(
        ContextCompat.getColor(progressBar.context, colorResId)
    )
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, clickListener: OnOptionClickListener){
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}