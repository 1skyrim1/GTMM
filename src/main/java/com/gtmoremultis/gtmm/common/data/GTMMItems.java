package com.gtmoremultis.gtmm.common.data;

import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gtmoremultis.gtmm.common.behaviour.AdvancedTerminalBehaviour;
import com.gtmoremultis.gtmm.common.behaviour.WirelessEnergyBindingToolBehavior;
import com.tterrag.registrate.util.entry.ItemEntry;

import static com.gregtechceu.gtceu.common.data.GTItems.attach;
import static com.gtmoremultis.gtmm.GTMMRegistries.REGISTRATE;

public class GTMMItems {
    static {
        REGISTRATE.creativeModeTab(() -> GTMMCreativeModeTabs.MAIN_TAB);
    }

    public static ItemEntry<ComponentItem> ADVANCED_TERMINAL = REGISTRATE
            .item("advanced_terminal", ComponentItem::create)
            .lang("Advanced Terminal")
            .properties(p -> p.stacksTo(1))
            .onRegister(attach(new AdvancedTerminalBehaviour())).register();

    public static ItemEntry<ComponentItem> WIRELESS_ENERGY_BINDING_TOOL = REGISTRATE
            .item("wireless_energy_binding_tool", ComponentItem::create)
            .lang("Wireless Energy Binding Tool")
            .properties(p -> p.stacksTo(1))
            .onRegister(attach(new WirelessEnergyBindingToolBehavior())).register();

    public static void init() {}
}
