package com.example.labandroidapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddEstablishmentActivity : ComponentActivity() {
    private lateinit var menuItemsContainer: LinearLayout
    private val menuItems = mutableListOf<MenuItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_establishment)

        //backBtn, navigate to home
        val backButton = findViewById<ImageButton>(R.id.BackBtn)
        backButton.setOnClickListener {
            val intent = Intent(this, EstablishmentChooserActivity::class.java)
            startActivity(intent)
        }

        //initializing container for menu items dynamically
        menuItemsContainer = findViewById(R.id.menuItemsContainer)


        val nameEditText = findViewById<EditText>(R.id.editTextEstablishmentName)
        val addressEditText = findViewById<EditText>(R.id.editTextEstablishmentAddress)
        val typeEditText = findViewById<EditText>(R.id.editTextEstablishmentType)
        val addButton = findViewById<Button>(R.id.AddEstablishmentBtn)
        val addMenuItemButton = findViewById<Button>(R.id.AddMenuItemBtn)

        //dynamic menu item Btn to add listener
        addMenuItemButton.setOnClickListener {
            addMenuItemView()
        }

        // addEstablishmentBtn
        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val address = addressEditText.text.toString()
            val type = typeEditText.text.toString()

            // checking field input
            if (name.isNotEmpty() && address.isNotEmpty() && type.isNotEmpty()) {
                updateMenuItems()  // Make sure the menuItems list is updated
                val establishment = Establishment(name = name, address = address, type = type, menu = menuItems)

                //save to DB
                lifecycleScope.launch(Dispatchers.IO) {
                    val db = AppDatabase.getDatabase(this@AddEstablishmentActivity)
                    db.establishmentDao().insert(establishment)
                    //Success message
                    runOnUiThread {
                        Toast.makeText(this@AddEstablishmentActivity, "Establishment added!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            } else {
                // Fail message
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //dynamic menu item Btn to add
    private fun addMenuItemView() {
        val inflater = LayoutInflater.from(this)
        val menuItemView = inflater.inflate(R.layout.item_menu, menuItemsContainer, false)
        menuItemsContainer.addView(menuItemView)
    }

    // update menu items data from UI
    private fun updateMenuItems() {
        menuItems.clear()
        for (i in 0 until menuItemsContainer.childCount) {
            val menuItemView = menuItemsContainer.getChildAt(i)
            val nameEditText = menuItemView.findViewById<EditText>(R.id.editTextMenuItemName)
            val priceEditText = menuItemView.findViewById<EditText>(R.id.editTextMenuItemPrice)
            val name = nameEditText.text.toString()
            val price = priceEditText.text.toString()
            if (name.isNotEmpty() && price.isNotEmpty()) {
                menuItems.add(MenuItem(name = name, price = price))
            }
        }
    }
}
