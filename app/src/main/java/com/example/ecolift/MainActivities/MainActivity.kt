package com.example.ecolift.MainActivities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.ecolift.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private val homeFragment = MainFragment()
    private val profileFragment = ProfileFragment()
    private val historyFragment = BooksRideHistory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        this.window.statusBarColor = Color.rgb(33,34,38)

        replaceFragment(homeFragment)
        val bottomBar: BottomNavigationView = findViewById(R.id.main_menu)
        // calling replace funtion if id selected
        bottomBar.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(homeFragment)
                R.id.profile -> replaceFragment(profileFragment)
                R.id.history -> replaceFragment(historyFragment)
            }
            true
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
//        val exitIntent = Intent(Intent.ACTION_MAIN)
//        exitIntent.addCategory(Intent.CATEGORY_HOME)
//        exitIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//        startActivity(exitIntent)
    }

    // called when you want to replace fragment
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

}