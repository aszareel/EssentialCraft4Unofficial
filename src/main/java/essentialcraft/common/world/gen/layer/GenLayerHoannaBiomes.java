package essentialcraft.common.world.gen.layer;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import essentialcraft.common.registry.BiomeRegistry;
import net.minecraft.init.Biomes;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class GenLayerHoannaBiomes extends GenLayer {
	static final List<BiomeEntry> HOANNA_BIOMES = Lists.newArrayList(
			new BiomeEntry(BiomeRegistry.dreadlands, 30),
			new BiomeEntry(BiomeRegistry.chaosCorruption, 30),
			new BiomeEntry(BiomeRegistry.frozenCorruption, 30),
			new BiomeEntry(BiomeRegistry.magicCorruption, 30),
			new BiomeEntry(BiomeRegistry.shadowCorruption, 1)
			);
	private List<BiomeEntry>[] biomes = new ArrayList[BiomeType.values().length];
	private final ChunkGeneratorSettings settings;

	public GenLayerHoannaBiomes(long p_i45560_1_, GenLayer p_i45560_3_, WorldType p_i45560_4_, ChunkGeneratorSettings p_i45560_5_) {
		super(p_i45560_1_);
		this.parent = p_i45560_3_;

		for(BiomeType type : BiomeType.values()) {
			ImmutableList<BiomeEntry> biomesToAdd = BiomeManager.getBiomes(type);
			int idx = type.ordinal();

			if(biomes[idx] == null)
				biomes[idx] = new ArrayList<BiomeEntry>();
			if(biomesToAdd != null) {
				biomes[idx].addAll(biomesToAdd);
				biomes[idx].addAll(HOANNA_BIOMES);
			}
		}

		int desertIdx = BiomeType.DESERT.ordinal();

		biomes[desertIdx].add(new BiomeEntry(BiomeRegistry.desert, 30));
		biomes[desertIdx].add(new BiomeEntry(Biomes.SAVANNA, 20));
		biomes[desertIdx].add(new BiomeEntry(Biomes.PLAINS, 10));

		if(p_i45560_4_ == WorldType.DEFAULT_1_1) {
			biomes[desertIdx].clear();
			biomes[desertIdx].add(new BiomeEntry(BiomeRegistry.desert, 10));
			biomes[desertIdx].add(new BiomeEntry(Biomes.FOREST, 10));
			biomes[desertIdx].add(new BiomeEntry(Biomes.EXTREME_HILLS, 10));
			biomes[desertIdx].add(new BiomeEntry(Biomes.SWAMPLAND, 10));
			biomes[desertIdx].add(new BiomeEntry(Biomes.PLAINS, 10));
			biomes[desertIdx].add(new BiomeEntry(Biomes.TAIGA, 10));
			this.settings = null;
		}
		else {
			this.settings = p_i45560_5_;
		}
	}

	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		int[] aint = this.parent.getInts(areaX, areaY, areaWidth, areaHeight);
		int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);

		for (int i = 0; i < areaHeight; ++i) {
			for (int j = 0; j < areaWidth; ++j) {
				this.initChunkSeed(j + areaX, i + areaY);
				int k = aint[j + i * areaWidth];
				int l = (k & 3840) >> 8;
			k = k & -3841;

			if (this.settings != null && this.settings.fixedBiome >= 0) {
				aint1[j + i * areaWidth] = this.settings.fixedBiome;
			}
			else if (isBiomeOceanic(k)) {
				aint1[j + i * areaWidth] = k;
			}
			else if (k == Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND)) {
				aint1[j + i * areaWidth] = k;
			}
			else if (k == 1) {
				if (l > 0) {
					if (this.nextInt(3) == 0) {
						aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.MESA_CLEAR_ROCK);
					}
					else {
						aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.MESA_ROCK);
					}
				}
				else {
					aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(BiomeType.DESERT).biome);
				}
			}
			else if (k == 2) {
				if (l > 0) {
					aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.JUNGLE);
				}
				else {
					aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(BiomeType.WARM).biome);
				}
			}
			else if (k == 3) {
				if (l > 0) {
					aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.REDWOOD_TAIGA);
				}
				else {
					aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(BiomeType.COOL).biome);
				}
			}
			else if (k == 4) {
				aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(BiomeType.ICY).biome);
			}
			else {
				aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND);
			}
			}
		}

		return aint1;
	}

	protected BiomeEntry getWeightedBiomeEntry(BiomeType type) {
		List<BiomeEntry> biomeList = biomes[type.ordinal()];
		int totalWeight = WeightedRandom.getTotalWeight(biomeList);
		int weight = BiomeManager.isTypeListModded(type)?nextInt(totalWeight):nextInt(totalWeight / 10) * 10;
		return WeightedRandom.getRandomItem(biomeList, weight);
	}
}
