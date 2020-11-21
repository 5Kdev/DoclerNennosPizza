package com.doclerholding.nenospizza.utils

import android.R
import android.app.Activity
import android.view.ViewGroup

class DefaultViewContainer : ViewContainer {
    override fun forActivity(activity: Activity): ViewGroup {
        return activity.findViewById(R.id.content)
    }
}