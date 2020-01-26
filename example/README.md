# alps_rfid_n6737 example

A new flutter plugin project.
  alps_rfid_n6737: ^0.0.1
https://pub.dev/packages/alps_rfid_n6737

## Running example
Build `flutter build apk`

Install `adb install -r build/app/outputs/apk/release/app-release.apk`

You cannot install/start app from VS Code because that uses 64 bit. Running "without debug" has a similar problem. Android Studio might work.

## For your own project
`import 'package:alps_rfid_n6737/alps_rfid_n6737.dart';`

##### Start reading RFID tags
    `AlpsRfidN6737.startRead();`
##### Setting RSSI Level
Min Level is 50 and Max is 90

    `AlpsRfidN6737.setPowerLevel(level: 60);`
##### Keep always reading RFID tags
    `AlpsRfidN6737.continuousRead();`
##### Listen to RFID tags
    `AlpsRfidN6737.dataStream.receiveBroadcastStream().listen(rfidBoarding);
       void rfidBoarding(dynamic rfidId) { }`
##### Close RFID Sensor
    `AlpsRfidN6737.stopRead();`
##### Read One RFID tag
    `AlpsRfidN6737.readOne();`

