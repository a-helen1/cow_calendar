package org.wit.cowcalendar.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.wit.cowcalendar.R
import org.wit.cowcalendar.views.login.LoginView

class SplashScreenActivity: AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash_screen)

    iv_landscape.alpha = 0f
    iv_landscape.animate().setDuration(2000).alpha(1f).withEndAction() {
      val i = Intent(this, LoginView::class.java)
      startActivity(i)
      overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
      finish()
    }
  }
}