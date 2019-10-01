import Flutter
import UIKit

public class SwiftAlpsRfidN6737Plugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "alps_rfid_n6737", binaryMessenger: registrar.messenger())
    let instance = SwiftAlpsRfidN6737Plugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    result("iOS " + UIDevice.current.systemVersion)
  }
}
