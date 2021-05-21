package org.wit.cowcalendar.activities

import android.app.DatePickerDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_animal.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.cowcalendar.R
import org.wit.cowcalendar.main.MainApp
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.views.BaseView
import org.wit.cowcalendar.views.animal.AnimalPresenter
import java.text.DateFormat
import java.util.*

class AnimalView : BaseView(), AnkoLogger {

  lateinit var presenter: AnimalPresenter
  var animal = AnimalModel()
  var animalSex = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_animal)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    info("Animal Activity started..")

    //presenter = AnimalPresenter(this)
    presenter = initPresenter(AnimalPresenter(this)) as AnimalPresenter

    val radioGroup = findViewById<RadioGroup>(R.id.radioGroup) as RadioGroup
    radioGroup.setOnCheckedChangeListener { group, ID ->
      when (ID) {
        R.id.radioButtonMale -> {
          animalSex = 1
        }
        R.id.radioButtonFemale -> {
          animalSex = 2
        }
      }
    }

    btnAddCow.setOnClickListener() {
      if (cowNo.text.toString().isEmpty()) {
        toast("Please enter an animal number")
      } else {
        presenter.doAddorSave(cowNo.text.toString(), animalSex ,animalDob.text.toString())
      }
      setResult(AppCompatActivity.RESULT_OK)
    }
  }

  override fun showAnimal(animal: AnimalModel) {
    cowNo.setText(animal.animalNumber.toString())
    animalDob.setText(animal.animalDob)
    if (animal.animalSex == 1) {
      radioButtonMale.isChecked = true
    } else {
      radioButtonFemale.isChecked = true
    }
    btnAddCow.setText(R.string.save_animal)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_animal, menu)
    if (presenter.edit) menu.getItem(0).setVisible(true)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item?.itemId) {
      R.id.item_delete -> {
        presenter.doDelete()
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
    newFragment.setAppObject(presenter.app)
    newFragment.show(supportFragmentManager, "datePicker")
  }
}

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

  lateinit var app: MainApp

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

    val c = Calendar.getInstance()
    c.set(Calendar.YEAR, year)
    c.set(Calendar.MONTH, month)
    c.set(Calendar.DAY_OF_MONTH, day)

    val pickedDate = DateFormat.getDateInstance(DateFormat.SHORT).format(c.time) // convert to a string

    //Log.d("Picked Date", pickedDate)
    //val l = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(pickedDate)
    //Log.d("parsed date", "$l")

    activity!!.findViewById<TextView>(R.id.animalDob).text = pickedDate
  }

  fun setAppObject(appMain: MainApp) {
    app = appMain
  }
}

