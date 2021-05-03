package org.wit.cowcalendar.activities

import android.app.DatePickerDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.activity_animal.*
import kotlinx.android.synthetic.main.card_animal.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.cowcalendar.R
import org.wit.cowcalendar.main.MainApp
import org.wit.cowcalendar.models.AnimalModel
import java.text.DateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneId.*
import java.time.format.DateTimeFormatter
import java.util.*

class AnimalActivity : AppCompatActivity(), AnkoLogger {

  var animal = AnimalModel()
  lateinit var app: MainApp
  var edit = false;

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_animal)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    info("Animal Activity started..")
    app = application as MainApp


    if (intent.hasExtra("animal_edit")) {
      edit = true
      animal = intent.extras?.getParcelable<AnimalModel>("animal_edit")!!
      cowNo.setText(animal.animalNumber)
      if (animal.animalSex == 1) {
        radioButtonMale.isChecked = true
      } else {
        radioButtonFemale.isChecked = true
      }
      btnAddCow.setText(R.string.save_animal)
    }

    /*
    btnSetDate.setOnClickListener {
      val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ _, mYear, mMonth, mDay ->
        animalDob.setText(""+ mDay +"/"+ mMonth +"/"+ mYear )
      }, year, month, day)
      dpd.show()
    }

     */


    val radioGroup = findViewById<RadioGroup>(R.id.radioGroup) as RadioGroup
    radioGroup.setOnCheckedChangeListener { group, ID ->
      when (ID) {
        R.id.radioButtonMale -> {
          animal.animalSex = 1
        }
        R.id.radioButtonFemale -> {
          animal.animalSex = 2
        }
      }
    }

    btnAddCow.setOnClickListener() {
      animal.animalNumber = cowNo.text.toString()
      if (animal.animalNumber.isEmpty()) {
        toast("Please enter a cow number")
      } else {
        if (edit) {
          app.animals.update(animal.copy())
        } else {
          app.animals.create(animal.copy())
        }
      }
      info("add button pressed: ${animal}")
      setResult(AppCompatActivity.RESULT_OK)
      finish()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_animal, menu)
    if (edit && menu != null) menu.getItem(0).setVisible(true)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item?.itemId) {
      R.id.item_delete -> {
        app.animals.delete(animal)
        finish()
      }
      R.id.item_cancel -> {
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  fun showDatePickerDialog(v: View) {
    val newFragment = DatePickerFragment()
    newFragment.show(supportFragmentManager, "datePicker")
  }

}

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    // Use the current date as the default date in the picker
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    // Create a new instance of DatePickerDialog and return it
    return DatePickerDialog(activity!!, this, year, month, day)
  }

  override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
    Log.d("Picked Date", "$year-$month-$day")
    val l = LocalDate.parse("$day-$month-$year", DateTimeFormatter.ofPattern("dd-MM-yyyy"))


    var unix = l.atStartOfDay(ZoneId.systemDefault()).toInstant().epochSecond
    Log.d("Times-tamp", "$unix")

  }
}

