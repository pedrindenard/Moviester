package com.app.moviester.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.app.moviester.R
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_maps.*

@Suppress("DEPRECATION")
class MapsFragment : Fragment(), OnMapReadyCallback {

    private var mGoogleMap: GoogleMap? = null
    private var mMapView: MapView? = null
    private var mMapsSupported = true

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {
            MapsInitializer.initialize(activity)
        } catch (e: GooglePlayServicesNotAvailableException) {
            mMapsSupported = false
        }
        if (mMapView != null) {
            mMapView!!.onCreate(savedInstanceState)
        }
        initializeMap()
    }

    private fun initializeMap() {
        if (mGoogleMap == null && mMapsSupported) {
            mMapView = requireActivity().findViewById<View>(R.id.map_view) as MapView
            mGoogleMap = mMapView?.map
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val parent = inflater.inflate(R.layout.fragment_maps, container, false) as LinearLayout
        mMapView = parent.findViewById<View>(R.id.map_view) as MapView
        return parent
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMapView!!.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        mMapView!!.onResume()
        initializeMap()
    }

    override fun onPause() {
        super.onPause()
        mMapView!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView!!.onLowMemory()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
    }
}