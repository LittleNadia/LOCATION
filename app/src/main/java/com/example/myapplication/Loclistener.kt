package com.example.myapplication

import android.location.Location
import android.location.LocationListener

class Loclistener:LocationListener {

    private lateinit var loclistenerInterface: LoclistenerInterface

    override fun onLocationChanged(location: Location) {
        loclistenerInterface.OnLocChanged(location)
    }

     fun setLoclistenerInterface(loclistenerInterface1: LoclistenerInterface) {
        this.loclistenerInterface=loclistenerInterface1
    }

}