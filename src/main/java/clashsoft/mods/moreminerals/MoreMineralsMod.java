package clashsoft.mods.moreminerals;

import clashsoft.cslib.minecraft.block.CSBlocks;
import clashsoft.cslib.minecraft.creativetab.CustomCreativeTab;
import clashsoft.cslib.minecraft.init.CSLib;
import clashsoft.cslib.minecraft.init.ClashsoftMod;
import clashsoft.cslib.minecraft.item.CSItems;
import clashsoft.cslib.minecraft.update.CSUpdate;
import clashsoft.mods.moreminerals.block.BlockOres;
import clashsoft.mods.moreminerals.common.MMMProxy;
import clashsoft.mods.moreminerals.item.ItemBlockOres;
import clashsoft.mods.moreminerals.item.ItemMaterials;
import clashsoft.mods.moreminerals.tileentity.TileEntityOres;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

@Mod(modid = MoreMineralsMod.MODID, name = MoreMineralsMod.NAME, version = MoreMineralsMod.VERSION, dependencies = MoreMineralsMod.DEPENDENCIES)
public class MoreMineralsMod extends ClashsoftMod
{
	public static final String		MODID				= "moreminerals";
	public static final String		NAME				= "More Minerals Mod";
	public static final String		ACRONYM				= "mmm";
	public static final String		VERSION				= CSUpdate.CURRENT_VERSION + "-1.0.0-dev";
	public static final String		DEPENDENCIES		= CSLib.DEPENDENCY;
	
	@Instance(MODID)
	public static MoreMineralsMod	INSTANCE;
	
	public static MMMProxy			proxy				= createProxy("clashsoft.mods.moreminerals.client.MMMClientProxy", "clashsoft.mods.moreminerals.common.MMMProxy");
	
	public static int[]				overworldGen		= new int[] { 12, 14, 20, 16, 24, 24, 24, 24, 16, 16 };
	public static int[]				overworldGenVanilla	= new int[] { 128, 16, 32, 32, 64, 32, 16 };
	
	public static Block[]			baseBlocks			= { Blocks.stone, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.netherrack, Blocks.end_stone };
	
	public static BlockOres			ores;
	public static ItemMaterials		materials;
	
	public static CreativeTabs		oresTab				= new CustomCreativeTab("ores");
	public static CreativeTabs		materialsTab		= new CustomCreativeTab("materials");
	
	public MoreMineralsMod()
	{
		super(proxy, MODID, NAME, ACRONYM, VERSION);
		this.url = "https://github.com/Clashsoft/More-Minerals-Mod/wiki";
	}
	
	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		
		ores = (BlockOres) new BlockOres().setCreativeTab(oresTab);
		materials = (ItemMaterials) new ItemMaterials().setCreativeTab(materialsTab);
		
		CSBlocks.addBlock(ores, ItemBlockOres.class, "ores");
		CSItems.addItem(materials, "materials");
	}
	
	@Override
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		
		GameRegistry.registerTileEntity(TileEntityOres.class, "MMMOres");
	}
	
	@Override
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);
	}
}