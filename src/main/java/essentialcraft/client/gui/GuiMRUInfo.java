package essentialcraft.client.gui;

import DummyCore.Client.GuiCommon;
import essentialcraft.client.gui.element.GuiBalanceState;
import essentialcraft.client.gui.element.GuiMRUState;
import essentialcraft.client.gui.element.GuiMRUStorage;
import essentialcraft.client.gui.element.GuiResistanceState;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class GuiMRUInfo extends GuiCommon{

	public GuiMRUInfo(Container c, TileEntity tile) {
		super(c,tile);
		this.elementList.add(new GuiMRUStorage(7, 4, tile));
		this.elementList.add(new GuiMRUState(25, 58, tile, 0));
		this.elementList.add(new GuiBalanceState(25, 40, tile));
		this.elementList.add(new GuiResistanceState(25, 22, tile));
	}
}
