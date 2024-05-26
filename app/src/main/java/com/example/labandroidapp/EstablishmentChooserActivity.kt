package com.example.labandroidapp

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_establishment_chooser)

        val addEstablishmentButton = findViewById<Button>(R.id.addEstablishmentBtn)
        addEstablishmentButton.setOnClickListener {
            val intent = Intent(this, AddEstablishmentActivity::class.java)
            startActivity(intent)
        }

        val previousRecyclerView = findViewById<RecyclerView>(R.id.recyclerViewPrevious)
        previousRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        previousAdapter = EstablishmentAdapter(listOf())
        previousRecyclerView.adapter = previousAdapter

        val nearbyRecyclerView = findViewById<RecyclerView>(R.id.recyclerViewNearby)
        nearbyRecyclerView.layoutManager = LinearLayoutManager(this)
        nearbyAdapter = EstablishmentAdapter(listOf())
        nearbyRecyclerView.adapter = nearbyAdapter

        // Fetch data from the database
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@EstablishmentChooserActivity)
            val establishments = withContext(Dispatchers.IO) {
                db.establishmentDao().getAll()
            }
            previousAdapter = EstablishmentAdapter(establishments)
            nearbyAdapter = EstablishmentAdapter(establishments)
            previousRecyclerView.adapter = previousAdapter
            nearbyRecyclerView.adapter = nearbyAdapter
        }
    }
}
