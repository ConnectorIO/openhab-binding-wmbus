/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.openhab.binding.wmbus;

import org.openhab.binding.wmbus.handler.WMBusAdapter;
import org.openhab.core.util.HexUtils;
import org.openmuc.jmbus.DataRecord;
import org.openmuc.jmbus.DecodingException;
import org.openmuc.jmbus.EncryptionMode;
import org.openmuc.jmbus.wireless.WMBusMessage;

/**
 * The {@link WMBusDevice} class defines WMBusDevice
 *
 * @author Hanno - Felix Wagner - Initial contribution
 */

public class WMBusDevice {

    private final WMBusMessage originalMessage;
    private final WMBusAdapter adapter;

    public WMBusDevice(WMBusMessage originalMessage, WMBusAdapter adapter) {
        this.originalMessage = originalMessage;
        this.adapter = adapter;
    }

    public WMBusMessage getOriginalMessage() {
        return originalMessage;
    }

    public WMBusAdapter getAdapter() {
        return adapter;
    }

    public void decode() throws DecodingException {
        originalMessage.getVariableDataResponse().decode();
    }

    public String getDeviceId() {
        return originalMessage.getSecondaryAddress().getDeviceId().toString();
    }

    public DataRecord findRecord(RecordType recordType) {
        if (RecordType.MANUFACTURER_DATA == recordType) {
            return new ManufacturerData(originalMessage.getVariableDataResponse().getManufacturerData());
        }

        for (DataRecord record : originalMessage.getVariableDataResponse().getDataRecords()) {
            if (recordType.matches(record)) {
                return record;
            }
        }
        return null;
    }

    public DataRecord findRecord(byte[] dib, byte[] vib) {
        return findRecord(new RecordType(dib, vib));
    }

    public boolean isEnrypted() {
        return originalMessage.getVariableDataResponse().getEncryptionMode() != EncryptionMode.NONE;
    }

    public String getDeviceAddress() {
        return HexUtils.bytesToHex(originalMessage.getSecondaryAddress().asByteArray());
    }

    public String getDeviceType() {
        return originalMessage.getControlField() + "" + originalMessage.getSecondaryAddress().getManufacturerId() + ""
                + originalMessage.getSecondaryAddress().getVersion() + ""
                + originalMessage.getSecondaryAddress().getDeviceType().getId();
    }

    public String getRawDeviceType() {
        return originalMessage.getControlField() + "" + originalMessage.getSecondaryAddress().getManufacturerId() + ""
                + originalMessage.getSecondaryAddress().getVersion() + "" + getOriginalDeviceTypeField();
    }

    public int getOriginalDeviceTypeField() {
        byte[] addressArray = originalMessage.getSecondaryAddress().asByteArray();
        return addressArray[addressArray.length - 1] & 0xFF;
    }

    @Override
    public String toString() {
        return originalMessage.getSecondaryAddress().toString();
    }
}

class ManufacturerData extends DataRecord {

    private final byte[] rawData;

    ManufacturerData(byte[] rawData) {
        this.rawData = rawData;
    }

    @Override
    public byte[] getDataValue() {
        return getRawData();
    }

    public byte[] getRawData() {
        return rawData;
    }
}
