package org.wit.cowcalendar.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnimalModel(
    var id: Long = 0,
    var animalNumber: Int  = 0,
    var animalSex: Int = 0,
    var animalDob: String = "",
    var lastEventType: String ="",
    var lastCalveDate: String ="",
    var isPregnant: Boolean = false,
    var okToServe: Boolean = true,
    var treatmentRequired: Boolean = false
): Parcelable

