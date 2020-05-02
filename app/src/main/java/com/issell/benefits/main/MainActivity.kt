package com.issell.benefits.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.issell.benefits.R
import com.issell.benefits.util.ActivityUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 메인 Fragment 셋
        var mainFragment:MainFragment? =
            supportFragmentManager.findFragmentById(R.id.frame_layout) as MainFragment?
        if (mainFragment == null) {
            // Create fragment
            mainFragment = MainFragment
            ActivityUtils.addFragmentToActivity(
                supportFragmentManager, mainFragment, R.id.frame_layout
            )
        }
    }
}
