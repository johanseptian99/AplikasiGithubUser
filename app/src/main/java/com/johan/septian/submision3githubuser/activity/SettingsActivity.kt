package com.johan.septian.submision3githubuser.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.johan.septian.submision3githubuser.R
import com.johan.septian.submision3githubuser.fragment.MyPreferenceFragment

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.title = "Setting"
        supportFragmentManager.beginTransaction().add(R.id.setting_holder, MyPreferenceFragment()).commit()
    }


}