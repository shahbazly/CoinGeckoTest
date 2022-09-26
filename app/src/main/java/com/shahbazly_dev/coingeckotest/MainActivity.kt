package com.shahbazly_dev.coingeckotest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shahbazly_dev.coingeckotest.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.main_activity) {

    private val viewBinding: MainActivityBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set navigation icon on the configuration change and when navigated somewhere.
        if (savedInstanceState != null && supportFragmentManager.backStackEntryCount != 0) {
            viewBinding.topAppBar.setNavigationIcon(R.drawable.ic_back)
        }

        viewBinding.topAppBar.setNavigationOnClickListener {
            supportFragmentManager.popBackStack()
        }

        // Change navigation icon and text based on the back stack entry count.
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount != 0) {
                viewBinding.topAppBar.setNavigationIcon(R.drawable.ic_back)
            } else {
                viewBinding.topAppBar.navigationIcon = null
                viewBinding.topAppBar.title = getString(R.string.src_main_toolbar_text)
            }
        }
    }
}