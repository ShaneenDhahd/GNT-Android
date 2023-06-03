package shaneen.dhahd.gnt_test.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import shaneen.dhahd.gnt_test.R
import shaneen.dhahd.gnt_test.databinding.FragmentChooseLocationBinding
import shaneen.dhahd.gnt_test.ext.UserStuff
import shaneen.dhahd.gnt_test.ext.dismiss
import shaneen.dhahd.gnt_test.ext.navigateTo

@AndroidEntryPoint
class ChooseLocationFragment : Fragment(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    lateinit var binding: FragmentChooseLocationBinding
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChooseLocationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.choose_location_map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        binding.mapDoneBtn.setOnClickListener {
            UserStuff.apply {
                lat = mMap.cameraPosition.target.latitude
                long = mMap.cameraPosition.target.latitude
                didSelectLocation = true
                dismiss()
            }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val baghdad = LatLng(33.315401, 44.365997)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(baghdad, 18f))
        mMap.setOnCameraMoveStartedListener {
            animatePin(true)
        }
        mMap.setOnCameraIdleListener {
            animatePin(false)
        }
    }
    private fun animatePin(isMoving: Boolean) {
        binding.apply {
            if (isMoving) {
                val param = markerIcon.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(0,0,0,32)
                markerIcon.layoutParams = param
            } else {
                val param = markerIcon.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(0,0,0,0)
                markerIcon.layoutParams = param
            }
        }
    }
}