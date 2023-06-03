package shaneen.dhahd.gnt_test.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import shaneen.dhahd.gnt_test.R
import shaneen.dhahd.gnt_test.databinding.FragmentFormsBinding
import shaneen.dhahd.gnt_test.ext.init
import shaneen.dhahd.gnt_test.ext.toast
import shaneen.dhahd.gnt_test.network.wrappers.ResponseWrapper
import shaneen.dhahd.gnt_test.ui.adapter.FormsAdapter
import shaneen.dhahd.gnt_test.viewModel.FormViewModel

@AndroidEntryPoint
class FormsFragment : Fragment() {

    lateinit var binding: FragmentFormsBinding
    private val adapter = FormsAdapter {

    }
    private val formsVM: FormViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFormsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.formsRv.init(adapter)
        getForms()
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