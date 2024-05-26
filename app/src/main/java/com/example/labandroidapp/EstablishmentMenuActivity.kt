package com.example.labandroidapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EstablishmentMenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_establishment_menu)

        val backButton = findViewById<ImageButton>(R.id.BackBtn)
        backButton.setOnClickListener {
            val intent = Intent(this, EstablishmentChooserActivity::class.java)
            startActivity(intent)
        }

        val establishmentName = findViewById<TextView>(R.id.establishmentName)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMenu)

        val establishment = intent.getParcelableExtra<Establishment>("establishment")
        establishmentName.text = establishment?.name

        recyclerView.layoutManager = LinearLayoutManager(this)
        if (establishment != null) {
            recyclerView.adapter = MenuAdapter(establishment.menu)
        }
    }
}
