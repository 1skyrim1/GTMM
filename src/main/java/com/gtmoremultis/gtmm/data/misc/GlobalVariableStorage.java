package com.gtmoremultis.gtmm.data.misc;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.UUID;

public abstract class GlobalVariableStorage {
    // Global EU Network
    public static HashMap<UUID, BigInteger> GlobalEnergy = new HashMap<>(100, 0.9f);
}