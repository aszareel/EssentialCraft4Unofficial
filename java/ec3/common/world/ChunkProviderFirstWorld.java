package ec3.common.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.feature.WorldGenDungeons;

public class ChunkProviderFirstWorld extends ChunkProviderGenerate{
	
	public Random rand;
	public World worldObj;

	public ChunkProviderFirstWorld(World p_i2006_1_, long p_i2006_2_,
			boolean p_i2006_4_) {
		super(p_i2006_1_, p_i2006_2_, false);
		rand = new Random(p_i2006_2_);
		this.worldObj = p_i2006_1_;
	}
	
    public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_)
    {
    	super.populate(p_73153_1_, p_73153_2_, p_73153_3_);
        int k = p_73153_2_ * 16;
        int l = p_73153_3_ * 16;
        int y = this.rand.nextInt(256);
        if(y < 32 && this.rand.nextFloat() < 0.1F)
        {
            int l1 = k + this.rand.nextInt(16) + 8;
            int j2 = l + this.rand.nextInt(16) + 8;
            (new WorldGenOldCatacombs()).generate(this.worldObj, this.rand, l1, y, j2);
        }
        
        if(this.rand.nextInt(32) == 0)
        {
            int l1 = k + this.rand.nextInt(16) + 8;
            int y1 = 6;
            int j2 = l + this.rand.nextInt(16) + 8;
    		int maxY = y1;
    		while(maxY < 256)
    		{
    			++maxY;
    			boolean isAir = true;
    			for(int i = 0; i < 10; ++i)
    			{
    				if(!worldObj.isAirBlock(l1, maxY+i, j2))
    					isAir = false;
    			}
    			if(isAir)
    				break;
    		}
          (new WorldGenDestroyedHouse(worldObj.rand.nextInt(10))).generate(worldObj, worldObj.rand, l1, maxY, j2);
        }
    }

}