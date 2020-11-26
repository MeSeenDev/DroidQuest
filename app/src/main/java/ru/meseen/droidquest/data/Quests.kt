package ru.meseen.droidquest.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.meseen.droidquest.R

@Parcelize
data class Quests(
    val quest: String = " Default",
    val answer: Boolean = false,
    val comment: String = "default comment",
    val drawable: Int = R.drawable.ic_baseline_expand_more_24
) : Parcelable
