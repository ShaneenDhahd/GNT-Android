package shaneen.dhahd.gnt_test.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import shaneen.dhahd.gnt_test.R
import shaneen.dhahd.gnt_test.databinding.FragmentLandingBinding
import shaneen.dhahd.gnt_test.ext.navigateTo
import shaneen.dhahd.gnt_test.ext.toast
import shaneen.dhahd.gnt_test.network.wrappers.ResponseWrapper
import shaneen.dhahd.gnt_test.viewModel.LoginViewModel

@AndroidEntryPoint
class LandingFragment : Fragment() {

    lateinit var binding: FragmentLandingBinding
    private val loginVM: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLandingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginVM.apply {
            refreshToken()
            loginObservable.observe(viewLifecycleOwner) {
                when(it){
                    is ResponseWrapper.Failure -> findNavController().navigateTo(R.id.loginFragment, true)
                    is ResponseWrapper.LocalFailure -> requireContext().toast("No Connection")
                    is ResponseWrapper.Success -> findNavController().navigateTo(R.id.navigation_form, true)
                    else -> {}
                }
            }
        }
    }

}