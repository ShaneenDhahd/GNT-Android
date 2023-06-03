package shaneen.dhahd.gnt_test.ui

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import shaneen.dhahd.gnt_test.R
import shaneen.dhahd.gnt_test.databinding.FragmentFormBinding
import shaneen.dhahd.gnt_test.ext.*
import shaneen.dhahd.gnt_test.network.responses.GovernmentsModel
import shaneen.dhahd.gnt_test.network.wrappers.ResponseWrapper
import shaneen.dhahd.gnt_test.viewModel.FormViewModel

@AndroidEntryPoint
class FormFragment : Fragment() {


    lateinit var binding: FragmentFormBinding
    private val formVM: FormViewModel by viewModels()

    private var governmentsList = ArrayList<String>()
    private var governments = GovernmentsModel()

    private var selectedGovernment = 1
    private var name = ""
    private var username = ""
    private var comment = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFormBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGovs()
        binding.apply {
            submitBtn.isEnabled = UserStuff.didSelectLocation

            locationBtn.setOnClickListener {
                findNavController().navigateTo(R.id.navigation_map)
            }
            submitBtn.setOnClickListener {
                validateFields {
                    binding.submitProgress.visibility = View.VISIBLE
                    submitForm()
                    clearFields()
                    dismiss()
                }
            }
        }
    }

    private fun clearFields(){
        UserStuff.apply {
            lat = 0.0
            long = 0.0
            didSelectLocation = false
        }
        binding.apply {
            nameField.text?.clear()
            usernameField.text?.clear()
            commentField.text?.clear()
            govermentsSpinner.setSelection(0)
        }
    }

    private fun setupGovs() {
        val spinner = binding.govermentsSpinner
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item, governmentsList
        )
        formVM.apply {
            getGovs()
            governmentsObservable.observe(viewLifecycleOwner) { response ->
                binding.governmentsProgressBar.visibility = View.GONE

                when (response) {
                    is ResponseWrapper.Success -> {
                        governments = response.value
                        response.value.forEach {
                            governmentsList.add(it.gov_name)
                            adapter.notifyDataSetChanged()
                        }
                    }
                    else -> {}
                }
            }
        }

       spinner.adapter = adapter
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?, position: Int, id: Long
            ) {
                selectedGovernment = governments[position].gov_id
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun submitForm(){
        val gps = "${UserStuff.lat},${UserStuff.long}"
        formVM.submit(name, username, selectedGovernment.toString(), comment, gps)
        formVM.formObservable.observe(viewLifecycleOwner) {response ->
            binding.submitProgress.visibility = View.GONE
            when(response) {
                is ResponseWrapper.Failure -> requireContext().toast(response.msg)
                is ResponseWrapper.LocalFailure -> requireContext().toast("Connection Error!")
                is ResponseWrapper.Success -> requireContext().toast("Form Sent")
                else -> {}
            }
        }
    }

    private fun validateFields(completion: ()-> Unit ) {
        binding.apply {
            if (!nameField.text.isNullOrBlank() && !usernameField.text.isNullOrBlank() && !commentField.text.isNullOrBlank()) {
                name = nameField.text.toString()
                username = usernameField.text.toString()
                comment = commentField.text.toString()
                completion()
            }
            else {
                if (nameField.isEmpty()) {
                    nameField.error = "Name can not be empty"
                }
               if (usernameField.isEmpty()) {
                    usernameField.error = "Username can not be empty"
                }
               if (commentField.isEmpty()) {
                    commentField.error = "Comment can not be empty"
                }

            }
        }
    }
}
