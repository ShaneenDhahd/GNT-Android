package shaneen.dhahd.gnt_test.ui

import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import shaneen.dhahd.gnt_test.R
import shaneen.dhahd.gnt_test.databinding.FragmentChooseLocationBinding
import shaneen.dhahd.gnt_test.databinding.FragmentUserMapBinding
import shaneen.dhahd.gnt_test.ext.UserStuff
import shaneen.dhahd.gnt_test.ext.dismiss

class UserMapFragment : Fragment(), OnMapReadyCallback {


    private lateinit var binding: FragmentUserMapBinding
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserMapBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.user_location_map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val lat = arguments?.getDouble("lat")
        val long = arguments?.getDouble("long")
        lat?.also {
            val location = LatLng(lat, long!!)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18f))
            putMarker(location)
        } ?: {
            val baghdad = LatLng(33.315401, 44.365997)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(baghdad, 18f))
            putMarker(baghdad)
        }
    }

    private fun putMarker(location: LatLng) {
        mMap.addMarker(
            MarkerOptions()
                .position(location)
                .title(null))
    }
}