package net.minecraft.entity.ai.goal;

import net.minecraft.entity.passive.ShoulderRidingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

public class LandOnOwnersShoulderGoal extends Goal
{
    private final ShoulderRidingEntity entity;
    private ServerPlayerEntity owner;
    private boolean isSittingOnShoulder;

    public LandOnOwnersShoulderGoal(ShoulderRidingEntity entityIn)
    {
        this.entity = entityIn;
    }

    public boolean shouldExecute()
    {
        ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)this.entity.getOwner();
        boolean flag = serverplayerentity != null && !serverplayerentity.isSpectator() && !serverplayerentity.abilities.isFlying && !serverplayerentity.isInWater();
        return !this.entity.func_233685_eM_() && flag && this.entity.canSitOnShoulder();
    }

    public boolean isPreemptible()
    {
        return !this.isSittingOnShoulder;
    }

    public void startExecuting()
    {
        this.owner = (ServerPlayerEntity)this.entity.getOwner();
        this.isSittingOnShoulder = false;
    }

    public void tick()
    {
        if (!this.isSittingOnShoulder && !this.entity.func_233684_eK_() && !this.entity.getLeashed())
        {
            if (this.entity.getBoundingBox().intersects(this.owner.getBoundingBox()))
            {
                this.isSittingOnShoulder = this.entity.func_213439_d(this.owner);
            }
        }
    }
}