package org.openhab.binding.wmbus.internal.transport;

import java.io.IOException;
import org.openhab.core.io.transport.serial.SerialPort;
import org.openhab.core.io.transport.serial.SerialPortIdentifier;
import org.openhab.core.io.transport.serial.SerialPortManager;
import org.openmuc.jmbus.transportlayer.TransportLayer;
import org.openmuc.jmbus.wireless.WMBusConnection.WMBusManufacturer;
import org.openmuc.jmbus.wireless.WMBusConnection.WMBusSerialBuilder;
import org.openmuc.jmbus.wireless.WMBusListener;
import org.openmuc.jmbus.wireless.WMBusMessage;
import org.openmuc.jrxtx.DataBits;
import org.openmuc.jrxtx.Parity;
import org.openmuc.jrxtx.StopBits;

public class OpenHABWMBusSerialBuilder extends WMBusSerialBuilder {

  private final SerialPortManager serialPortProvider;

  private String serialPortName;
  private int baudrate;
  private int dataBits;
  private int stopBits;
  private int parity;
  private int timeout;

  public OpenHABWMBusSerialBuilder(SerialPortManager serialPortProvider, WMBusManufacturer wmBusManufacturer, WMBusListener listener, String serialPortName) {
    super(wmBusManufacturer, listener, serialPortName);

    this.serialPortProvider = serialPortProvider;
  }

  @Override
  public WMBusSerialBuilder setSerialPortName(String serialPortName) {
    this.serialPortName = serialPortName;
    return self();
  }

  @Override
  public WMBusSerialBuilder setBaudrate(int baudrate) {
    this.baudrate = baudrate;
    return self();
  }

  @Override
  public WMBusSerialBuilder setDataBits(DataBits dataBits) {
    this.dataBits = dataBits(dataBits);
    return self();
  }

  @Override
  public WMBusSerialBuilder setStopBits(StopBits stopBits) {
    this.stopBits = stopBits(stopBits);
    return self();
  }

  @Override
  public WMBusSerialBuilder setParity(Parity parity) {
    this.parity = parity(parity);
    return self();
  }

  @Override
  public WMBusSerialBuilder setTimeout(int timeout) {
    this.timeout = timeout;
    return self();
  }

  @Override
  protected TransportLayer buildTransportLayer() {
    SerialPortIdentifier portIdentifier = serialPortProvider.getIdentifier(serialPortName);
    return new OpenHABTransportLayer(
      portIdentifier, serialPortProvider,
      baudrate, dataBits, stopBits, parity, timeout
    );
  }

  private int stopBits(StopBits stopBits) {
    if (StopBits.STOPBITS_1 == stopBits) {
      return SerialPort.STOPBITS_1;
    }
    if (StopBits.STOPBITS_1_5 == stopBits) {
      return SerialPort.STOPBITS_1_5;
    }
    if (StopBits.STOPBITS_2 == stopBits) {
      return SerialPort.STOPBITS_2;
    }
    throw new IllegalArgumentException("Unknown stop bit setting " + stopBits);
  }

  int dataBits(DataBits dataBits) {
    if (DataBits.DATABITS_5 == dataBits) {
      return SerialPort.DATABITS_5;
    }
    if (DataBits.DATABITS_6 == dataBits) {
      return SerialPort.DATABITS_6;
    }
    if (DataBits.DATABITS_7 == dataBits) {
      return SerialPort.DATABITS_7;
    }
    if (DataBits.DATABITS_8 == dataBits) {
      return SerialPort.DATABITS_8;
    }
    throw new IllegalArgumentException("Unknown data bits setting " + dataBits);
  }

  private int parity(Parity parity) {
    if (parity == Parity.NONE) {
      return SerialPort.PARITY_NONE;
    }
    if (parity == Parity.ODD) {
      return SerialPort.PARITY_ODD;
    }
    if (parity == Parity.EVEN) {
      return SerialPort.PARITY_EVEN;
    }
    if (parity == Parity.MARK) {
      return SerialPort.PARITY_MARK;
    }
    if (parity == Parity.SPACE) {
      return SerialPort.PARITY_SPACE;
    }
    throw new IllegalArgumentException("Unknown parity setting " + parity);
  }

  public static void main(String[] args) {
    OpenHABWMBusSerialBuilder serialBuilder = new OpenHABWMBusSerialBuilder(null,
        WMBusManufacturer.AMBER, new WMBusListener() {
      @Override
      public void newMessage(WMBusMessage message) {

      }

      @Override
      public void discardedBytes(byte[] bytes) {

      }

      @Override
      public void stoppedListening(IOException cause) {

      }
    }, "/dev/ttyS0");
    serialBuilder.buildTransportLayer();
  }

}
