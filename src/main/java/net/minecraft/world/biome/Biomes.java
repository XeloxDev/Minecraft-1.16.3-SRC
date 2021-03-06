package net.minecraft.world.biome;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public abstract class Biomes
{
    public static final RegistryKey<Biome> OCEAN = func_242548_a("ocean");
    public static final RegistryKey<Biome> PLAINS = func_242548_a("plains");
    public static final RegistryKey<Biome> DESERT = func_242548_a("desert");
    public static final RegistryKey<Biome> MOUNTAINS = func_242548_a("mountains");
    public static final RegistryKey<Biome> FOREST = func_242548_a("forest");
    public static final RegistryKey<Biome> TAIGA = func_242548_a("taiga");
    public static final RegistryKey<Biome> SWAMP = func_242548_a("swamp");
    public static final RegistryKey<Biome> RIVER = func_242548_a("river");
    public static final RegistryKey<Biome> NETHER_WASTES = func_242548_a("nether_wastes");
    public static final RegistryKey<Biome> THE_END = func_242548_a("the_end");
    public static final RegistryKey<Biome> FROZEN_OCEAN = func_242548_a("frozen_ocean");
    public static final RegistryKey<Biome> FROZEN_RIVER = func_242548_a("frozen_river");
    public static final RegistryKey<Biome> SNOWY_TUNDRA = func_242548_a("snowy_tundra");
    public static final RegistryKey<Biome> SNOWY_MOUNTAINS = func_242548_a("snowy_mountains");
    public static final RegistryKey<Biome> MUSHROOM_FIELDS = func_242548_a("mushroom_fields");
    public static final RegistryKey<Biome> MUSHROOM_FIELD_SHORE = func_242548_a("mushroom_field_shore");
    public static final RegistryKey<Biome> BEACH = func_242548_a("beach");
    public static final RegistryKey<Biome> DESERT_HILLS = func_242548_a("desert_hills");
    public static final RegistryKey<Biome> WOODED_HILLS = func_242548_a("wooded_hills");
    public static final RegistryKey<Biome> TAIGA_HILLS = func_242548_a("taiga_hills");
    public static final RegistryKey<Biome> MOUNTAIN_EDGE = func_242548_a("mountain_edge");
    public static final RegistryKey<Biome> JUNGLE = func_242548_a("jungle");
    public static final RegistryKey<Biome> JUNGLE_HILLS = func_242548_a("jungle_hills");
    public static final RegistryKey<Biome> JUNGLE_EDGE = func_242548_a("jungle_edge");
    public static final RegistryKey<Biome> DEEP_OCEAN = func_242548_a("deep_ocean");
    public static final RegistryKey<Biome> STONE_SHORE = func_242548_a("stone_shore");
    public static final RegistryKey<Biome> SNOWY_BEACH = func_242548_a("snowy_beach");
    public static final RegistryKey<Biome> BIRCH_FOREST = func_242548_a("birch_forest");
    public static final RegistryKey<Biome> BIRCH_FOREST_HILLS = func_242548_a("birch_forest_hills");
    public static final RegistryKey<Biome> DARK_FOREST = func_242548_a("dark_forest");
    public static final RegistryKey<Biome> SNOWY_TAIGA = func_242548_a("snowy_taiga");
    public static final RegistryKey<Biome> SNOWY_TAIGA_HILLS = func_242548_a("snowy_taiga_hills");
    public static final RegistryKey<Biome> GIANT_TREE_TAIGA = func_242548_a("giant_tree_taiga");
    public static final RegistryKey<Biome> GIANT_TREE_TAIGA_HILLS = func_242548_a("giant_tree_taiga_hills");
    public static final RegistryKey<Biome> WOODED_MOUNTAINS = func_242548_a("wooded_mountains");
    public static final RegistryKey<Biome> SAVANNA = func_242548_a("savanna");
    public static final RegistryKey<Biome> SAVANNA_PLATEAU = func_242548_a("savanna_plateau");
    public static final RegistryKey<Biome> BADLANDS = func_242548_a("badlands");
    public static final RegistryKey<Biome> WOODED_BADLANDS_PLATEAU = func_242548_a("wooded_badlands_plateau");
    public static final RegistryKey<Biome> BADLANDS_PLATEAU = func_242548_a("badlands_plateau");
    public static final RegistryKey<Biome> SMALL_END_ISLANDS = func_242548_a("small_end_islands");
    public static final RegistryKey<Biome> END_MIDLANDS = func_242548_a("end_midlands");
    public static final RegistryKey<Biome> END_HIGHLANDS = func_242548_a("end_highlands");
    public static final RegistryKey<Biome> END_BARRENS = func_242548_a("end_barrens");
    public static final RegistryKey<Biome> WARM_OCEAN = func_242548_a("warm_ocean");
    public static final RegistryKey<Biome> LUKEWARM_OCEAN = func_242548_a("lukewarm_ocean");
    public static final RegistryKey<Biome> COLD_OCEAN = func_242548_a("cold_ocean");
    public static final RegistryKey<Biome> DEEP_WARM_OCEAN = func_242548_a("deep_warm_ocean");
    public static final RegistryKey<Biome> DEEP_LUKEWARM_OCEAN = func_242548_a("deep_lukewarm_ocean");
    public static final RegistryKey<Biome> DEEP_COLD_OCEAN = func_242548_a("deep_cold_ocean");
    public static final RegistryKey<Biome> DEEP_FROZEN_OCEAN = func_242548_a("deep_frozen_ocean");
    public static final RegistryKey<Biome> THE_VOID = func_242548_a("the_void");
    public static final RegistryKey<Biome> SUNFLOWER_PLAINS = func_242548_a("sunflower_plains");
    public static final RegistryKey<Biome> DESERT_LAKES = func_242548_a("desert_lakes");
    public static final RegistryKey<Biome> GRAVELLY_MOUNTAINS = func_242548_a("gravelly_mountains");
    public static final RegistryKey<Biome> FLOWER_FOREST = func_242548_a("flower_forest");
    public static final RegistryKey<Biome> TAIGA_MOUNTAINS = func_242548_a("taiga_mountains");
    public static final RegistryKey<Biome> SWAMP_HILLS = func_242548_a("swamp_hills");
    public static final RegistryKey<Biome> ICE_SPIKES = func_242548_a("ice_spikes");
    public static final RegistryKey<Biome> MODIFIED_JUNGLE = func_242548_a("modified_jungle");
    public static final RegistryKey<Biome> MODIFIED_JUNGLE_EDGE = func_242548_a("modified_jungle_edge");
    public static final RegistryKey<Biome> TALL_BIRCH_FOREST = func_242548_a("tall_birch_forest");
    public static final RegistryKey<Biome> TALL_BIRCH_HILLS = func_242548_a("tall_birch_hills");
    public static final RegistryKey<Biome> DARK_FOREST_HILLS = func_242548_a("dark_forest_hills");
    public static final RegistryKey<Biome> SNOWY_TAIGA_MOUNTAINS = func_242548_a("snowy_taiga_mountains");
    public static final RegistryKey<Biome> GIANT_SPRUCE_TAIGA = func_242548_a("giant_spruce_taiga");
    public static final RegistryKey<Biome> GIANT_SPRUCE_TAIGA_HILLS = func_242548_a("giant_spruce_taiga_hills");
    public static final RegistryKey<Biome> MODIFIED_GRAVELLY_MOUNTAINS = func_242548_a("modified_gravelly_mountains");
    public static final RegistryKey<Biome> SHATTERED_SAVANNA = func_242548_a("shattered_savanna");
    public static final RegistryKey<Biome> SHATTERED_SAVANNA_PLATEAU = func_242548_a("shattered_savanna_plateau");
    public static final RegistryKey<Biome> ERODED_BADLANDS = func_242548_a("eroded_badlands");
    public static final RegistryKey<Biome> MODIFIED_WOODED_BADLANDS_PLATEAU = func_242548_a("modified_wooded_badlands_plateau");
    public static final RegistryKey<Biome> MODIFIED_BADLANDS_PLATEAU = func_242548_a("modified_badlands_plateau");
    public static final RegistryKey<Biome> BAMBOO_JUNGLE = func_242548_a("bamboo_jungle");
    public static final RegistryKey<Biome> BAMBOO_JUNGLE_HILLS = func_242548_a("bamboo_jungle_hills");
    public static final RegistryKey<Biome> SOUL_SAND_VALLEY = func_242548_a("soul_sand_valley");
    public static final RegistryKey<Biome> CRIMSON_FOREST = func_242548_a("crimson_forest");
    public static final RegistryKey<Biome> WARPED_FOREST = func_242548_a("warped_forest");
    public static final RegistryKey<Biome> BASALT_DELTAS = func_242548_a("basalt_deltas");

    private static RegistryKey<Biome> func_242548_a(String p_242548_0_)
    {
        return RegistryKey.func_240903_a_(Registry.BIOME_KEY, new ResourceLocation(p_242548_0_));
    }
}
