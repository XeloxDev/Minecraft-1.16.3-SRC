package net.minecraft.block;

import java.util.Random;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;

public class WeepingVinesTopBlock extends AbstractTopPlantBlock
{
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(4.0D, 9.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public WeepingVinesTopBlock(AbstractBlock.Properties properties)
    {
        super(properties, Direction.DOWN, SHAPE, false, 0.1D);
    }

    protected int getGrowthAmount(Random rand)
    {
        return PlantBlockHelper.getGrowthAmount(rand);
    }

    protected Block getBodyPlantBlock()
    {
        return Blocks.WEEPING_VINES_PLANT;
    }

    protected boolean canGrowIn(BlockState state)
    {
        return PlantBlockHelper.isAir(state);
    }
}