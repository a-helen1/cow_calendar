package org.wit.cowcalendar.views.login

import android.os.Bundle
import android.os.PersistableBundle
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import org.wit.cowcalendar.R
import org.wit.cowcalendar.views.BaseView

class LoginView : BaseView() {

  lateinit var presenter: LoginPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    init(toolbar, false)

    presenter = initPresenter(LoginPresenter(this)) as LoginPresenter

    signUp.setOnClickListener {
      val email = email.text.toString()
      val password = password.toString()
      if (email == "" || password == "") {
        toast("Please enter an email and password")
      }
      else {
        presenter.doSignUp(email,password)
      }
    }

    logIn.setOnClickListener {
      val email = email.text.toString()
      val password = password.text.toString()
      if (email == "" || password == "") {
        toast ("Please enter an email or password")
      }
      else {
        presenter.doLogin(email, password)
      }
    }
  }
}