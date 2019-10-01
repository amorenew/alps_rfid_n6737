import 'dart:async';

import 'package:flutter/services.dart';

class AlpsRfidN6737 {
  static const MethodChannel _channel =
      const MethodChannel('alps_rfid_n6737');
  static const EventChannel dataStream = EventChannel('alps_rfid_n6737/data');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

    static Future<bool> setPowerLevel({int level = 50}) async {
    return _channel.invokeMethod(
        'setPowerLevel', <String, String>{'level': level.toString()});
  }

  static Future<bool> startRead() async {
    return _channel.invokeMethod('startRead');
  }

  static Future<bool> stopRead() async {
    return _channel.invokeMethod('stopRead');
  }

  static Future<bool> readOne() async {
    return _channel.invokeMethod('readOne');
  }
  static Future<bool> quary() async {
    return _channel.invokeMethod('quary');
  }
  static Future<bool> continuousRead() async {
    return _channel.invokeMethod('continuousRead');
  }

  static Future<bool> close() async {
    return _channel.invokeMethod('close');
  }

}
