package com.example.labandroidapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EstablishmentChooserActivity : ComponentActivity() {
    private lateinit var previousAdapter: EstablishmentAdapter
    private lateinit var nearbyAdapter: EstablishmentAdapter
    private var visitedPlaces: MutableList<Establishment> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_establishment_chooser)

        // recyclerView for previous places, horizontal layout
        val previousRecyclerView = findViewById<RecyclerView>(R.id.recyclerViewPrevious)
        previousRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        previousAdapter = EstablishmentAdapter(visitedPlaces)
        previousRecyclerView.adapter = previousAdapter

        // recyclerView for close to you, vertical layout
        val nearbyRecyclerView = findViewById<RecyclerView>(R.id.recyclerViewNearby)
        nearbyRecyclerView.layoutManager = LinearLayoutManager(this)
        nearbyAdapter = EstablishmentAdapter(listOf())
        nearbyRecyclerView.adapter = nearbyAdapter

        // get data and update both recyclerViews
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@EstablishmentChooserActivity)
            val establishments = withContext(Dispatchers.IO) {
                db.establishmentDao().getAll()
            }
            nearbyAdapter = EstablishmentAdapter(establishments)
            nearbyRecyclerView.adapter = nearbyAdapter

            loadVisitedPlaces(establishments)
        }

        // addEstablishmentBtn
        val addButton = findViewById<Button>(R.id.addEstablishmentBtn)
        addButton.setOnClickListener {
            val intent = Intent(this, AddEstablishmentActivity::class.java)
            startActivity(intent)
        }

        // logoutBtn
        val logoutButton = findViewById<Button>(R.id.logoutBtn)
        logoutButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun loadVisitedPlaces(establishments: List<Establishment>) {
        val sharedPreferences = getSharedPreferences("visited_places", Context.MODE_PRIVATE)
        val visitedPlaceNames = sharedPreferences.getStringSet("visited", mutableSetOf())?.toList() ?: listOf()

        visitedPlaces.clear()
        visitedPlaces.addAll(visitedPlaceNames.mapNotNull { name ->
            establishments.find { it.name == name }
        })

        previousAdapter.notifyDataSetChanged()
    }
}
