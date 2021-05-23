package org.wit.cowcalendar.views.login

import org.wit.cowcalendar.views.BasePresenter
import org.wit.cowcalendar.views.BaseView
import org.wit.cowcalendar.views.VIEW

class LoginPresenter(view:BaseView) : BasePresenter(view)  {

  fun doLogin(email: String, password: String) {
    view?.navigateTo(VIEW.LIST)
  }

  fun doSignUp(email: String, password: String) {
    view?.navigateTo(VIEW.LIST)
  }
}