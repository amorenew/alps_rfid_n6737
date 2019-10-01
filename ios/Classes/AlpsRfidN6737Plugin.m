#import "AlpsRfidN6737Plugin.h"
#import <alps_rfid_n6737/alps_rfid_n6737-Swift.h>

@implementation AlpsRfidN6737Plugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftAlpsRfidN6737Plugin registerWithRegistrar:registrar];
}
@end
