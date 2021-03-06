package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class WallBlock extends Block implements IWaterLoggable
{
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final EnumProperty<WallHeight> WALL_HEIGHT_EAST = BlockStateProperties.WALL_HEIGHT_EAST;
    public static final EnumProperty<WallHeight> WALL_HEIGHT_NORTH = BlockStateProperties.WALL_HEIGHT_NORTH;
    public static final EnumProperty<WallHeight> WALL_HEIGHT_SOUTH = BlockStateProperties.WALL_HEIGHT_SOUTH;
    public static final EnumProperty<WallHeight> WALL_HEIGHT_WEST = BlockStateProperties.WALL_HEIGHT_WEST;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final Map<BlockState, VoxelShape> stateToShapeMap;
    private final Map<BlockState, VoxelShape> stateToCollisionShapeMap;
    private static final VoxelShape field_235619_i_ = Block.makeCuboidShape(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
    private static final VoxelShape field_235620_j_ = Block.makeCuboidShape(7.0D, 0.0D, 0.0D, 9.0D, 16.0D, 9.0D);
    private static final VoxelShape field_235621_k_ = Block.makeCuboidShape(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 16.0D);
    private static final VoxelShape field_235622_o_ = Block.makeCuboidShape(0.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
    private static final VoxelShape field_235623_p_ = Block.makeCuboidShape(7.0D, 0.0D, 7.0D, 16.0D, 16.0D, 9.0D);

    public WallBlock(AbstractBlock.Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(UP, Boolean.valueOf(true)).with(WALL_HEIGHT_NORTH, WallHeight.NONE).with(WALL_HEIGHT_EAST, WallHeight.NONE).with(WALL_HEIGHT_SOUTH, WallHeight.NONE).with(WALL_HEIGHT_WEST, WallHeight.NONE).with(WATERLOGGED, Boolean.valueOf(false)));
        this.stateToShapeMap = this.func_235624_a_(4.0F, 3.0F, 16.0F, 0.0F, 14.0F, 16.0F);
        this.stateToCollisionShapeMap = this.func_235624_a_(4.0F, 3.0F, 24.0F, 0.0F, 24.0F, 24.0F);
    }

    private static VoxelShape func_235631_a_(VoxelShape p_235631_0_, WallHeight p_235631_1_, VoxelShape p_235631_2_, VoxelShape p_235631_3_)
    {
        if (p_235631_1_ == WallHeight.TALL)
        {
            return VoxelShapes.or(p_235631_0_, p_235631_3_);
        }
        else
        {
            return p_235631_1_ == WallHeight.LOW ? VoxelShapes.or(p_235631_0_, p_235631_2_) : p_235631_0_;
        }
    }

    private Map<BlockState, VoxelShape> func_235624_a_(float p_235624_1_, float p_235624_2_, float p_235624_3_, float p_235624_4_, float p_235624_5_, float p_235624_6_)
    {
        float f = 8.0F - p_235624_1_;
        float f1 = 8.0F + p_235624_1_;
        float f2 = 8.0F - p_235624_2_;
        float f3 = 8.0F + p_235624_2_;
        VoxelShape voxelshape = Block.makeCuboidShape((double)f, 0.0D, (double)f, (double)f1, (double)p_235624_3_, (double)f1);
        VoxelShape voxelshape1 = Block.makeCuboidShape((double)f2, (double)p_235624_4_, 0.0D, (double)f3, (double)p_235624_5_, (double)f3);
        VoxelShape voxelshape2 = Block.makeCuboidShape((double)f2, (double)p_235624_4_, (double)f2, (double)f3, (double)p_235624_5_, 16.0D);
        VoxelShape voxelshape3 = Block.makeCuboidShape(0.0D, (double)p_235624_4_, (double)f2, (double)f3, (double)p_235624_5_, (double)f3);
        VoxelShape voxelshape4 = Block.makeCuboidShape((double)f2, (double)p_235624_4_, (double)f2, 16.0D, (double)p_235624_5_, (double)f3);
        VoxelShape voxelshape5 = Block.makeCuboidShape((double)f2, (double)p_235624_4_, 0.0D, (double)f3, (double)p_235624_6_, (double)f3);
        VoxelShape voxelshape6 = Block.makeCuboidShape((double)f2, (double)p_235624_4_, (double)f2, (double)f3, (double)p_235624_6_, 16.0D);
        VoxelShape voxelshape7 = Block.makeCuboidShape(0.0D, (double)p_235624_4_, (double)f2, (double)f3, (double)p_235624_6_, (double)f3);
        VoxelShape voxelshape8 = Block.makeCuboidShape((double)f2, (double)p_235624_4_, (double)f2, 16.0D, (double)p_235624_6_, (double)f3);
        Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for (Boolean obool : UP.getAllowedValues())
        {
            for (WallHeight wallheight : WALL_HEIGHT_EAST.getAllowedValues())
            {
                for (WallHeight wallheight1 : WALL_HEIGHT_NORTH.getAllowedValues())
                {
                    for (WallHeight wallheight2 : WALL_HEIGHT_WEST.getAllowedValues())
                    {
                        for (WallHeight wallheight3 : WALL_HEIGHT_SOUTH.getAllowedValues())
                        {
                            VoxelShape voxelshape9 = VoxelShapes.empty();
                            voxelshape9 = func_235631_a_(voxelshape9, wallheight, voxelshape4, voxelshape8);
                            voxelshape9 = func_235631_a_(voxelshape9, wallheight2, voxelshape3, voxelshape7);
                            voxelshape9 = func_235631_a_(voxelshape9, wallheight1, voxelshape1, voxelshape5);
                            voxelshape9 = func_235631_a_(voxelshape9, wallheight3, voxelshape2, voxelshape6);

                            if (obool)
                            {
                                voxelshape9 = VoxelShapes.or(voxelshape9, voxelshape);
                            }

                            BlockState blockstate = this.getDefaultState().with(UP, obool).with(WALL_HEIGHT_EAST, wallheight).with(WALL_HEIGHT_WEST, wallheight2).with(WALL_HEIGHT_NORTH, wallheight1).with(WALL_HEIGHT_SOUTH, wallheight3);
                            builder.put(blockstate.with(WATERLOGGED, Boolean.valueOf(false)), voxelshape9);
                            builder.put(blockstate.with(WATERLOGGED, Boolean.valueOf(true)), voxelshape9);
                        }
                    }
                }
            }
        }

        return builder.build();
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return this.stateToShapeMap.get(state);
    }

    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return this.stateToCollisionShapeMap.get(state);
    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type)
    {
        return false;
    }

    private boolean func_220113_a(BlockState state, boolean p_220113_2_, Direction direction)
    {
        Block block = state.getBlock();
        boolean flag = block instanceof FenceGateBlock && FenceGateBlock.isParallel(state, direction);
        return state.isIn(BlockTags.WALLS) || !cannotAttach(block) && p_220113_2_ || block instanceof PaneBlock || flag;
    }

    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        IWorldReader iworldreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.east();
        BlockPos blockpos3 = blockpos.south();
        BlockPos blockpos4 = blockpos.west();
        BlockPos blockpos5 = blockpos.up();
        BlockState blockstate = iworldreader.getBlockState(blockpos1);
        BlockState blockstate1 = iworldreader.getBlockState(blockpos2);
        BlockState blockstate2 = iworldreader.getBlockState(blockpos3);
        BlockState blockstate3 = iworldreader.getBlockState(blockpos4);
        BlockState blockstate4 = iworldreader.getBlockState(blockpos5);
        boolean flag = this.func_220113_a(blockstate, blockstate.isSolidSide(iworldreader, blockpos1, Direction.SOUTH), Direction.SOUTH);
        boolean flag1 = this.func_220113_a(blockstate1, blockstate1.isSolidSide(iworldreader, blockpos2, Direction.WEST), Direction.WEST);
        boolean flag2 = this.func_220113_a(blockstate2, blockstate2.isSolidSide(iworldreader, blockpos3, Direction.NORTH), Direction.NORTH);
        boolean flag3 = this.func_220113_a(blockstate3, blockstate3.isSolidSide(iworldreader, blockpos4, Direction.EAST), Direction.EAST);
        BlockState blockstate5 = this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(fluidstate.getFluid() == Fluids.WATER));
        return this.func_235626_a_(iworldreader, blockstate5, blockpos5, blockstate4, flag, flag1, flag2, flag3);
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (stateIn.get(WATERLOGGED))
        {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        if (facing == Direction.DOWN)
        {
            return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        }
        else
        {
            return facing == Direction.UP ? this.func_235625_a_(worldIn, stateIn, facingPos, facingState) : this.func_235627_a_(worldIn, currentPos, stateIn, facingPos, facingState, facing);
        }
    }

    private static boolean func_235629_a_(BlockState p_235629_0_, Property<WallHeight> p_235629_1_)
    {
        return p_235629_0_.get(p_235629_1_) != WallHeight.NONE;
    }

    private static boolean func_235632_a_(VoxelShape p_235632_0_, VoxelShape p_235632_1_)
    {
        return !VoxelShapes.compare(p_235632_1_, p_235632_0_, IBooleanFunction.ONLY_FIRST);
    }

    private BlockState func_235625_a_(IWorldReader p_235625_1_, BlockState p_235625_2_, BlockPos p_235625_3_, BlockState p_235625_4_)
    {
        boolean flag = func_235629_a_(p_235625_2_, WALL_HEIGHT_NORTH);
        boolean flag1 = func_235629_a_(p_235625_2_, WALL_HEIGHT_EAST);
        boolean flag2 = func_235629_a_(p_235625_2_, WALL_HEIGHT_SOUTH);
        boolean flag3 = func_235629_a_(p_235625_2_, WALL_HEIGHT_WEST);
        return this.func_235626_a_(p_235625_1_, p_235625_2_, p_235625_3_, p_235625_4_, flag, flag1, flag2, flag3);
    }

    private BlockState func_235627_a_(IWorldReader p_235627_1_, BlockPos p_235627_2_, BlockState p_235627_3_, BlockPos p_235627_4_, BlockState p_235627_5_, Direction p_235627_6_)
    {
        Direction direction = p_235627_6_.getOpposite();
        boolean flag = p_235627_6_ == Direction.NORTH ? this.func_220113_a(p_235627_5_, p_235627_5_.isSolidSide(p_235627_1_, p_235627_4_, direction), direction) : func_235629_a_(p_235627_3_, WALL_HEIGHT_NORTH);
        boolean flag1 = p_235627_6_ == Direction.EAST ? this.func_220113_a(p_235627_5_, p_235627_5_.isSolidSide(p_235627_1_, p_235627_4_, direction), direction) : func_235629_a_(p_235627_3_, WALL_HEIGHT_EAST);
        boolean flag2 = p_235627_6_ == Direction.SOUTH ? this.func_220113_a(p_235627_5_, p_235627_5_.isSolidSide(p_235627_1_, p_235627_4_, direction), direction) : func_235629_a_(p_235627_3_, WALL_HEIGHT_SOUTH);
        boolean flag3 = p_235627_6_ == Direction.WEST ? this.func_220113_a(p_235627_5_, p_235627_5_.isSolidSide(p_235627_1_, p_235627_4_, direction), direction) : func_235629_a_(p_235627_3_, WALL_HEIGHT_WEST);
        BlockPos blockpos = p_235627_2_.up();
        BlockState blockstate = p_235627_1_.getBlockState(blockpos);
        return this.func_235626_a_(p_235627_1_, p_235627_3_, blockpos, blockstate, flag, flag1, flag2, flag3);
    }

    private BlockState func_235626_a_(IWorldReader p_235626_1_, BlockState p_235626_2_, BlockPos p_235626_3_, BlockState p_235626_4_, boolean p_235626_5_, boolean p_235626_6_, boolean p_235626_7_, boolean p_235626_8_)
    {
        VoxelShape voxelshape = p_235626_4_.getCollisionShape(p_235626_1_, p_235626_3_).project(Direction.DOWN);
        BlockState blockstate = this.func_235630_a_(p_235626_2_, p_235626_5_, p_235626_6_, p_235626_7_, p_235626_8_, voxelshape);
        return blockstate.with(UP, Boolean.valueOf(this.func_235628_a_(blockstate, p_235626_4_, voxelshape)));
    }

    private boolean func_235628_a_(BlockState p_235628_1_, BlockState p_235628_2_, VoxelShape p_235628_3_)
    {
        boolean flag = p_235628_2_.getBlock() instanceof WallBlock && p_235628_2_.get(UP);

        if (flag)
        {
            return true;
        }
        else
        {
            WallHeight wallheight = p_235628_1_.get(WALL_HEIGHT_NORTH);
            WallHeight wallheight1 = p_235628_1_.get(WALL_HEIGHT_SOUTH);
            WallHeight wallheight2 = p_235628_1_.get(WALL_HEIGHT_EAST);
            WallHeight wallheight3 = p_235628_1_.get(WALL_HEIGHT_WEST);
            boolean flag1 = wallheight1 == WallHeight.NONE;
            boolean flag2 = wallheight3 == WallHeight.NONE;
            boolean flag3 = wallheight2 == WallHeight.NONE;
            boolean flag4 = wallheight == WallHeight.NONE;
            boolean flag5 = flag4 && flag1 && flag2 && flag3 || flag4 != flag1 || flag2 != flag3;

            if (flag5)
            {
                return true;
            }
            else
            {
                boolean flag6 = wallheight == WallHeight.TALL && wallheight1 == WallHeight.TALL || wallheight2 == WallHeight.TALL && wallheight3 == WallHeight.TALL;

                if (flag6)
                {
                    return false;
                }
                else
                {
                    return p_235628_2_.getBlock().isIn(BlockTags.WALL_POST_OVERRIDE) || func_235632_a_(p_235628_3_, field_235619_i_);
                }
            }
        }
    }

    private BlockState func_235630_a_(BlockState p_235630_1_, boolean p_235630_2_, boolean p_235630_3_, boolean p_235630_4_, boolean p_235630_5_, VoxelShape p_235630_6_)
    {
        return p_235630_1_.with(WALL_HEIGHT_NORTH, this.func_235633_a_(p_235630_2_, p_235630_6_, field_235620_j_)).with(WALL_HEIGHT_EAST, this.func_235633_a_(p_235630_3_, p_235630_6_, field_235623_p_)).with(WALL_HEIGHT_SOUTH, this.func_235633_a_(p_235630_4_, p_235630_6_, field_235621_k_)).with(WALL_HEIGHT_WEST, this.func_235633_a_(p_235630_5_, p_235630_6_, field_235622_o_));
    }

    private WallHeight func_235633_a_(boolean p_235633_1_, VoxelShape p_235633_2_, VoxelShape p_235633_3_)
    {
        if (p_235633_1_)
        {
            return func_235632_a_(p_235633_2_, p_235633_3_) ? WallHeight.TALL : WallHeight.LOW;
        }
        else
        {
            return WallHeight.NONE;
        }
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos)
    {
        return !state.get(WATERLOGGED);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(UP, WALL_HEIGHT_NORTH, WALL_HEIGHT_EAST, WALL_HEIGHT_WEST, WALL_HEIGHT_SOUTH, WATERLOGGED);
    }

    public BlockState rotate(BlockState state, Rotation rot)
    {
        switch (rot)
        {
            case CLOCKWISE_180:
                return state.with(WALL_HEIGHT_NORTH, state.get(WALL_HEIGHT_SOUTH)).with(WALL_HEIGHT_EAST, state.get(WALL_HEIGHT_WEST)).with(WALL_HEIGHT_SOUTH, state.get(WALL_HEIGHT_NORTH)).with(WALL_HEIGHT_WEST, state.get(WALL_HEIGHT_EAST));

            case COUNTERCLOCKWISE_90:
                return state.with(WALL_HEIGHT_NORTH, state.get(WALL_HEIGHT_EAST)).with(WALL_HEIGHT_EAST, state.get(WALL_HEIGHT_SOUTH)).with(WALL_HEIGHT_SOUTH, state.get(WALL_HEIGHT_WEST)).with(WALL_HEIGHT_WEST, state.get(WALL_HEIGHT_NORTH));

            case CLOCKWISE_90:
                return state.with(WALL_HEIGHT_NORTH, state.get(WALL_HEIGHT_WEST)).with(WALL_HEIGHT_EAST, state.get(WALL_HEIGHT_NORTH)).with(WALL_HEIGHT_SOUTH, state.get(WALL_HEIGHT_EAST)).with(WALL_HEIGHT_WEST, state.get(WALL_HEIGHT_SOUTH));

            default:
                return state;
        }
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn)
    {
        switch (mirrorIn)
        {
            case LEFT_RIGHT:
                return state.with(WALL_HEIGHT_NORTH, state.get(WALL_HEIGHT_SOUTH)).with(WALL_HEIGHT_SOUTH, state.get(WALL_HEIGHT_NORTH));

            case FRONT_BACK:
                return state.with(WALL_HEIGHT_EAST, state.get(WALL_HEIGHT_WEST)).with(WALL_HEIGHT_WEST, state.get(WALL_HEIGHT_EAST));

            default:
                return super.mirror(state, mirrorIn);
        }
    }
}
