package com.amorenew.alps_rfid_n6737.alps_rfid_n6737.rfid

import android.os.Handler

class SerialPortHelper internal constructor(private val serialListener: SerialListener) {
    private val manager: Manager
    var isOpen = false
        private set
    var rssiText = ""
        internal set
    internal val MinLevel = 50
    internal val MaxLevel = 90

    private val mHandler = object : Handler() {
        override fun handleMessage(msg: android.os.Message) {
            when (msg.what) {
                0 -> {
                    val data0 = msg.data.getString("data")
                    if (data0!!.length == 28) {
                        if (data0.length == 28) {
                            val id = data0.substring(10, 18)
                            val rssi = data0.substring(22, 24)
                            val hexStringToString = hexStringToString(id)
                            serialListener.onDataUpdated(hexStringToString, Integer.valueOf(rssi, 16) * -1)

                        } else {

                        }
                    }
                }
                1 -> msg.data.getString("data")?.let { serialListener.onDataUpdated(it, 0) }
            }/*cardAdapter.notifyDataSetChanged();*/
        }
    }

    init {
        this.manager = Manager(mHandler)

    }

    fun setPowerLevel(rssiValue: Int): Boolean {
        var rssiValue = rssiValue
        if (rssiValue < MinLevel)
            rssiValue = MinLevel
        if (rssiValue > MaxLevel)
            rssiValue = MaxLevel
        this.rssiText = Integer.toHexString(rssiValue) + "0D0A"
        setRssiFiltration()
        return isOpen;
    }

    internal fun startRead() {
        if (!isOpen) {
            manager.open()
            isOpen = true
        }

    }

    internal fun close() {
        manager.close()
        isOpen = false
    }

    internal fun stopRead() {
        if (isOpen)
            manager.stopRead()
    }

    internal fun quary() {
        if (isOpen)
            manager.quary()
    }

    internal fun readOne() {
        if (isOpen)
            manager.readOne()
    }

    internal fun continuousRead() {
        if (isOpen)
            manager.continuousRead()
    }

    internal fun setRssiFiltration() {
        if (isOpen)
            manager.setRssiFiltration(rssiText)
    }

    fun hexStringToString(hexString: String): String {
        val a = Integer.valueOf(hexString, 16)
        var returnString = a.toString()
        val length = returnString.length

        if (length == 10) {
        } else {
            for (i in 0 until 10 - length) {
                returnString = "0$returnString"
            }
        }
        return returnString
    }


}
