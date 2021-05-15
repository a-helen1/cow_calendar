package org.wit.cowcalendar.activities

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Selection.setSelection
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_animal.*
import kotlinx.android.synthetic.main.activity_animal.toolbarAdd
import kotlinx.android.synthetic.main.activity_animal_events.*
import kotlinx.android.synthetic.main.activity_animal_list.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.wit.cowcalendar.R
import org.wit.cowcalendar.main.MainApp
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.models.EventModel
import java.text.DateFormat
import java.util.*

var animalId = 0

class AnimalEventActivity : AppCompatActivity(), AnkoLogger, EventListener {

  lateinit var app: MainApp
  var animal = AnimalModel()
  var event =EventModel()

  var x = 0
  //var animalId = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_animal_events)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    app = application as MainApp
    val eventTypeList = resources.getStringArray(R.array.Events)

    animal = intent.extras?.getParcelable<AnimalModel>("animal_event")!!
    animalNo.text = animal.animalNumber
    animalDobEvent.text = animal.animalDob
    animalId = animal.animalNumber.toInt()
    if (animal.animalSex == 1) {
      animalSexTxt.setText(R.string.sex_male)
    }else {
      animalSexTxt.setText(R.string.sex_female)
    }

    val eventLayoutManager = LinearLayoutManager(this)
    eventRecyclerView.layoutManager = eventLayoutManager
    loadEvents(animalId)

    val spinner = findViewById<Spinner>(R.id.eventSpinner)
    if (spinner != null) {
      val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, eventTypeList)
      spinner.adapter = adapter

      spinner.onItemSelectedListener = object :
      AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
          x= p2
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
          TODO("Not yet implemented")
        }
      }
    }

    btnAddEvent.setOnClickListener() {
      event.eventDate = eventDate.text.toString()
      event.eventType = eventSpinner.selectedItem.toString()
      event.animalId = animal.animalNumber.toInt()
      when (x){
        3-> startActivityForResult(intentFor<AddServeActivity>().putExtra("event_info", event ).putExtra("animal", animal), 0)
      }

    }
  }

  fun showDatePickerDialog(v: View) {
    val newFragment = EventDatePickerFragment()
    newFragment.setAppObject(app)
    newFragment.show(supportFragmentManager, "datePicker")
  }

  override fun onEventClick(event: EventModel) {}

  private fun loadEvents(id: Int) {
    //showEvents(app.events.findAll())
    var allEvents = app.events.findAll()
    var animalEvents = mutableListOf<EventModel>()
    for (item in allEvents) {
      if (item.animalId == id) {
        animalEvents.add(item)
      }
    }
    showEvents(animalEvents)


  }

  fun showEvents (events: List<EventModel>) {
    eventRecyclerView.adapter = EventAdapter(events,this)
    eventRecyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    loadEvents(animalId)
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





