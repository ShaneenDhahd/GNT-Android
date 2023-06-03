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
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_graph) as? NavHostFragment
        navHostFragment?.also {
            val navController = navHostFragment.navController

            // Set up ActionBar with Navigation
            setupActionBarWithNavController(navController)
        }

            // Handle the case when the retrieved fragment is null or not a NavHostFragment
//        binding.fragmentsContainer
//
//        navigateToLogin()
//        loginVM.apply {
//            refreshToken()
//            loginObservable.observe(this@MainActivity) {
//                binding.apply {
//                    welcomeMsg.visibility = View.GONE
//                    progressBar.visibility = View.GONE
//                }
//                when(it){
//                    is ResponseWrapper.Failure -> navigateToLogin()
//                    is ResponseWrapper.LocalFailure -> this@MainActivity.toast("No Connection")
//                    is ResponseWrapper.Success -> navigateToForm()
//                    else -> {}
//                }
//            }
//        }
    }

//    private fun navigateToLogin(){
//        val loginFragment = LoginFragment()
//        supportFragmentManager.beginTransaction()
//            .replace(binding.fragmentsContainer.id, loginFragment)
//            .commit()
//    }
//     private fun navigateToForm(){
//            val formFragment = FormFragment()
//            supportFragmentManager.beginTransaction()
//                .replace(binding.fragmentsContainer.id, formFragment)
//                .commit()
//        }

}