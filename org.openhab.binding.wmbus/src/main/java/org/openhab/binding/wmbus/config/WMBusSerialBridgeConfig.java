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
package org.openhab.binding.wmbus.config;

import org.openmuc.jmbus.wireless.WMBusMode;

/**
 * A specialized version of configuration of serial devices - USB sticks.
 *
 * @author Łukasz Dywicki - Initial contribution
 */
public class WMBusSerialBridgeConfig extends WMBusBridgeConfig {

    public StickModel stickModel;
    public String serialDevice;
    public WMBusMode radioMode;
}
