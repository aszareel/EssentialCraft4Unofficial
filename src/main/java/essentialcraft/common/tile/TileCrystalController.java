package essentialcraft.common.tile;

import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import essentialcraft.api.ApiCore;
import essentialcraft.common.item.ItemEssence;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.config.Configuration;

public class TileCrystalController extends TileMRUGeneric {

	public static int cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC;
	public static int mruUsage = 100;
	public static int chanceToUseMRU = 20;
	public static float mutateModifier = 0.001F;

	public TileCrystalController() {
		super();
		mruStorage.setMaxMRU(cfgMaxMRU);
		setSlotsNum(2);
	}

	@Override
	public void update() {
		super.update();
		mruStorage.update(getPos(), getWorld(), getStackInSlot(0));

		if(getWorld().isBlockIndirectlyGettingPowered(pos) == 0) {
			if(!getWorld().isRemote && getWorld().rand.nextInt(chanceToUseMRU) == 0 && mruStorage.getMRU() >= mruUsage) {
				mruStorage.extractMRU(mruUsage, true);
			}
		}
		spawnParticles();
		if(!getWorld().isRemote && getWorld().isBlockIndirectlyGettingPowered(pos) == 0)
			mutateToElement();
	}

	public void spawnParticles() {
		if(world.isRemote && mruStorage.getMRU() > 0 && getCrystal() != null) {
			for(int o = 0; o < 2; ++o) {
				getWorld().spawnParticle(EnumParticleTypes.REDSTONE, pos.getX()+0.3D + getWorld().rand.nextDouble()/2, pos.getY()+0.3F + (float)o/2, pos.getZ()+0.3D + getWorld().rand.nextDouble()/2D, -1.0D, 1.0D, 0.0D);
			}
		}
	}

	public void mutateToElement() {
		if(!getStackInSlot(1).isEmpty() && getStackInSlot(1).getItem() instanceof ItemEssence && mruStorage.getMRU() > mruUsage*10 && getCrystal() != null && getCrystal().size < 100) {
			ItemStack e = getStackInSlot(1);
			TileElementalCrystal c = getCrystal();
			int rarity = (int)((float)e.getItemDamage() / 4);
			float chance = mutateModifier * (rarity + 1);
			if(getWorld().rand.nextFloat() <= chance) {
				mruStorage.extractMRU(mruUsage*10, true);
				int type = e.getItemDamage()%4;
				c.mutate(type, getWorld().rand.nextInt((rarity + 1) * 2));
				decrStackSize(1, 1);
			}
		}
	}

	public TileElementalCrystal getCrystal() {
		TileElementalCrystal t = null;
		if(hasCrystalOnFront())
			t = (TileElementalCrystal)getWorld().getTileEntity(pos.east());
		if(hasCrystalOnBack())
			t = (TileElementalCrystal)getWorld().getTileEntity(pos.west());
		if(hasCrystalOnLeft())
			t = (TileElementalCrystal)getWorld().getTileEntity(pos.south());
		if(hasCrystalOnRight())
			t = (TileElementalCrystal)getWorld().getTileEntity(pos.north());
		return t;
	}

	public boolean hasCrystalOnFront() {
		TileEntity t = getWorld().getTileEntity(pos.east());
		return t != null && t instanceof TileElementalCrystal;
	}

	public boolean hasCrystalOnBack() {
		TileEntity t = getWorld().getTileEntity(pos.west());
		return t != null && t instanceof TileElementalCrystal;
	}

	public boolean hasCrystalOnLeft() {
		TileEntity t = getWorld().getTileEntity(pos.south());
		return t != null && t instanceof TileElementalCrystal;
	}

	public boolean hasCrystalOnRight() {
		TileEntity t = getWorld().getTileEntity(pos.north());
		return t != null && t instanceof TileElementalCrystal;
	}

	public static void setupConfig(Configuration cfg) {
		try {
			cfg.load();
			String[] cfgArrayString = cfg.getStringList("CrystalControllerSettings", "tileentities", new String[] {
					"Max MRU:" + ApiCore.DEVICE_MAX_MRU_GENERIC,
					"MRU Usage:100",
					"Chance to NOT use MRU(do not set to 0!):20",
					"Crystal mutation chance modifier:0.001"
			}, "");
			String dataString="";

			for(int i = 0; i < cfgArrayString.length; ++i)
				dataString += "||" + cfgArrayString[i];

			DummyData[] data = DataStorage.parseData(dataString);

			mutateModifier = Float.parseFloat(data[3].fieldValue);
			mruUsage = Integer.parseInt(data[1].fieldValue);
			chanceToUseMRU = Integer.parseInt(data[2].fieldValue);
			cfgMaxMRU = Integer.parseInt(data[0].fieldValue);

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
