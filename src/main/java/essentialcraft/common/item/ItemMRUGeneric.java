package essentialcraft.common.item;

import java.util.List;

import essentialcraft.api.IMRUHandlerItem;
import essentialcraft.common.capabilities.mru.CapabilityMRUHandler;
import essentialcraft.common.capabilities.mru.MRUItemStorage;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMRUGeneric extends Item {

	public static Capability<IMRUHandlerItem> MRU_HANDLER_ITEM_CAPABILITY = CapabilityMRUHandler.MRU_HANDLER_ITEM_CAPABILITY;
	public int maxMRU = 5000;

	public ItemMRUGeneric() {
		super();
	}

	public ItemMRUGeneric setMaxMRU(int maxMRU) {
		this.maxMRU = maxMRU;
		return this;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add(par1ItemStack.getCapability(MRU_HANDLER_ITEM_CAPABILITY, null).getMRU() + "/" + par1ItemStack.getCapability(MRU_HANDLER_ITEM_CAPABILITY, null).getMaxMRU() + " MRU");
	}

	@Override
	public void getSubItems(CreativeTabs par2CreativeTabs, NonNullList<ItemStack> par3List) {
		if(this.isInCreativeTab(par2CreativeTabs)) {
			ItemStack min = new ItemStack(this, 1, 0);
			ItemStack max = new ItemStack(this, 1, 0);
			min.getCapability(MRU_HANDLER_ITEM_CAPABILITY, null).setMRU(0);
			max.getCapability(MRU_HANDLER_ITEM_CAPABILITY, null).setMRU(maxMRU);
			par3List.add(min);
			par3List.add(max);
		}
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return !oldStack.getItem().equals(newStack.getItem());
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new MRUItemStorage(stack, maxMRU);
	}
}
