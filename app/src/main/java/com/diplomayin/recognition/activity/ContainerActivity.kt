package com.diplomayin.recognition.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.diplomayin.recognition.R
import com.diplomayin.recognition.base.utils.extension.replaceFragment
import com.diplomayin.recognition.base.utils.viewBinding
import com.diplomayin.recognition.databinding.ActivityContainerBinding
import com.diplomayin.recognition.fragment.map.GoogleMapFragment
import com.diplomayin.recognition.fragment.map.MapScreenFragment
import com.diplomayin.recognition.fragment.profile.ProfileFragment
import com.diplomayin.recognition.fragment.recognition.RecognitionFragment
import com.google.android.libraries.maps.MapFragment
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_container.*

class ContainerActivity : AppCompatActivity() {

    var mapFragment = GoogleMapFragment.newInstance()
    var recognitionFragment = RecognitionFragment.newInstance()
    var profileFragment = ProfileFragment.newInstance()

    val navListener = object : BottomNavigationView.OnNavigationItemSelectedListener{
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            var selectedFragment : Fragment? = null

            when(item.itemId){
                R.id.nav_recognition -> {
                    selectedFragment = recognitionFragment
                }
                R.id.nav_map -> {
                    selectedFragment = mapFragment
                }
                R.id.nav_profile -> {
                    selectedFragment = profileFragment
                }
            }

            if (selectedFragment != null) {
                supportFragmentManager.replaceFragment(R.id.frame_container,selectedFragment)
            }
            return true
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener)
        bottomNavigationView.selectedItemId = R.id.nav_map
    }
}