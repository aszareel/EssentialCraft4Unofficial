package ec3.client.render.tile;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import ec3.client.model.ModelFloatingCube;
import ec3.common.tile.TileMRUCoil;
import ec3.common.tile.TileRayTower;

@SideOnly(Side.CLIENT)
public class RenderMRUCoil extends TileEntitySpecialRenderer
{
    private static final ResourceLocation enderCrystalTextures = new ResourceLocation("essentialcraft:textures/entities/rayCrystal.png");
    private ModelFloatingCube model;

    public RenderMRUCoil()
    {
        this.model = new ModelFloatingCube(0.0F, true);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(TileMRUCoil p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
    	RenderHelper.disableStandardItemLighting();
        float f2 = p_76986_1_.innerRotation + p_76986_9_;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)p_76986_2_+0.5F, (float)p_76986_4_+0.6F, (float)p_76986_6_+0.5F);
        this.bindTexture(enderCrystalTextures);
        float f3 = MathHelper.sin(f2 * 0.2F) / 2.0F + 0.5F;
        f3 += f3 * f3;
        GlStateManager.scale(0.2F, 0.2F, 0.2F);
        this.model.render(p_76986_1_, 0.0F, f2 * 3.0F, 0.35F, 0.0F, 0.0F, 0.0625F);
        if(p_76986_1_.localLightning != null)
        	p_76986_1_.localLightning.render(p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        if(p_76986_1_.monsterLightning != null)
        	p_76986_1_.monsterLightning.render(p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_);
        GlStateManager.popMatrix();

        RenderHelper.enableStandardItemLighting();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(TileRayTower p_110775_1_)
    {
        return enderCrystalTextures;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(TileEntity p_110775_1_)
    {
        return this.getEntityTexture((TileRayTower)p_110775_1_);
    }

	@Override
	public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_, int destroyStage) {
		this.doRender((TileMRUCoil) p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}