import 'dart:async';

import 'package:flutter/services.dart';

class AlpsRfidN6737 {
  /// the method channel name for rfid
  static const MethodChannel _channel = const MethodChannel('alps_rfid_n6737');

  /// the stream event name for rfid tags
  static const EventChannel dataStream = EventChannel('alps_rfid_n6737/data');

  /// get platform version
  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  ///set rfid rssi level
  static Future<bool> setPowerLevel({int level = 50}) async {
    return _channel.invokeMethod(
        'setPowerLevel', <String, String>{'level': level.toString()});
  }

  ///start reading rfid tags
  static Future<bool> startRead() async {
    return _channel.invokeMethod('startRead');
  }

  ///stop reading rfid sensor
  static Future<bool> stopRead() async {
    return _channel.invokeMethod('stopRead');
  }

  ///read one rfid tag
  static Future<bool> readOne() async {
    return _channel.invokeMethod('readOne');
  }

  ///quary rfid rssi
  static Future<bool> quary() async {
    return _channel.invokeMethod('quary');
  }

  ///keep always reading rfid tags
  static Future<bool> continuousRead() async {
    return _channel.invokeMethod('continuousRead');
  }

  ///close rfid sensor
  static Future<bool> close() async {
    return _channel.invokeMethod('close');
  }
}
