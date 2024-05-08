package com.silver2040.tntexpanded.datagen;

import com.silver2040.tntexpanded.registry.TntBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.silver2040.tntexpanded.TntExpanded.MODID;

public class TntExpandedBlockStateProvider extends BlockStateProvider {
    public TntExpandedBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(TntBlocks.BOMBLET_TNT.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/cluster_bomblet")));
    }
}
