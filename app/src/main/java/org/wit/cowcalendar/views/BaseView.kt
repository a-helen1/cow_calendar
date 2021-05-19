package org.wit.cowcalendar.views

import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import org.jetbrains.anko.AnkoLogger
import org.wit.cowcalendar.activities.AnimalEventView
import org.wit.cowcalendar.activities.AnimalView
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.views.addServe.AddServeView
import org.wit.cowcalendar.views.animalList.AnimalListView
import org.wit.cowcalendar.views.login.LoginView

enum class VIEW {
  ANIMAL, LIST, EVENT, ADD, LOGIN
}
open abstract class BaseView() :AppCompatActivity(), AnkoLogger {

  var basePresenter: BasePresenter? = null

  fun navigateTo( view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
    var intent = Intent(this, AnimalListView::class.java)
    when (view) {
      VIEW.ANIMAL -> intent = Intent(this, AnimalView::class.java)
      VIEW.LIST -> intent = Intent(this, AnimalListView::class.java)
      VIEW.EVENT -> intent = Intent(this, AnimalEventView::class.java)
      VIEW.ADD -> intent = Intent(this, AddServeView::class.java)
      VIEW.LOGIN -> intent = Intent(this, LoginView::class.java)
    }
    if (key != "") {
      intent.putExtra(key, value)
    }
    startActivityForResult(intent, code)
  }

  fun initPresenter(presenter: BasePresenter): BasePresenter {
    basePresenter = presenter
    return presenter
  }

  fun init (toolbar: Toolbar, upEnabled: Boolean) {
    toolbar.title =title
    setSupportActionBar(toolbar)
  }

  override fun onDestroy() {
    basePresenter?.onDestroy()
    super.onDestroy()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null) {
      basePresenter?.doActivityResult(requestCode, resultCode, data)
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
  }

  open fun showAnimal(animal: AnimalModel) {}
  open fun showAnimals(animals: List<AnimalModel>) {}
  open fun showProgress() {}
  open fun hideProgress() {}

}