    package org.wit.cowcalendar.models

    import android.content.Context
    import com.google.gson.Gson
    import com.google.gson.GsonBuilder
    import com.google.gson.reflect.TypeToken
    import org.jetbrains.anko.AnkoLogger
    import org.wit.cowcalendar.helpers.exists
    import org.wit.cowcalendar.helpers.read
    import org.wit.cowcalendar.helpers.write
    import java.util.*
    import kotlin.collections.ArrayList

    val JSON_FILE = "animals.json"
    val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
    val listType = object  : TypeToken<java.util.ArrayList<AnimalModel>>() {}.type

    fun generateRandomId(): Long {
        return Random().nextLong()
    }

    class AnimalJSONStore : AnimalStore, AnkoLogger {
        val context : Context
        var animals = mutableListOf<AnimalModel>()

        constructor( context: Context) {
            this.context = context
            if (exists(context, JSON_FILE)) {
                deserialize()
            }
        }

        override fun findAll(): MutableList<AnimalModel> {
            animals.sortWith(compareBy { it.animalNumber.toInt() })
            return animals
        }

        fun findById(animalNo: Int): AnimalModel? {
            return animals.find { it.animalNumber == animalNo}

        }

        override fun create(animal: AnimalModel) {
            animal.id = generateRandomId()
            animals.add(animal)
            serialise()
        }

        override fun update(animal: AnimalModel) {
            val animalsList = findAll() as ArrayList<AnimalModel>
            var foundAnimal: AnimalModel? = animalsList.find { p -> p.id == animal.id }
            if (foundAnimal != null ) {
                foundAnimal.animalNumber = animal.animalNumber
                foundAnimal.animalSex = animal.animalSex
                foundAnimal.animalDob = animal.animalDob
                foundAnimal.lastEventType = animal.lastEventType
            }
            serialise()
        }

        override fun delete(animal: AnimalModel) {
            animals.remove(animal)
            serialise()
        }

        private fun serialise() {
            val jsonString = gsonBuilder.toJson(animals, listType)
            write(context, JSON_FILE, jsonString)
        }

        private fun deserialize() {
            val jsonString = read(context, JSON_FILE)
            animals = Gson().fromJson(jsonString, listType)
        }
    }

