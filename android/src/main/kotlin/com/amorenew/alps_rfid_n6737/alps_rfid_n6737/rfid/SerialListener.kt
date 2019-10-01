package com.amorenew.alps_rfid_n6737.alps_rfid_n6737.rfid

interface SerialListener {
    fun onDataUpdated(tagId: String, rssiLevel: Int)
}
