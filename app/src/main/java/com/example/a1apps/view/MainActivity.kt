package com.example.a1apps.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.a1apps.R
import com.example.a1apps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.fragmentContainerView2)
        binding.bottomMenu.setupWithNavController(navController)

        // hosted bottom nav bar on main screen and fragment too so hiding bottom nav during the splash screen
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> binding.bottomMenu.visibility = View.GONE
                else -> binding.bottomMenu.visibility = View.VISIBLE
            }
        }
    }
}
