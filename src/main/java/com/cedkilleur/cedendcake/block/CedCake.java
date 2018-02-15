package com.cedkilleur.cedendcake.block;

import java.util.List;
import java.util.Random;

import com.cedkilleur.cedendcake.CedMain;
import com.cedkilleur.cedendcake.integration.ICedTOPIntegration;
import com.cedkilleur.cedendcake.integration.ICedWailaIntegration;

import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CedCake extends Block implements ICedTOPIntegration, ICedWailaIntegration{

	public static final PropertyInteger BITES = PropertyInteger.create("bites", 0, 6);
	public static final AxisAlignedBB[] CAKE_AABB = new AxisAlignedBB[] {
			new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D),
			new AxisAlignedBB(0.1875D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D),
			new AxisAlignedBB(0.3125D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D),
			new AxisAlignedBB(0.4375D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D),
			new AxisAlignedBB(0.5625D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D),
			new AxisAlignedBB(0.6875D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D),
			new AxisAlignedBB(0.8125D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D)};

	public CedCake()
	{
		super(Material.CAKE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(BITES, 0));
		this.setTickRandomly(true);
		this.setHardness(0.5F);
		this.setSoundType(SoundType.CLOTH);
		this.setUnlocalizedName(CedMain.MODID + ".cedCake");
		this.setRegistryName(CedMain.MODID + ":cedCake");
		this.setCreativeTab(CedMain.TAB);
	}

	@Override
	public CedCake setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return CAKE_AABB[state.getValue(BITES)];
	}

	@Override @SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		return state.getCollisionBoundingBox(worldIn, pos);
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}


	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ)
	{
		int meta = this.getMetaFromState(worldIn.getBlockState(pos)) - 1;
		final ItemStack item = playerIn.getHeldItem(hand);

		if (playerIn.capabilities.isCreativeMode)
		{
			if ((item != null) && (item.getItem() == Items.ENDER_EYE))
			{
				worldIn.setBlockState(pos, this.getStateFromMeta(0), 2);
				return true;
			}
			else
			{
				playerIn.changeDimension(1);
				return true;
			}
		}
		else
		{
			if ((item != null) && (item.getItem() == Items.ENDER_EYE))
			{
				if (meta >= 0)
				{
					worldIn.setBlockState(pos, this.getStateFromMeta(meta), 2);
					item.shrink(1);
					return true;
				}
			}
			else
			{
				this.nomEndCake(worldIn, pos, playerIn);
				return true;
			}
		}

		return false;
	}

	private void nomEndCake(World world, BlockPos pos, EntityPlayer player)
	{
		if (player.canEat(false) || CedMain.eatCakeWhenFull)
		{
			int l = this.getMetaFromState(world.getBlockState(pos)) + 1;

			if (l >= 6)
			{
				return;
			}
			else
			{
				player.getFoodStats().addStats(2, 0.1F);
				world.setBlockState(pos, this.getStateFromMeta(l), 2);
				if (world.provider.getDimension() == 0)
				{
					player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("resistance"), 200, 1));
					player.changeDimension(1);
				}
				if (world.provider.getDimension() == 1)
				{
					player.changeDimension(1);
				}
			}
		}
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getStateFromMeta(6);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		if (world.provider.getDimension() == 1)
		{
			world.scheduleBlockUpdate(pos, this, 0, 12000);
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (world.provider.getDimension() == 1)
		{
			int meta = this.getMetaFromState(world.getBlockState(pos)) - 1;
			if (meta > 0) {
				world.setBlockState(pos, this.getStateFromMeta(meta), 2);
			}
			world.scheduleBlockUpdate(pos, this, 0, 12000);
		}
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!this.canBlockStay(worldIn, pos)) {
			worldIn.setBlockToAir(pos);
		}
	}

	private boolean canBlockStay(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return null;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(BITES, meta);
	}

	@Override @SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(BITES);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BITES);
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
		return (7 - blockState.getValue(BITES)) * 2;
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		list.add(new ItemStack(this));
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
		if (world.getBlockState(data.getPos()).getBlock() instanceof CedCake) {
			probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
			.item(new ItemStack(Items.CAKE))
			.text(TextFormatting.GREEN + "Uses Left : ")
			.progress(6 - blockState.getValue(BITES), 6);
		}
	}


	//WAILA INTEGRATION
	@SideOnly(Side.CLIENT)
	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		if (accessor.getBlockState().getBlock() instanceof CedCake) {
			currenttip.add(TextFormatting.GREEN + "Uses Left : " + (6 - accessor.getBlockState().getValue(BITES)) + " out of 6");
		}
		return currenttip;
	}
}
