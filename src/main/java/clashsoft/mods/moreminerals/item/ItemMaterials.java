package clashsoft.mods.moreminerals.item;

import java.util.List;

import clashsoft.mods.moreminerals.chem.Element;
import clashsoft.mods.moreminerals.chem.PeriodicTable;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemMaterials extends Item
{
	public static final String[]	names		= { "item.ingot", "item.nugget", "item.dust" };
	
	public IIcon[]					ingotIcons	= new IIcon[PeriodicTable.SIZE];
	public IIcon[]					nuggetIcons	= new IIcon[PeriodicTable.SIZE];
	public IIcon[]					dustIcons	= new IIcon[PeriodicTable.SIZE];
	
	public static Element getFirstElement(ItemStack stack)
	{
		NBTTagCompound compound = stack.getTagCompound();
		if (compound != null)
		{
			int[] nums = compound.getIntArray("Elements");
			if (nums.length > 0)
			{
				return PeriodicTable.get(nums[0]);
			}
		}
		return null;
	}
	
	public static Element[] getElements(ItemStack stack)
	{
		NBTTagCompound compound = stack.getTagCompound();
		if (compound != null)
		{
			int[] nums = compound.getIntArray("Elements");
			return PeriodicTable.toElements(nums);
		}
		return null;
	}
	
	public static void setElement(ItemStack stack, Element e)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null)
		{
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		nbt.setIntArray("Elements", new int[] { e.getNumber() });
	}
	
	public static void setElements(ItemStack stack, Element... elements)
	{
		NBTTagCompound compound = stack.getTagCompound();
		if (compound == null)
		{
			compound = new NBTTagCompound();
			stack.setTagCompound(compound);
		}
		compound.setIntArray("Elements", PeriodicTable.toNums(elements));
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		for (int i = 1; i < PeriodicTable.SIZE; i++)
		{
			Element e = PeriodicTable.get(i);
			if (e != null)
			{
				this.ingotIcons[i] = iconRegister.registerIcon(e.getTextureName(2));
				this.nuggetIcons[i] = iconRegister.registerIcon(e.getTextureName(3));
				this.dustIcons[i] = iconRegister.registerIcon(e.getTextureName(4));
			}
		}
	}
	
	@Override
	public IIcon getIconIndex(ItemStack stack)
	{
		Element e = getFirstElement(stack);
		int metadata = stack.getItemDamage();
		if (metadata == 0)
			return this.ingotIcons[e.getNumber()];
		else if (metadata == 1)
			return this.nuggetIcons[e.getNumber()];
		else if (metadata == 2)
			return this.dustIcons[e.getNumber()];
		return null;
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		return this.getIconIndex(stack);
	}
	
	@Override
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		Element[] elements = getElements(stack);
		return PeriodicTable.calcColor(elements, stack.getItemDamage() + 2);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		Element[] elements = getElements(stack);
		if (elements != null)
		{
			StringBuilder builder = new StringBuilder(elements.length << 4);
			joinElementNames(elements, builder);
			builder.append(" ");
			builder.append(super.getItemStackDisplayName(stack));
		}
		return super.getItemStackDisplayName(stack);
	}
	
	public static void joinElementNames(Element[] elements, StringBuilder builder)
	{
		int len = elements.length;
		if (len == 1)
		{
			builder.append(elements[0].getName());
		}
		else
		{
			for (int i = 0; i < len; i++)
			{
				builder.append(elements[i].getSymbol());
			}
			builder.append(" ");
			builder.append(StatCollector.translateToLocal("material.alloy"));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return names[stack.getItemDamage()];
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		Element[] elements = getElements(stack);
		if (elements != null)
		{
			for (Element e : elements)
			{
				list.add(String.format("%s %s (%d)", e.getName(), e.getSymbol(), e.getNumber()));
			}
		}
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < 3; i++)
		{
			for (Element e : PeriodicTable.getElements())
			{
				if (e != null && e.isAvailable(null))
				{
					ItemStack stack = new ItemStack(this, 1, i);
					setElement(stack, e);
					list.add(stack);
				}
			}
		}
	}
}
