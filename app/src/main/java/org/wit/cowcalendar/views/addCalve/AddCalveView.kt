package org.wit.cowcalendar.views.addCalve

import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_calve.*
import kotlinx.android.synthetic.main.activity_add_calve.addEventDate
import kotlinx.android.synthetic.main.activity_add_calve.animalDobEvent
import kotlinx.android.synthetic.main.activity_add_calve.animalNo
import kotlinx.android.synthetic.main.activity_add_serve.*
import org.jetbrains.anko.AnkoLogger
import org.wit.cowcalendar.R
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.models.EventModel

class AddCalveView: AppCompatActivity(), AnkoLogger {

  lateinit var presenter: AddCalvePresenter
  var animal = AnimalModel()
  var event = EventModel()
  var calveSex = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_calve)

    presenter = AddCalvePresenter(this)

    val radioGroup = findViewById<RadioGroup>(R.id.radioGroup) as RadioGroup
    radioGroup.setOnCheckedChangeListener { group, ID ->
      when (ID) {
        R.id.radioButtonMale -> {
          calveSex = 1
        }
        R.id.radioButtonFemale -> {
          calveSex = 2
        }
      }
    }

    btnAddCalve.setOnClickListener() {
      presenter.doAddCalve(calveSex)
    }
  }

  fun showEvents(animal: AnimalModel, event: EventModel) {
    animalNo.text = animal.animalNumber.toString()
    animalDobEvent.text = animal.animalDob
    addEventDate.text = event.eventDate
    if (animal.animalSex == 1) {
      animalSexTxt1.setText(R.string.sex_male)
    }else {
      animalSexTxt1.setText(R.string.sex_female)
    }
  }


}