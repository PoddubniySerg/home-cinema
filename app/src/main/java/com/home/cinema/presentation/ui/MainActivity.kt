package com.home.cinema.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.home.cinema.R
import com.home.cinema.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNav
            .setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, _, arguments ->
            if (
                arguments?.getBoolean(resources.getString(R.string.bottom_navigation_is_hidden_nav_graph_argument)) == true
            ) {
                binding.bottomNav.visibility = View.GONE
            } else binding.bottomNav.visibility = View.VISIBLE
        }
    }
}