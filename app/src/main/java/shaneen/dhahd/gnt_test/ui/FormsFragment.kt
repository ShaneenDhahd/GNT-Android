package shaneen.dhahd.gnt_test.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import shaneen.dhahd.gnt_test.R
import shaneen.dhahd.gnt_test.databinding.FragmentFormsBinding
import shaneen.dhahd.gnt_test.di.PreferencesManager
import shaneen.dhahd.gnt_test.ext.init
import shaneen.dhahd.gnt_test.ext.navigateTo
import shaneen.dhahd.gnt_test.ext.toast
import shaneen.dhahd.gnt_test.network.wrappers.ResponseWrapper
import shaneen.dhahd.gnt_test.ui.adapter.FormsAdapter
import shaneen.dhahd.gnt_test.viewModel.FormViewModel
import shaneen.dhahd.gnt_test.viewModel.LoginViewModel
import javax.inject.Inject


@AndroidEntryPoint
class FormsFragment : Fragment() {

    lateinit var binding: FragmentFormsBinding

    @Inject
    lateinit var preferencesManager: PreferencesManager

    private val adapter = FormsAdapter {
        val coordinates = it.split(",")
        val lat = coordinates.first().toDouble()
        val long = coordinates.last().toDouble()
        val bundle = Bundle()
        bundle.putDouble("lat", lat)
        bundle.putDouble("long", long)
        findNavController().navigate(R.id.navigation_user_map, bundle)
    }

    private val formsVM: FormViewModel by viewModels()

    private val logoutVM: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFormsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getForms()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.formsRv.init(adapter)

        binding.apply {
            addFormBtn.setOnClickListener {
                findNavController().navigateTo(R.id.navigation_form)
            }
            logoutBtn.setOnClickListener {
                logoutVM.logout()
                logoutVM.logoutObservable.observe(viewLifecycleOwner) {
                    preferencesManager.clearAll()
                }
                findNavController().navigateTo(R.id.navigation_login, true)
            }
        }
    }

    private fun getForms(){
        formsVM.apply {
            getForms()
            formsObservable.observe(viewLifecycleOwner) { response ->
                when(response) {
                    is ResponseWrapper.Failure -> requireContext().toast("something went wrong")
                    is ResponseWrapper.LocalFailure -> requireContext().toast("No Connection!")
                    is ResponseWrapper.Success -> {
                        adapter.setItems(response.value.FormData)
                        adapter.notifyDataSetChanged()
                    }
                    else -> {}
                }
            }
        }
    }

}