package essentialcraft.common.tile;

import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import essentialcraft.api.ApiCore;
import net.minecraftforge.common.config.Configuration;

public class TileMoonWell extends TileMRUGeneric {

	public static int cfgMaxMRU = ApiCore.GENERATOR_MAX_MRU_GENERIC;
	public static float cfgBalance = 1F;
	public static float mruGenerated = 60;

	public TileMoonWell() {
		super();
		mruStorage.setMaxMRU(cfgMaxMRU);
		mruStorage.setBalance(cfgBalance);
		slot0IsBoundGem = false;
	}

	public boolean canGenerateMRU() {
		int moonPhase = getWorld().provider.getMoonPhase(getWorld().getWorldTime());
		boolean night = !getWorld().isDaytime();
		return moonPhase != 4 && night && getWorld().canBlockSeeSky(pos.up());
	}

	@Override
	public void update() {
		super.update();
		if(getWorld().isBlockIndirectlyGettingPowered(pos) == 0) {
			int moonPhase = getWorld().provider.getMoonPhase(getWorld().getWorldTime());
			float moonFactor = 1.0F;
			switch(moonPhase) {
			case 0: {
				moonFactor = 1.0F;
				break;
			}
			case 1: {
				moonFactor = 0.75F;
				break;
			}
			case 7: {
				moonFactor = 0.75F;
				break;
			}
			case 2: {
				moonFactor = 0.5F;
				break;
			}
			case 6: {
				moonFactor = 0.5F;
				break;
			}
			case 3: {
				moonFactor = 0.25F;
				break;
			}
			case 5: {
				moonFactor = 0.25F;
				break;
			}
			case 4: {
				moonFactor = 0.0F;
				break;
			}
			}
			float mruGenerated = TileMoonWell.mruGenerated;
			mruGenerated *= moonFactor;
			float heightFactor = 1.0F;
			if(pos.getY() > 80)
				heightFactor = 0F;
			else {
				heightFactor = 1.0F - pos.getY()/80F;
				mruGenerated *= heightFactor;
			}
			if(mruGenerated > 0 && canGenerateMRU()) {
				mruStorage.addMRU((int)mruGenerated, true);
			}
		}
	}

	public static void setupConfig(Configuration cfg) {
		try {
			cfg.load();
			String[] cfgArrayString = cfg.getStringList("MoonWellSettings", "tileentities", new String[] {
					"Max MRU:"+ApiCore.GENERATOR_MAX_MRU_GENERIC,
					"Default balance:1.0",
					"Max MRU generated per tick:60"
			}, "");
			String dataString = "";

			for(int i = 0; i < cfgArrayString.length; ++i)
				dataString += "||" + cfgArrayString[i];

			DummyData[] data = DataStorage.parseData(dataString);

			cfgMaxMRU = Integer.parseInt(data[0].fieldValue);
			cfgBalance = Float.parseFloat(data[1].fieldValue);
			mruGenerated = Float.parseFloat(data[2].fieldValue);

			cfg.save();
		}
		catch(Exception e) {
			return;
		}
	}

	@Override
	public int[] getOutputSlots() {
		return new int[0];
	}
}
