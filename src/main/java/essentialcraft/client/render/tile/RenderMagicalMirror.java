package essentialcraft.client.render.tile;

import DummyCore.Client.AdvancedModelLoader;
import DummyCore.Client.IModelCustom;
import DummyCore.Utils.DrawUtils;
import essentialcraft.common.tile.TileMagicalMirror;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMagicalMirror extends TileEntitySpecialRenderer<TileMagicalMirror>
{
	public static final ResourceLocation textures = new ResourceLocation("essentialcraft:textures/models/armtextures.png");
	public static final ResourceLocation glass = new ResourceLocation("essentialcraft:textures/models/mirror.png");
	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:models/block/mirror.obj"));

	public void doRender(TileMagicalMirror tile, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		RenderHelper.disableStandardItemLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)p_76986_2_+0.5F, (float)p_76986_4_-0.25F, (float)p_76986_6_+0.5F);
		float timeIndex = (tile.getWorld().getWorldTime()+p_76986_8_)%120;
		float yIndex = 1.0F;
		if(timeIndex <= 60)
			yIndex = timeIndex/240F;
		else
			yIndex = 0.5F-timeIndex/240F;
		GlStateManager.translate(0, yIndex-0.25F, 0);
		if(tile.inventoryPos != null) {
			double d0 = tile.inventoryPos.getX() - tile.getPos().getX();
			double d1 = tile.inventoryPos.getY() - tile.getPos().getY() - yIndex;
			double d2 = tile.inventoryPos.getZ() - tile.getPos().getZ();
			double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
			float f = -(float)(Math.atan2(d2, d0) * 180.0D / Math.PI)-90;
			float f1 = -(float)-(Math.atan2(d1, d3) * 180.0D / Math.PI);

			GlStateManager.rotate(f, 0, 1, 0);
			GlStateManager.rotate(f1, 1, 0, 0);
		}


		this.bindTexture(textures);
		model.renderPart("pCube2");
		this.bindTexture(glass);
		if(tile.pulsing)
		{
			timeIndex = Minecraft.getMinecraft().world.getWorldTime()%20;
			float colorIndex = 1.0F;
			if(timeIndex <= 10)
			{
				colorIndex = 1.0F - timeIndex/10;
			}else
			{
				colorIndex = (timeIndex-10)/10;
			}
			GlStateManager.color(1, colorIndex, 1);
		}
		model.renderPart("pPlane1");
		GlStateManager.rotate(180, 0, 1, 0);
		model.renderPart("pPlane1");
		GlStateManager.color(1, 1, 1);
		GlStateManager.popMatrix();
		RenderHelper.enableStandardItemLighting();

		if(!tile.transferingStack.isEmpty())
		{
			if(tile.transferTime < 20)
			{
				DrawUtils.renderItemStack_Full(tile.transferingStack, p_76986_2_, p_76986_4_, p_76986_6_, (tile.getWorld().getWorldTime()+p_76986_8_)%360, 0, 1, 1, 1, 0.5F, -0.3F+tile.transferTime/20F, 0.5F);
			}else
			{
				Vec3d vec = new Vec3d(tile.inventoryPos.getX() - tile.getPos().getX(), tile.inventoryPos.getY() - tile.getPos().getY(), tile.inventoryPos.getZ() - tile.getPos().getZ());

				DrawUtils.renderItemStack_Full(tile.transferingStack, p_76986_2_, p_76986_4_, p_76986_6_, (tile.getWorld().getWorldTime()+p_76986_8_)%360, 0, 1, 1, 1, 0.5F+(float)vec.x*(tile.transferTime-20F)/40, 0.5F+(float)vec.y*(tile.transferTime-20F)/40, 0.5F+(float)vec.z*(tile.transferTime-20F)/40);
			}
		}
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(TileEntity p_110775_1_)
	{
		return textures;
	}

	@Override
	public void render(TileMagicalMirror p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_, int destroyStage, float alpha) {
		if(p_147500_1_.getBlockMetadata() == 0)
			this.doRender(p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}

	@Override
	public boolean isGlobalRenderer(TileMagicalMirror te) {
		return true;
	}
}