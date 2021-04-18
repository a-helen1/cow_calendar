package org.wit.cowcalendar.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnimalModel(
    var animalNumber: String  = "",
    var animalSex: String = ""
): Parcelable