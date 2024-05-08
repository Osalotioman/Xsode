package com.example.test4_emptyviewsact.utils
//package com.xsode.utils

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.test4_emptyviewsact.R
//import com.xsode.R  // Add this import statement

object DrawerUtils {

fun setupDrawerToggle(activity: AppCompatActivity, drawerLayout: DrawerLayout): ActionBarDrawerToggle {
val toggle = ActionBarDrawerToggle(
activity,
drawerLayout,
R.string.navigation_drawer_open,
R.string.navigation_drawer_close
)
drawerLayout.addDrawerListener(toggle)
activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
activity.supportActionBar?.setHomeButtonEnabled(true)
return toggle
}
}