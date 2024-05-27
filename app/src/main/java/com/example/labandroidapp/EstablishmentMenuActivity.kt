package com.example.labandroidapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


// Displaying menu of an establishment
class EstablishmentMenuActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_establishment_menu)


        //backBtn
        val backButton = findViewById<ImageButton>(R.id.BackBtn)
        backButton.setOnClickListener {
            val intent = Intent(this, EstablishmentChooserActivity::class.java)
            startActivity(intent)
        }

        // get textview and recyclerView from layout
        val establishmentName = findViewById<TextView>(R.id.establishmentName)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMenu)

        // get establishment name
        val establishment = intent.getParcelableExtra<Establishment>("establishment")
        establishmentName.text = establishment?.name

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = establishment?.let { MenuAdapter(it.menu) }

        // updating previous places list
        establishment?.let {
            updateVisitedPlaces(it)
        }
    }

    //update list of visited places for sharedPreferences (data that is stored between users)
    private fun updateVisitedPlaces(establishment: Establishment) {
        val sharedPreferences = getSharedPreferences("visited_places", Context.MODE_PRIVATE)
        val visitedPlaces = sharedPreferences.getStringSet("visited", mutableSetOf())?.toMutableList() ?: mutableListOf()

        visitedPlaces.remove(establishment.name) // remove duplicate
        visitedPlaces.add(0, establishment.name) // bring to 1st

        // save back to SharedPreferences
        sharedPreferences.edit().putStringSet("visited", visitedPlaces.toSet()).apply()
    }
}
