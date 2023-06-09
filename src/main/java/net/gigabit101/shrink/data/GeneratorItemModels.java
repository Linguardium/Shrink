package net.gigabit101.shrink.data;

import net.gigabit101.shrink.Shrink;
import net.gigabit101.shrink.items.ShrinkItems;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class GeneratorItemModels extends ItemModelProvider
{
    public GeneratorItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator.getPackOutput(), Shrink.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
//        singleTexture(getPath(ShrinkItems.SHRINKING_DEVICE.get()), mcLoc("item/handheld"), "layer0", modLoc("item/" + getPath(ShrinkItems.SHRINKING_DEVICE.get())));
        singleTexture(getPath(ShrinkItems.MOB_BOTTLE.get()), mcLoc("item/handheld"), "layer0", mcLoc("item/glass_bottle"));
    }

    public String getPath(Item item)
    {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    private void registerBlockModel(Block block)
    {
        String path = ForgeRegistries.BLOCKS.getKey(block).getPath();
        getBuilder(path).parent(new ModelFile.UncheckedModelFile(modLoc("block/" + path)));
    }

    @Override
    public String getName() {
        return "Item Models";
    }

}
