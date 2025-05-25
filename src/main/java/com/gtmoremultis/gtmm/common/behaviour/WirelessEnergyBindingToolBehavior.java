package com.gtmoremultis.gtmm.common.behaviour;

import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.item.component.IItemUIFactory;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gtmoremultis.gtmm.api.gui.widget.TerminalInputWidget;
import com.gtmoremultis.gtmm.api.machine.multiblock.WirelessEnergyHatchPartMachine;
import com.gtmoremultis.gtmm.common.machine.multiblock.WPowerSubstationMachine;
import com.lowdragmc.lowdraglib.gui.editor.ColorPattern;
import com.lowdragmc.lowdraglib.gui.factory.HeldItemUIFactory;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.widget.*;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class WirelessEnergyBindingToolBehavior implements IItemUIFactory {

    private ItemStack itemStack;

    @Getter
    @Setter
    public int frequency = 0;

    public WirelessEnergyBindingToolBehavior() {}

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) {
            Level level = context.getLevel();
            BlockPos pos = context.getClickedPos();
            if (MetaMachine.getMachine(level, pos) instanceof WirelessEnergyHatchPartMachine hatch) {
                hatch.setFrequencyFromString(String.valueOf(frequency));
                context.getPlayer().sendSystemMessage(Component.translatable("item.wireless_energy_binding_tool.chat_message.1", String.valueOf(getFrequency())));
                return InteractionResult.CONSUME;
            } else if (MetaMachine.getMachine(level, pos) instanceof WPowerSubstationMachine machine) {
                machine.setFrequency(frequency);
                context.getPlayer().sendSystemMessage(Component.translatable("item.wireless_energy_binding_tool.chat_message.2", String.valueOf(getFrequency())));
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public ModularUI createUI(HeldItemUIFactory.HeldItemHolder holder, Player entityPlayer) {
        return new ModularUI(108, 34, holder, entityPlayer).widget(createWidget());
    }

    private Widget createWidget() {
        var group = new WidgetGroup(0, 0, 100 + 8, 26 + 8);
        List<Component> lines = new ArrayList<>(List.of());
        //lines.add(Component.translatable("item.gtmm.wireless_energy_binding_tool.setting.tooltip"));
        group.addWidget(
                new DraggableWidgetGroup(4, 4, 100, 24)
                        .addWidget(new LabelWidget(4, 7, Component.translatable("item.gtmm.wireless_energy_binding_tool.setting.1").getString())
                                .setHoverTooltips(lines))
                        .addWidget(new TerminalInputWidget(70, 5, 20, 16, this::getFrequency,
                                this::setFrequencyWidget)
                                .setMin(0).setMax(99))
                        .setBackground(GuiTextures.DISPLAY));
        group.setBackground(GuiTextures.BACKGROUND_INVERSE);
        return group;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand usedHand) {
        this.itemStack = player.getItemInHand(usedHand);
        var tag = this.itemStack.getTag();
        if (tag != null && !tag.isEmpty()) {
            this.setFrequency(tag.getInt("frequency"));
        } else {
            tag = new CompoundTag();
            tag.putInt("frequency", 0);
            this.itemStack.setTag(tag);
            this.setFrequency(0);
        }
        return IItemUIFactory.super.use(item, level, player, usedHand);
    }

    private void setFrequencyWidget(int frequency) {
        setFrequency(frequency);
        var tag = this.itemStack.getTag();
        if (tag == null) tag = new CompoundTag();
        tag.putInt("frequency", frequency);
        this.itemStack.setTag(tag);
    }
}
