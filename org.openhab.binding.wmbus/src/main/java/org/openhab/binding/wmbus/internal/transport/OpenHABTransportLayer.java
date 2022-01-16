package org.openhab.binding.wmbus.internal.transport;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.openhab.core.io.transport.serial.PortInUseException;
import org.openhab.core.io.transport.serial.SerialPort;
import org.openhab.core.io.transport.serial.SerialPortIdentifier;
import org.openhab.core.io.transport.serial.SerialPortManager;
import org.openhab.core.io.transport.serial.UnsupportedCommOperationException;
import org.openmuc.jmbus.transportlayer.TransportLayer;

public class OpenHABTransportLayer implements TransportLayer {

  private final SerialPortIdentifier portIdentifier;
  private final SerialPortManager serialPortManager;
  private final int baudrate;
  private final int dataBits;
  private final int stopBits;
  private final int parity;
  private int timeout;
  private SerialPort serialPort;

  public OpenHABTransportLayer(SerialPortIdentifier portIdentifier, SerialPortManager serialPortManager, int baudrate, int dataBits, int stopBits, int parity, int timeout) {
    this.portIdentifier = portIdentifier;
    this.serialPortManager = serialPortManager;
    this.baudrate = baudrate;
    this.dataBits = dataBits;
    this.stopBits = stopBits;
    this.parity = parity;
    this.timeout = timeout;
  }

  @Override
  public void open() throws IOException {
    try {
      serialPort = portIdentifier.open("openhab-wmbus", timeout);
      serialPort.setSerialPortParams(baudrate, dataBits, stopBits, parity);
    } catch (PortInUseException | UnsupportedCommOperationException e) {
      throw new IOException(e);
    }
  }

  @Override
  public void close() {
    serialPort.close();
  }

  @Override
  public DataOutputStream getOutputStream() {
    try {
      return new DataOutputStream(serialPort.getOutputStream());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public DataInputStream getInputStream() {
    try {
      return new DataInputStream(serialPort.getInputStream());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean isClosed() {
    return serialPort == null;
  }

  @Override
  public void setTimeout(int timeout) throws IOException {
    this.timeout = timeout;
  }

  @Override
  public int getTimeout() throws IOException {
    return timeout;
  }

}
