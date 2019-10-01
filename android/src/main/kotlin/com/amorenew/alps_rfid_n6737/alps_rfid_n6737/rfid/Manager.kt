package com.amorenew.alps_rfid_n6737.alps_rfid_n6737.rfid

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import cn.pda.serialport.SerialPort
import cn.pda.serialport.Tools
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class Manager(private val handler: Handler) {

    private var mSerialPort: SerialPort? = null
    private var `is`: InputStream? = null
    private var os: OutputStream? = null
    private var recvThread: RecvThread? = null


    private val cmdStop = "A55A0007020D0A"
    private val cmdContinuous = "A55A0007010D0A"
    private val cmdQuery = "A55A0008050D0A"
    private val cmdReadOne = "A55A0007000D0A"
    private val setRssiFiltration = "A55A000804"

    fun open() {
        try {
            mSerialPort = SerialPort(port, BaudRate, 0)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        mSerialPort!!.power_3v3on()
        `is` = mSerialPort!!.inputStream
        os = mSerialPort!!.outputStream
        try {
            Thread.sleep(100)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        recvThread = RecvThread()
        recvThread!!.start()
    }

    fun close() {
        if (recvThread != null) {
            recvThread!!.interrupt()
        }
        if (mSerialPort != null) {
            mSerialPort!!.setGPIOlow(89)
            try {
                `is`!!.close()
                os!!.close()
            } catch (e: IOException) {

            }

            mSerialPort!!.close(port)
            mSerialPort!!.power_3v3off()

        }
    }

    /**
     * recv thread receive serialport data
     * @author Administrator
     */
    private inner class RecvThread : Thread() {
        override fun run() {
            super.run()
            try {
                while (!isInterrupted) {
                    var size = 0
                    val buffer = ByteArray(1024)
                    if (`is` == null) {
                        return
                    }
                    size = `is`!!.read(buffer)
                    if (size > 0) {
                        //onDataReceived(buffer, size);
                        sendMessege(buffer, size, 0)
                    }
                    Thread.sleep(100)
                }
            } catch (e: Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

        }
    }

    private fun sendMessege(data: ByteArray, dataLen: Int, mode: Int) {
        var mode = mode
        try {
            //          String dataStr = new String(data, 0, dataLen,"GBK");
            val dataStr = Tools.Bytes2HexString(data, dataLen)
            Log.e("hai-len", (dataStr.length % 28).toString() + "")
            if (dataStr.length % 28 == 0) {
                mode = 0
            } else {
                mode = 1
            }
            Log.e("hai-mode", mode.toString() + "")

            val bundle = Bundle()
            bundle.putString("data", dataStr)
            val msg = Message()
            msg.what = mode
            msg.data = bundle
            handler.sendMessage(msg)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    fun stopRead() {

        send(cmdStop)

    }

    fun readOne() {

        send(cmdReadOne)

    }


    fun continuousRead() {

        send(cmdContinuous)

    }


    fun quary() {

        send(cmdQuery)
    }

    fun setRssiFiltration(rssi: String) {

        send(setRssiFiltration + rssi)
    }


    /**
     * Send command to serial port
     */
    private fun send(commandStr: String?) {
        try {
            var cmd: ByteArray? = null
            Log.i("send()", commandStr!!)
            //editRecv.append("[SendCmd]:" + commandStr + "\n");
            if (commandStr == null) {
                //                showToast("cmd is null");
            } else {
                cmd = Tools.HexString2Bytes(commandStr)
                os!!.write(cmd!!)
            }

        } catch (e: IOException) {
            Log.e("", "send(), IOException >>>>>> " + Log.getStackTraceString(e))
        } catch (e: Exception) {
            Log.e("", "send(), Exception >>>>>> " + Log.getStackTraceString(e))

        }

    }

    companion object {
        var port = 12
        var BaudRate = 57600
    }
}
