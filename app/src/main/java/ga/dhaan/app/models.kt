package ga.dhaan.app

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

infix fun Show.withScore(score: Double) = ShowWithScore(score, this)

@Parcelize
data class ShowWithScore(val score: Double, val show: Show) : Parcelable

@Parcelize
data class Show(
        val id: Int,
        val name: String,
        val language: String,
        val genres: ArrayList<String>,
        val status: String
) : Parcelable