package shaneen.dhahd.gnt_test.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import shaneen.dhahd.gnt_test.R
import shaneen.dhahd.gnt_test.databinding.ActivityMainBinding
import shaneen.dhahd.gnt_test.viewModel.LoginViewModel
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val loginVM: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_graph) as? NavHostFragment
        navHostFragment?.also {
            val navController = navHostFragment.navController
            setupActionBarWithNavController(navController)
        }
    }

}