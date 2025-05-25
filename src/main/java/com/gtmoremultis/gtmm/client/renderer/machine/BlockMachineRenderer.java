package com.gtmoremultis.gtmm.client.renderer.machine;

import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.client.renderer.machine.IPartRenderer;
import com.gregtechceu.gtceu.client.renderer.machine.MachineRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class BlockMachineRenderer extends MachineRenderer implements IPartRenderer {
    public BlockMachineRenderer(ResourceLocation modelLocation) {
        super(modelLocation);
    }


    /*
    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderMachine(List<BakedQuad> quads, MachineDefinition definition, @Nullable MetaMachine machine, Direction frontFacing, @Nullable Direction side, RandomSource rand, @Nullable Direction modelFacing, ModelState modelState) {
        this.renderBaseModel(quads, definition, machine, frontFacing, side, rand);
        // List<BakedQuad> quads, MachineDefinition definition, @Nullable MetaMachine machine, ModelState modelState, @Nullable Direction side, RandomSource rand
    }
     */
}
