package org.wit.cowcalendar.views.event

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_event_view.*
import org.jetbrains.anko.AnkoLogger
import org.wit.cowcalendar.R
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.models.EventModel
import org.wit.cowcalendar.views.BaseView
import org.wit.cowcalendar.views.animalEvent.AnimalEventPresenter

class EventView: BaseView(), AnkoLogger{

  lateinit var presenter: EventPresenter
  var event = EventModel()
  var animal = AnimalModel()


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_event_view)

    toolbarEvent.title = title
    setSupportActionBar(toolbarEvent)

    presenter = initPresenter(EventPresenter(this)) as EventPresenter
  }

  fun showEvent(event: EventModel, animal: AnimalModel) {
    cowNo.text = event.animalId.toString()
    typeOfEvent.text = event.eventType
    dateOfEvent.text = event.eventDate
    sireEvent.text = event.sire
    serveNoEvent.text = event.serveNo.toString()
    dateCalve.text = event.calveDate

    if (event.calveSex == 1) {
      sexOfCalf.text = "Male"
    } else if (event.calveSex ==2) {
      sexOfCalf.text = "Female"
    } else {
      sexOfCalf.text = ""
    }

    dateDryOff.text = event.dryOffDate

    if (animal.okToServe) {
      isOkToServe.text = "Ok To Serve"
    } else {
      isOkToServe.text = "Not ok To Serve"
    }

    if (animal.treatmentRequired) {
      requiresTreatment.text = "Treatment Required"
    } else {
      requiresTreatment.text = "No Treatment Required"
    }

    if (animal.isPregnant) {
      pregnantState.text = "Pregnant"
    } else {
      pregnantState.text = "Not Pregnant"
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_event, menu)
    //if (presenter.edit) menu.getItem(0).setVisible(true)
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

}