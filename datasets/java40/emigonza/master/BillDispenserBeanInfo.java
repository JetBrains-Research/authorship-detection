//------------------------------------------------------------------------------
//
// THIS SOFTWARE IS PROVIDED AS IS. THE JAVAPOS WORKING GROUP MAKES NO
// REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE,
// EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
// WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
// NON-INFRINGEMENT.  INDIVIDUAL OR CORPORATE MEMBERS OF THE JAVAPOS
// WORKING GROUP SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED AS A RESULT
// OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
//
// BillDispenserBeanInfo.java - Bean information for the JavaPOS BillDispenser
//    device control
//
//------------------------------------------------------------------------------

package jpos;

import java.beans.*;
import java.lang.reflect.*;

public class BillDispenserBeanInfo
  extends SimpleBeanInfo
{
  public BeanDescriptor getBeanDescriptor()
  {
    return new BeanDescriptor(jpos.BillDispenser.class);
  }

  public PropertyDescriptor makeProperty(String propertyName)
    throws IntrospectionException
  {
    return new PropertyDescriptor(propertyName, jpos.BillDispenser.class);
  }

  public PropertyDescriptor[] getPropertyDescriptors()
  {
    try
    {
      PropertyDescriptor[] properties =
      {
        // Capabilities
        makeProperty("CapCompareFirmwareVersion"),
        makeProperty("CapDiscrepancy"),
        makeProperty("CapEmptySensor"),
        makeProperty("CapJamSensor"),
        makeProperty("CapNearEmptySensor"),
        makeProperty("CapPowerReporting"),
        makeProperty("CapStatisticsReporting"),
        makeProperty("CapUpdateFirmware"),
        makeProperty("CapUpdateStatistics"),

        // Properties
        makeProperty("AsyncMode"),
        makeProperty("AsyncResultCode"),
        makeProperty("AsyncResultCodeExtended"),
        makeProperty("CurrencyCashList"),
        makeProperty("CurrencyCode"),
        makeProperty("CurrencyCodeList"),
        makeProperty("CurrentExit"),
        makeProperty("DeviceExits"),
        makeProperty("DeviceStatus"),
        makeProperty("ExitCashList"),
        makeProperty("PowerNotify"),
        makeProperty("PowerState")
      };

      return properties;
    }
    catch(Exception e)
    {
      return super.getPropertyDescriptors();
    }
  }

  public EventSetDescriptor makeEvent(String eventName)
    throws IntrospectionException, ClassNotFoundException
  {
    String listener = "jpos.events." + eventName + "Listener";
    return new EventSetDescriptor(jpos.BillDispenser.class,
                                  eventName,
                                  Class.forName(listener),
                                  eventName + "Occurred");
  }

  public EventSetDescriptor[] getEventSetDescriptors()
  {
    try
    {
      EventSetDescriptor[] events =
      {
        makeEvent("DirectIO"),
        makeEvent("StatusUpdate")
      };

      return events;
    }
    catch(Exception e)
    {
      return super.getEventSetDescriptors();
    }
  }
}
