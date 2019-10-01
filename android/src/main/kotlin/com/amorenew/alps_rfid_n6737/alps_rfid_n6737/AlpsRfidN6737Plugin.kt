package com.amorenew.alps_rfid_n6737.alps_rfid_n6737

import android.util.Log
import com.amorenew.alps_rfid_n6737.alps_rfid_n6737.rfid.SerialListener
import com.amorenew.alps_rfid_n6737.alps_rfid_n6737.rfid.SerialPortHelper
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class AlpsRfidN6737Plugin : MethodCallHandler {

    companion object {
        private val METHOD_SET_POWER_LEVLEL = "setPowerLevel"
        private val METHOD_START_READ = "startRead"
        private val METHOD_STOP_READ = "stopRead"
        private val METHOD_CLOSE = "close"
        private val METHOD_QUARY = "quary"
        private val METHOD_READ_ONE = "readOne"
        private val METHOD_CONTINUOUS_READ = "continuousRead"

        private val CHANNEL_DATA = "alps_rfid_n6737/data"

        private lateinit var registrar: Registrar
        private val dataSubject = PublishSubject.create<String>()

        lateinit var serialPortHelper: SerialPortHelper;

        var serialListener = object : SerialListener {
            override fun onDataUpdated(tagId: String, rssiLevel: Int) {
                dataSubject.onNext(tagId)
            }
        };


        @JvmStatic
        fun registerWith(registrar: Registrar) {
            this.registrar = registrar;
            initDataChannel(registrar)
            serialPortHelper = SerialPortHelper(serialListener)
            val channel = MethodChannel(registrar.messenger(), "alps_rfid_n6737")
            channel.setMethodCallHandler(AlpsRfidN6737Plugin())
            Log.d("idString", "register with")

        }

        private fun initDataChannel(registrar: Registrar) {
            val dataEventChannel = EventChannel(registrar.messenger(),
                    CHANNEL_DATA)
            dataEventChannel.setStreamHandler(object : EventChannel.StreamHandler {
                override fun onListen(value: Any?, eventSink: EventChannel.EventSink) {
                    dataSubject
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<String> {
                                override fun onSubscribe(d: Disposable) {
                                    Log.d("idString", "onSubscribe")
                                }

                                override fun onNext(data: String) {

                                    eventSink.success(data)
                                }

                                override fun onError(e: Throwable) {
                                    Log.d("idString", "onError")

                                }

                                override fun onComplete() {
                                    Log.d("idString", "onComplete")

                                }
                            })
                }

                override fun onCancel(o: Any?) {

                }
            })
        }

    }

    private lateinit var result: Result
    override fun onMethodCall(call: MethodCall, result: Result) {
        this.result = result
        when (call.method) {
            "getPlatformVersion" ->
                result.success("Android ${android.os.Build.VERSION.RELEASE}")
            METHOD_SET_POWER_LEVLEL ->
                setPowerLevel(call)
            METHOD_START_READ ->
                startRead()
            METHOD_STOP_READ ->
                stopRead()
            METHOD_READ_ONE ->
                readOne()
            METHOD_QUARY ->
                quary()
            METHOD_CONTINUOUS_READ ->
                continuousRead()
            METHOD_CLOSE ->
                close()
            else -> result.notImplemented()
        }

    }

    private fun setPowerLevel(call: MethodCall) {
        val level = call.argument<String>("level");
        var isSuccess = serialPortHelper.setPowerLevel(Integer.parseInt(level!!));
        result.success(isSuccess);
    }

    private fun startRead() {
        Log.d("idString", "start")

        serialPortHelper.startRead();
        result.success(true);
    }

    private fun stopRead() {
        serialPortHelper.stopRead();
        result.success(true);
    }

    private fun readOne() {
        serialPortHelper.readOne();
        result.success(true);
    }

    private fun quary() {
        serialPortHelper.quary();
        result.success(true);
    }

    private fun continuousRead() {
        serialPortHelper.continuousRead();
        result.success(true);
    }

    private fun close() {
        serialPortHelper.close();
        result.success(true);
    }


}
