package org.wit.cowcalendar.views.addScan

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_calve.*
import kotlinx.android.synthetic.main.activity_add_scan.*
import kotlinx.android.synthetic.main.activity_add_scan.addEventDate
import kotlinx.android.synthetic.main.activity_add_scan.animalDobEvent
import kotlinx.android.synthetic.main.activity_add_scan.animalNo
import kotlinx.android.synthetic.main.activity_add_scan.animalSexTxt1
import org.jetbrains.anko.AnkoLogger
import org.wit.cowcalendar.R
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.models.EventModel

class AddScanView: AppCompatActivity(), AnkoLogger {

    lateinit var presenter: AddScanPresenter
    var animal = AnimalModel()
    var event = EventModel()
    var eventType = ""
    var okToServe = false
    var treatmentRequired = false
    var isPregnant = false


    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_add_scan)

      presenter = AddScanPresenter(this)

      val radioGroup1 = findViewById<RadioGroup>(R.id.radioScanType) as RadioGroup
      radioGroup1.setOnCheckedChangeListener { group, ID ->
        when (ID) {
          R.id.radioButtonPreServe -> {
            radioScanPreOutcome.visibility = View.VISIBLE
            radioScanPostOutcome.visibility = View.INVISIBLE
            eventType = "Pre Serve Scan"
          }
          R.id.radioButtonPostServ -> {
            radioScanPostOutcome.visibility = View.VISIBLE
            radioScanPreOutcome.visibility = View.INVISIBLE
            eventType = "Post Serve Scan"
          }
        }
      }

      val radioGroup2 = findViewById<RadioGroup>(R.id.radioScanPreOutcome) as RadioGroup
      radioGroup2.setOnCheckedChangeListener { group, ID ->
        when (ID) {
          R.id.radioButtonOkToServ -> {
            okToServe = true
            treatmentRequired = false
          }
          R.id.radioButtonTreatment -> {
            treatmentRequired = true
            okToServe = false
          }
        }
      }

      val radioGroup3 = findViewById<RadioGroup>(R.id.radioScanPostOutcome) as RadioGroup
      radioGroup3.setOnCheckedChangeListener { group, ID ->
        when (ID) {
          R.id.radioButtonPregnant -> {
            isPregnant = true
          }
          R.id.radioButtonNotPrengnant -> {
            isPregnant = false
          }
        }
      }

      btnAddScan.setOnClickListener() {
        presenter.doAddScan(eventType, okToServe, treatmentRequired, isPregnant)
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




