package com.example.yogaapp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.lifecycle.ViewModelProvider
import com.example.yogaapp.databinding.MainActivityYogaBinding
import com.example.yogaapp.viewmodel.YogaViewModel
import com.example.yogaapp.viewmodel.YogaViewModelFactory

class MainYogaActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var viewModel: YogaViewModel
    private lateinit var binding: MainActivityYogaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val app = application as YogaApplication
        viewModel = ViewModelProvider(this, YogaViewModelFactory(app.repository))[YogaViewModel::class.java]

        installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.categoriesLoading
            }
        }

        super.onCreate(savedInstanceState)
        binding = MainActivityYogaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = ""
        binding.toolbar.setNavigationIconTint(Color.parseColor("#C47CCC"))

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}