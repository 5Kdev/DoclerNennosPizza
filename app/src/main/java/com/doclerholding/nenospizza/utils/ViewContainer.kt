package com.doclerholding.nenospizza.utils

import android.R
import android.app.Activity
import android.view.ViewGroup

interface ViewContainer {
    fun forActivity(activity: Activity): ViewGroup? {
        return activity.findViewById(R.id.content)
    }
}