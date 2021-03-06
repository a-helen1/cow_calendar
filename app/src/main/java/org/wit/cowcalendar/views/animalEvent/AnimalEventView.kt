package org.wit.cowcalendar.activities

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_animal.toolbarAdd
import kotlinx.android.synthetic.main.activity_animal_events.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.cowcalendar.R
import org.wit.cowcalendar.main.MainApp
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.models.EventModel
import org.wit.cowcalendar.views.animalEvent.AnimalEventPresenter
import org.wit.cowcalendar.views.animalEvent.EventAdapter
import org.wit.cowcalendar.views.animalEvent.EventListener
import java.text.DateFormat
import java.util.*

var animalId = 0

class AnimalEventView : AppCompatActivity(), AnkoLogger, EventListener {

  lateinit var presenter: AnimalEventPresenter

  var x = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_animal_events)

    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)

    presenter = AnimalEventPresenter(this)

    val eventTypeList = resources.getStringArray(R.array.Events)
    val spinner = findViewById<Spinner>(R.id.eventSpinner)

    if (spinner != null) {
      val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, eventTypeList)
      spinner.adapter = adapter

      spinner.onItemSelectedListener = object :
      AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
          x= p2
          if (x == 4) {
            btnAddEvent.setText(R.string.btn_add_dryoff)
          }else {
            btnAddEvent.setText(R.string.btn_add_event)
          }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
          TODO("Not yet implemented")
        }
      }
    }

    btnAddEvent.setOnClickListener() {
      if (eventDate.text.isEmpty()) {
        toast("Please Enter an Event Date")
      }else {
        when (x){

          0 -> toast("Please Select an Event")

          1 -> if(presenter.animal.lastEventType == "Dry Off" && presenter.animal.isPregnant) {
            presenter.doAddCalveEvent(
              eventDate.text.toString(),
              eventSpinner.selectedItem.toString()
            )
          }else {
            toast("Please add a dry off event or animal may not be pregnant")
          }

          2 -> if(presenter.animal.lastEventType == "Serve" || presenter.animal.lastEventType == "Calve") {
            presenter.doAddScanEvent(
              eventDate.text.toString(),
              eventSpinner.selectedItem.toString())
          } else {
            toast("Animal has not been served or has not recently calved"
            )
          }

          3 -> if(presenter.animal.isPregnant || !presenter.animal.okToServe) {
            toast("Animal is Pregnant or requires treatment")
          }else{
            presenter.doAddServeEvent(
              eventDate.text.toString(),
              eventSpinner.selectedItem.toString())
          }

          4 -> presenter.doAddDryOffEvent(eventDate.text.toString(),
            eventSpinner.selectedItem.toString())
        }
      }
    }
  }

  fun showEvents (animal: AnimalModel, events: MutableList<EventModel>) {
    animalNo.setText(animal.animalNumber.toString())
    animalDobEvent.setText (animal.animalDob)
    animalId = animal.animalNumber
    if (animal.animalSex == 1) {
      animalSexTxt.setText(R.string.sex_male)
    }else {
      animalSexTxt.setText(R.string.sex_female)
    }

    val eventLayoutManager = LinearLayoutManager(this)
    eventRecyclerView.layoutManager = eventLayoutManager
    eventRecyclerView.adapter = EventAdapter(events,this)
    eventRecyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onEventClick(event: EventModel) {
    presenter.doShowEvent(event)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_animal, menu)
    //if (presenter.edit) menu.getItem(0).setVisible(true)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item?.itemId) {
      R.id.item_delete -> {
        presenter.doDelete(presenter.animal)
        finish()
      }
      R.id.item_edit -> {
        presenter.edit(presenter.animal)
      }
      R.id.item_cancel -> {
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  fun showDatePickerDialog(v: View) {
    val newFragment = EventDatePickerFragment()
    newFragment.setAppObject(presenter.app)
    newFragment.show(supportFragmentManager, "datePicker")
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    presenter.getUpdatedAnimal()
    presenter.getEvents()
    showEvents(presenter.animal, presenter.animalEvents)
    super.onActivityResult(requestCode, resultCode, data)
  }
}

class EventDatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

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

    activity!!.findViewById<TextView>(R.id.eventDate).text = pickedDate
  }

  fun setAppObject(appMain: MainApp) {
    app = appMain
  }
}





