package com.gtmoremultis.gtmm.block;

import com.gtmoremultis.gtmm.api.block.ITier;

public enum BlockTier implements ITier {
    TIER0(0),
    TIER1(1),
    TIER2(2),
    TIER3(3),
    TIER4(4),
    TIER5(5),
    TIER6(6),
    TIER7(7),
    TIER8(8),
    TIER9(9),
    TIER10(10),
    TIER11(11),
    TIER12(12),
    TIER13(13);

    private final int tier;

    BlockTier(int tier) {
        this.tier = tier;
    }

    @Override
    public int tier() {
        return tier;
    }
}
