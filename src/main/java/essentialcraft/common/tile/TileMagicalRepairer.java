package essentialcraft.common.tile;

import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import essentialcraft.api.ApiCore;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.config.Configuration;

public class TileMagicalRepairer extends TileMRUGeneric {

	public static int cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC;
	public static boolean generatesCorruption = true;
	public static int genCorruption = 3;
	public static int mruUsage = 70;

	public TileMagicalRepairer() {
		super();
		mruStorage.setMaxMRU(cfgMaxMRU);
		setSlotsNum(2);
	}

	@Override
	public void update() {
		super.update();
		mruStorage.update(getPos(), getWorld(), getStackInSlot(0));
		if(getWorld().isBlockIndirectlyGettingPowered(pos) == 0)
			repare();
		spawnParticles();
	}

	public void repare() {
		ItemStack repareItem = getStackInSlot(1);
		if(canRepare(repareItem)) {
			if(mruStorage.getMRU() >= mruUsage) {
				mruStorage.extractMRU(mruUsage, true);
				repareItem.setItemDamage(repareItem.getItemDamage() - 1);
				if(generatesCorruption)
					ECUtils.increaseCorruptionAt(getWorld(), pos, getWorld().rand.nextInt(genCorruption));
			}
		}
	}

	public boolean canRepare(ItemStack s) {
		return !s.isEmpty() && s.getItemDamage() != 0 && s.getItem().isRepairable() && mruStorage.getMRU() >= mruUsage;
	}

	public void spawnParticles() {
		if(world.isRemote && canRepare(getStackInSlot(1)) && mruStorage.getMRU() > 0) {
			for(int o = 0; o < 10; ++o) {
				getWorld().spawnParticle(EnumParticleTypes.REDSTONE, pos.getX()+0.25D + getWorld().rand.nextDouble()/2.2D, pos.getY()+0.25D+(float)o/20, pos.getZ()+0.25D + getWorld().rand.nextDouble()/2.2D, 1.0D, 0.0D, 1.0D);
			}
		}
	}

	public static void setupConfig(Configuration cfg) {
		try {
			cfg.load();
			String[] cfgArrayString = cfg.getStringList("MagicalRepairerSettings", "tileentities", new String[] {
					"Max MRU:" + ApiCore.DEVICE_MAX_MRU_GENERIC,
					"MRU Usage:70",
					"Can this device actually generate corruption:true",
					"The amount of corruption generated each tick(do not set to 0!):3"
			}, "");
			String dataString = "";

			for(int i = 0; i < cfgArrayString.length; ++i)
				dataString += "||" + cfgArrayString[i];

			DummyData[] data = DataStorage.parseData(dataString);

			mruUsage = Integer.parseInt(data[1].fieldValue);
			cfgMaxMRU = Integer.parseInt(data[0].fieldValue);
			generatesCorruption = Boolean.parseBoolean(data[2].fieldValue);
			genCorruption = Integer.parseInt(data[3].fieldValue);

			cfg.save();
		}
		catch(Exception e) {
			return;
		}
	}

	@Override
	public int[] getOutputSlots() {
		return new int[] {1};
	}
}
