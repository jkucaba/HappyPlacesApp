package com.example.happyplaces.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.happyplaces.R
import com.example.happyplaces.databinding.ActivityMapBinding
import com.example.happyplaces.models.HappyPlaceModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mHappyPlaceDetail : HappyPlaceModel? = null

    private var binding: ActivityMapBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        if(intent.hasExtra(MainActivity.EXTRA_PLACE_DETAILS)){
            mHappyPlaceDetail = intent.getParcelableExtra(MainActivity.EXTRA_PLACE_DETAILS)
                    as HappyPlaceModel?
        }

        if(mHappyPlaceDetail!= null){
            setSupportActionBar(binding?.toolbarMap)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = mHappyPlaceDetail!!.title

            binding?.toolbarMap?.setNavigationOnClickListener {
                onBackPressed()
            }

            val supportMapFragment : SupportMapFragment =
                supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            supportMapFragment.getMapAsync(this)
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        val position = LatLng(mHappyPlaceDetail!!.latitude, mHappyPlaceDetail!!.longitude)
        p0.addMarker(
            MarkerOptions()
              .position(position)
              .title(mHappyPlaceDetail!!.location)
        )       // show marker
        val newLatLngZoom = CameraUpdateFactory.newLatLngZoom(position, 15f)
        p0.animateCamera(newLatLngZoom) //moves the marker to the location
    }
}