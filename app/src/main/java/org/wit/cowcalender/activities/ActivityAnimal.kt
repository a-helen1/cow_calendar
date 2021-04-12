package org.wit.cowcalender.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_animal.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.internals.AnkoInternals.createAnkoContext
import org.jetbrains.anko.toast
import org.wit.cowcalender.R
import org.wit.cowcalender.models.AnimalModel

class ActivityAnimal : AppCompatActivity(), AnkoLogger {

  var animal = AnimalModel()
  val animals = ArrayList<AnimalModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_animal)

    val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
    radioGroup?.setOnCheckedChangeListener { group, checkedId ->
      var animalSex = if (R.id.radioButtonMale == checkedId) "male" else "female"
      animal.animalSex = animalSex
    }

    btnAddCow.setOnClickListener() {

      animal.animalNumber = cowNo.text.toString()

            if (animal.animalNumber.isNotEmpty()) {
        animals.add(animal.copy())
        info ("add button pressed: ${animal.animalNumber}")
        for (i in animals.indices) {
          info("Animal[$i]:${this.animals[i]}")
        }
      }
      else {
        toast("Please enter a cow number")
      }

    }

  }

}