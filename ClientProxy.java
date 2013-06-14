package clashsoft.mods.moreminerals;

import clashsoft.clashsoftapi.ClashsoftMod;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	public static int oreRenderer;
	
	public void registerRenderers()
	{
		oreRenderer = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(oreRenderer, new MoreMineralsOreRenderer());
		
		MinecraftForgeClient.registerItemRenderer(MoreMineralsMod.dataSword.itemID, ClashsoftMod.csItemRenderer);
		MinecraftForgeClient.registerItemRenderer(MoreMineralsMod.dataSpade.itemID, ClashsoftMod.csItemRenderer);
		MinecraftForgeClient.registerItemRenderer(MoreMineralsMod.dataPickaxe.itemID, ClashsoftMod.csItemRenderer);
		MinecraftForgeClient.registerItemRenderer(MoreMineralsMod.dataAxe.itemID, ClashsoftMod.csItemRenderer);
		MinecraftForgeClient.registerItemRenderer(MoreMineralsMod.dataHoe.itemID, ClashsoftMod.csItemRenderer);
	}
}
