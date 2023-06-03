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
import shaneen.dhahd.gnt_test.databinding.FragmentLoginBinding
import shaneen.dhahd.gnt_test.ext.navigateTo
import shaneen.dhahd.gnt_test.network.wrappers.ResponseWrapper
import shaneen.dhahd.gnt_test.viewModel.LoginViewModel

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginVM: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            loginBtn.setOnClickListener {
                loginVM.apply {
                    login(emailField.text.toString(), passwordField.text.toString())
                    loginObservable.observe(viewLifecycleOwner) {
                        when(it){
                            is ResponseWrapper.Success -> findNavController().navigateTo(R.id.navigation_forms, true)
                            else -> {}
                        }
                    }
                }
            }
        }
    }

}