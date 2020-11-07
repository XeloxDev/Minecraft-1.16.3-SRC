package net.minecraft.entity.merchant.villager;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.util.registry.Registry;

public class VillagerData
{
    private static final int[] LEVEL_EXPERIENCE_AMOUNTS = new int[] {0, 10, 70, 150, 250};
    public static final Codec<VillagerData> CODEC = RecordCodecBuilder.create((dataInstance) ->
    {
        return dataInstance.group(Registry.VILLAGER_TYPE.fieldOf("type").orElseGet(() -> {
            return VillagerType.PLAINS;
        }).forGetter((data) -> {
            return data.type;
        }), Registry.VILLAGER_PROFESSION.fieldOf("profession").orElseGet(() -> {
            return VillagerProfession.NONE;
        }).forGetter((data) -> {
            return data.profession;
        }), Codec.INT.fieldOf("level").orElse(1).forGetter((data) -> {
            return data.level;
        })).apply(dataInstance, VillagerData::new);
    });
    private final VillagerType type;
    private final VillagerProfession profession;
    private final int level;

    public VillagerData(VillagerType type, VillagerProfession profession, int level)
    {
        this.type = type;
        this.profession = profession;
        this.level = Math.max(1, level);
    }

    public VillagerType getType()
    {
        return this.type;
    }

    public VillagerProfession getProfession()
    {
        return this.profession;
    }

    public int getLevel()
    {
        return this.level;
    }

    public VillagerData withType(VillagerType typeIn)
    {
        return new VillagerData(typeIn, this.profession, this.level);
    }

    public VillagerData withProfession(VillagerProfession professionIn)
    {
        return new VillagerData(this.type, professionIn, this.level);
    }

    public VillagerData withLevel(int levelIn)
    {
        return new VillagerData(this.type, this.profession, levelIn);
    }

    public static int func_221133_b(int p_221133_0_)
    {
        return canLevelUp(p_221133_0_) ? LEVEL_EXPERIENCE_AMOUNTS[p_221133_0_ - 1] : 0;
    }

    public static int func_221127_c(int p_221127_0_)
    {
        return canLevelUp(p_221127_0_) ? LEVEL_EXPERIENCE_AMOUNTS[p_221127_0_] : 0;
    }

    public static boolean canLevelUp(int level)
    {
        return level >= 1 && level < 5;
    }
}
