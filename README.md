# alps_rfid_n6737

A new flutter plugin project.
  alps_rfid_n6737: ^0.0.1
https://pub.dev/packages/alps_rfid_n6737

## Getting Started
```import 'package:alps_rfid_n6737/alps_rfid_n6737.dart';```

##### Start reading RFID tags
    ```AlpsRfidN6737.startRead();```
##### Setting RSSI Level
Min Level is 50 and Max is 90

    ```AlpsRfidN6737.setPowerLevel(level: 60);```
##### Keep always reading RFID tags
    ```AlpsRfidN6737.continuousRead();```
##### Listen to RFID tags
    ```AlpsRfidN6737.dataStream.receiveBroadcastStream().listen(rfidBoarding);
       void rfidBoarding(dynamic rfidId) { }```
##### Close RFID Sensor
    ```AlpsRfidN6737.stopRead();```
##### Read One RFID tag
    ```AlpsRfidN6737.readOne();```

