package org.wit.cowcalendar.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_animal.view.*
import kotlinx.android.synthetic.main.card_event.view.*
import org.wit.cowcalendar.R
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.models.EventModel
import java.util.*

interface EventListener{
  fun onEventClick(event: EventModel)
}

class EventAdapter constructor(
  private var events: List<EventModel>,
  private val listener: EventListener
): RecyclerView.Adapter<EventAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(
      LayoutInflater.from(parent?.context).inflate(
        R.layout.card_event,
        parent,
        false
      )
    )
  }

  override fun onBindViewHolder(holder: MainHolder, Position: Int) {
    val event = events[holder.adapterPosition]
    holder.bind(event, listener)
  }

  override fun getItemCount(): Int = events.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(event: EventModel, listener: EventListener){
      itemView.eventType.text = event.eventType
      itemView.eventDate.text = event.eventDate
      itemView.eventSire.text = event.sire
      if (event.isPregnant) {
        itemView.eventPregnantState.text ="Pregnant"
      } else {
        itemView.eventPregnantState.text =" Not Pregnant"
      }
      itemView.setOnClickListener { listener.onEventClick(event)}
    }
  }
}