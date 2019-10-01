import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:alps_rfid_n6737/alps_rfid_n6737.dart';

void main() {
  const MethodChannel channel = MethodChannel('alps_rfid_n6737');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await AlpsRfidN6737.platformVersion, '42');
  });
}
