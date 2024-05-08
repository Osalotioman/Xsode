package com.example.test4_emptyviewsact.utils
//package com.xsode.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtils {

fun checkPermission(context: Context): Boolean {
return ContextCompat.checkSelfPermission(
context,
Manifest.permission.READ_EXTERNAL_STORAGE
) == PackageManager.PERMISSION_GRANTED
}

fun requestPermission(activity: AppCompatActivity, requestCode: Int) {
ActivityCompat.requestPermissions(
activity,
arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
requestCode
)
}
}