package org.wit.cowcalendar.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventModel(
  var id: Long = 0,
  var animalId: Int = 0,
  var eventType: String = "",
  var eventDate: String = "",
  var sire: String = "",
  var serveNo: Int = 0,
  var calveDate: String = "",
  var calveSex: Int = 0,
  var dryOffDate: String = ""
): Parcelable