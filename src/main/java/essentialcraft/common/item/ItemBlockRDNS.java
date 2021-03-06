package essentialcraft.common.item;

import essentialcraft.common.block.BlockRedstoneDeviceNotSided;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockRDNS extends ItemBlock{

	public ItemBlockRDNS(Block p_i45328_1_) {
		super(p_i45328_1_);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	public int getMetadata(int par1)
	{
		return par1;
	}

	@Override
	public String getUnlocalizedName(ItemStack p_77667_1_) {
		return super.getUnlocalizedName(p_77667_1_)+"."+BlockRedstoneDeviceNotSided.names[p_77667_1_.getItemDamage()];
	}
}
