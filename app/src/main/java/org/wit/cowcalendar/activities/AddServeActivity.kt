package org.wit.cowcalendar.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.ActionMode
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_serve.*
import kotlinx.android.synthetic.main.activity_add_serve.animalDobEvent
import kotlinx.android.synthetic.main.activity_add_serve.animalNo
import kotlinx.android.synthetic.main.activity_add_serve.animalSexTxt
import kotlinx.android.synthetic.main.activity_animal.*
import kotlinx.android.synthetic.main.activity_animal.toolbarAdd
import kotlinx.android.synthetic.main.activity_animal_events.*
import org.jetbrains.anko.AnkoLogger
import org.wit.cowcalendar.R
import org.wit.cowcalendar.main.MainApp
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.models.EventModel

class AddServeActivity : AppCompatActivity(), AnkoLogger {

  var animal = AnimalModel()
  var event = EventModel()
  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_serve)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    app = application as MainApp

    event = intent.extras?.getParcelable<EventModel>("event_info")!!
    animal = intent.extras?.getParcelable<AnimalModel>("animal")!!
    animalNo.text = animal.animalNumber
    animalDobEvent.text = animal.animalDob
    addEventDate.text = event.eventDate
    if (animal.animalSex == 1) {
      animalSexTxt.setText(R.string.sex_male)
    }else {
      animalSexTxt.setText(R.string.sex_female)
    }

    btnAddServe.setOnClickListener() {
      event.sire = sire.text.toString()
      event.serveNo += 1
      app.events.create(event.copy())
      Log.d("Event", "$event"  )
    }
  }
 }



