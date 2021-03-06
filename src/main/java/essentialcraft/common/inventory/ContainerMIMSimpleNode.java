package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import essentialcraft.common.item.ItemFilter;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerMIMSimpleNode extends ContainerInventory {

	public ContainerMIMSimpleNode(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		addSlotToContainer(new SlotGeneric(inv, 0, 80, 12) {
			@Override
			public boolean isItemValid(ItemStack stk) {
				return stk.getItem() instanceof ItemFilter;
			}
		});
		this.setupPlayerInventory();
	}
}
