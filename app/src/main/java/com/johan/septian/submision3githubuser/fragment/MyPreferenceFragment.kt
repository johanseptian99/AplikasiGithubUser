package com.johan.septian.submision3githubuser.fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.johan.septian.submision3githubuser.R
import com.johan.septian.submision3githubuser.reminder.Reminder

class MyPreferenceFragment: PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var REMINDER: String
    private lateinit var isReminderPreference: SwitchPreference

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.setting_preferences)
        init()
        setSummaries()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        isReminderPreference.isChecked = sh.getBoolean(REMINDER, false)
    }

    private fun init() {
        REMINDER = resources.getString(R.string.key_reminder)
        isReminderPreference = findPreference<SwitchPreference>(REMINDER) as SwitchPreference
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        val reminder = Reminder()
        if (key == REMINDER) {
            isReminderPreference.isChecked = sharedPreferences.getBoolean(REMINDER, false)
                if (isReminderPreference.isChecked) {
                    reminder.setRepeatingAlarm(requireActivity(), Reminder.TYPE_REPEATING, getString(R.string.reminder_message))
                } else {
                    reminder.cancelAlarm(requireActivity(), Reminder.TYPE_REPEATING)
                }
            }
        }

}
