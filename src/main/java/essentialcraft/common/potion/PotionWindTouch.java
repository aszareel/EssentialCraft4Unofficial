package essentialcraft.common.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionWindTouch extends Potion{

	public PotionWindTouch(int p_i1573_1_, boolean p_i1573_2_,int p_i1573_3_)
	{
		super(p_i1573_2_, p_i1573_3_);
		this.setIconIndex(6, 1);
		this.setEffectiveness(0.25D);
		this.setPotionName("potion.windTouch");
		this.setRegistryName("essentialcraft", "potion.windTouch");
	}

	public boolean isUsable()
	{
		return true;
	}

	@Override
	public void performEffect(EntityLivingBase p_76394_1_, int p_76394_2_)
	{

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

	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc)
	{
		mc.fontRenderer.drawStringWithShadow(effect.getAmplifier()+1+"", x+19, y+19, 0xffffff);
	}

	static final ResourceLocation rl = new ResourceLocation("essentialcraft", "textures/special/potions.png");
}
