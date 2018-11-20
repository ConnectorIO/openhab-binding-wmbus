/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.wmbus.device.diehl;

import org.openhab.binding.wmbus.WMBusDevice;
import org.openhab.binding.wmbus.handler.WMBusAdapter;
import org.openmuc.jmbus.DecodingException;
import org.openmuc.jmbus.wireless.WMBusMessage;

/**
 * Representation of Diehl devices.
 *
 * @author Łukasz Dywicki - Initial contribution.
 */
public class DiehlWMBusDevice extends WMBusDevice {

    public DiehlWMBusDevice(WMBusDevice device) throws DecodingException {
        this(device.getOriginalMessage(), device.getAdapter());
    }

    public DiehlWMBusDevice(WMBusMessage originalMessage, WMBusAdapter adapter) throws DecodingException {
        super(originalMessage, adapter);
    }

}
