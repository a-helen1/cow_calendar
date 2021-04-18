import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_animal.view.*
import org.wit.cowcalendar.R
import org.wit.cowcalendar.models.AnimalModel

class AnimalAdapter constructor(private var animals: List<AnimalModel>):
    RecyclerView.Adapter<AnimalAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_animal,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, Position: Int) {
        val animal = animals[holder.adapterPosition]
        holder.bind(animal)
    }

    override fun getItemCount(): Int = animals.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(animal: AnimalModel){
            itemView.animalNumber.text = animal.animalNumber
            itemView.animalSex.text = animal.animalSex
        }
    }
}