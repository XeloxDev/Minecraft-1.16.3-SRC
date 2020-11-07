package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;

public class CoralBlock extends Block
{
    private final Block deadBlock;

    public CoralBlock(Block deadBlock, AbstractBlock.Properties properties)
    {
        super(properties);
        this.deadBlock = deadBlock;
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
    {
        if (!this.canLive(worldIn, pos))
        {
            worldIn.setBlockState(pos, this.deadBlock.getDefaultState(), 2);
        }
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (!this.canLive(worldIn, currentPos))
        {
            worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 60 + worldIn.getRandom().nextInt(40));
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    protected boolean canLive(IBlockReader reader, BlockPos pos)
    {
        for (Direction direction : Direction.values())
        {
            FluidState fluidstate = reader.getFluidState(pos.offset(direction));

            if (fluidstate.isTagged(FluidTags.WATER))
            {
                return true;
            }
        }

        return false;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        if (!this.canLive(context.getWorld(), context.getPos()))
        {
            context.getWorld().getPendingBlockTicks().scheduleTick(context.getPos(), this, 60 + context.getWorld().getRandom().nextInt(40));
        }

        return this.getDefaultState();
    }
}
