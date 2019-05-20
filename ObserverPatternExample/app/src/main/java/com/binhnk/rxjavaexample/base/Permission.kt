package com.binhnk.rxjavaexample.base

import androidx.fragment.app.Fragment
import pub.devrel.easypermissions.EasyPermissions

object Permission {
    fun requestPermissions(
        fragment: Fragment,
        rationale: String,
        requestCode: Int,
        perms: Array<String>
    ) {
        EasyPermissions.requestPermissions(fragment, rationale, requestCode, *perms)
    }
}