package net.minecraft.item;

import java.util.Optional;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompassItem extends Item implements IVanishable
{
    private static final Logger field_234666_a_ = LogManager.getLogger();

    public CompassItem(Item.Properties builder)
    {
        super(builder);
    }

    public static boolean func_234670_d_(ItemStack p_234670_0_)
    {
        CompoundNBT compoundnbt = p_234670_0_.getTag();
        return compoundnbt != null && (compoundnbt.contains("LodestoneDimension") || compoundnbt.contains("LodestonePos"));
    }

    public boolean hasEffect(ItemStack stack)
    {
        return func_234670_d_(stack) || super.hasEffect(stack);
    }

    public static Optional<RegistryKey<World>> func_234667_a_(CompoundNBT p_234667_0_)
    {
        return World.CODEC.parse(NBTDynamicOps.INSTANCE, p_234667_0_.get("LodestoneDimension")).result();
    }

    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if (!worldIn.isRemote)
        {
            if (func_234670_d_(stack))
            {
                CompoundNBT compoundnbt = stack.getOrCreateTag();

                if (compoundnbt.contains("LodestoneTracked") && !compoundnbt.getBoolean("LodestoneTracked"))
                {
                    return;
                }

                Optional<RegistryKey<World>> optional = func_234667_a_(compoundnbt);

                if (optional.isPresent() && optional.get() == worldIn.getDimensionKey() && compoundnbt.contains("LodestonePos") && !((ServerWorld)worldIn).getPointOfInterestManager().func_234135_a_(PointOfInterestType.LODESTONE, NBTUtil.readBlockPos(compoundnbt.getCompound("LodestonePos"))))
                {
                    compoundnbt.remove("LodestonePos");
                }
            }
        }
    }

    public ActionResultType onItemUse(ItemUseContext context)
    {
        BlockPos blockpos = context.getPos();
        World world = context.getWorld();

        if (!world.getBlockState(blockpos).isIn(Blocks.LODESTONE))
        {
            return super.onItemUse(context);
        }
        else
        {
            world.playSound((PlayerEntity)null, blockpos, SoundEvents.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.PLAYERS, 1.0F, 1.0F);
            PlayerEntity playerentity = context.getPlayer();
            ItemStack itemstack = context.getItem();
            boolean flag = !playerentity.abilities.isCreativeMode && itemstack.getCount() == 1;

            if (flag)
            {
                this.func_234669_a_(world.getDimensionKey(), blockpos, itemstack.getOrCreateTag());
            }
            else
            {
                ItemStack itemstack1 = new ItemStack(Items.COMPASS, 1);
                CompoundNBT compoundnbt = itemstack.hasTag() ? itemstack.getTag().copy() : new CompoundNBT();
                itemstack1.setTag(compoundnbt);

                if (!playerentity.abilities.isCreativeMode)
                {
                    itemstack.shrink(1);
                }

                this.func_234669_a_(world.getDimensionKey(), blockpos, compoundnbt);

                if (!playerentity.inventory.addItemStackToInventory(itemstack1))
                {
                    playerentity.dropItem(itemstack1, false);
                }
            }

            return ActionResultType.func_233537_a_(world.isRemote);
        }
    }

    private void func_234669_a_(RegistryKey<World> p_234669_1_, BlockPos p_234669_2_, CompoundNBT p_234669_3_)
    {
        p_234669_3_.put("LodestonePos", NBTUtil.writeBlockPos(p_234669_2_));
        World.CODEC.encodeStart(NBTDynamicOps.INSTANCE, p_234669_1_).resultOrPartial(field_234666_a_::error).ifPresent((p_234668_1_) ->
        {
            p_234669_3_.put("LodestoneDimension", p_234668_1_);
        });
        p_234669_3_.putBoolean("LodestoneTracked", true);
    }

    public String getTranslationKey(ItemStack stack)
    {
        return func_234670_d_(stack) ? "item.minecraft.lodestone_compass" : super.getTranslationKey(stack);
    }
}
