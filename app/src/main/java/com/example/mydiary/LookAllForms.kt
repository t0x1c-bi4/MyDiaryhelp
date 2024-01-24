package com.example.mydiary

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.ArrayAdapter
import com.example.mydiary.DataBaseHelper
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class LookAllForms : AppCompatActivity() {
    companion object {
        const val PROFILE_DETAILS_REQUEST_CODE = 1
    }
    private lateinit var dbHelper: DataBaseHelper
    private lateinit var listView: ListView
    private lateinit var profileAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.f_look_all_forms)

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            val intent = Intent(this, FormsOfFriends::class.java)
            startActivity(intent)
        }

        val buttonSortAZ = findViewById<Button>(R.id.buttonSortAZ)
        buttonSortAZ.setOnClickListener {
            sortByABC()
        }

        val buttonSortZA = findViewById<Button>(R.id.buttonSortZA)
        buttonSortZA.setOnClickListener {
            sortByZYX()
        }

        dbHelper = DataBaseHelper(this)
        listView = findViewById(R.id.listViewProfiles)

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedProfile = dbHelper.getAllProfiles()[position]
            val intent = Intent(this, ProfileDetails::class.java)
            intent.putExtra("profile_id", selectedProfile.id.toString()) // Передаем идентификатор профиля в новую активность
            startActivityForResult(intent, PROFILE_DETAILS_REQUEST_CODE)
        }
        updateListView()
        showProfiles()
    }
    private fun sortByABC() {
        val profileList = dbHelper.getAllProfiles().sortedBy { it.name }
        updateListViewWithProfiles(profileList)
    }

    private fun sortByZYX() {
        val profileList = dbHelper.getAllProfiles().sortedByDescending { it.name }
        updateListViewWithProfiles(profileList)
    }

    private fun updateListViewWithProfiles(profileList: List<ProfileData>) {
        val profileNames = profileList.map { it.name }
        profileAdapter.clear()
        profileAdapter.addAll(profileNames)
        profileAdapter.notifyDataSetChanged()
    }

    private fun updateListView() {
        val profiles = dbHelper.getAllProfileNames()
        profileAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, profiles)
        listView.adapter = profileAdapter
    }

    private fun showProfiles() {
        val profileList = dbHelper.getAllProfiles()
        val profileNames = profileList.map { it.name }

        profileAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, profileNames)
        listView.adapter = profileAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PROFILE_DETAILS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val profileDeleted = data?.getBooleanExtra("profileDeleted", false) ?: false
            if (profileDeleted) {
                // Обновите список профилей
                updateListView()
                showProfiles()
            }
        }
    }
}