package essentialcraft.common.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionShadeCorruption extends Potion {

	public PotionShadeCorruption(int p_i1573_1_, boolean p_i1573_2_,int p_i1573_3_)
	{
		super(p_i1573_2_, p_i1573_3_);
		this.setIconIndex(5, 2);
		this.setEffectiveness(0.25D);
		this.setPotionName("potion.shadeCorruption");
		this.setRegistryName("essentialcraft", "potion.shadeCorruption");
	}

	public boolean isUsable()
	{
		return true;
	}

	@Override
	public void performEffect(EntityLivingBase p_76394_1_, int p_76394_2_) {
		//welp idk wat shade iz
	}

	@Override
	public boolean isReady(int p_76397_1_, int p_76397_2_)
	{
		return p_76397_1_ % 20 == 0;
	}

	@Override
	public boolean hasStatusIcon()
	{
		return true;
	}

	@Override
	public int getStatusIconIndex()
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(rl);
		return super.getStatusIconIndex();
	}

	static final ResourceLocation rl = new ResourceLocation("essentialcraft", "textures/special/potions.png");
}
