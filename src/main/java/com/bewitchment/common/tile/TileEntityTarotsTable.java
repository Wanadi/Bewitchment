package com.bewitchment.common.tile;

import javax.annotation.Nonnull;

import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.TarotMessage;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibGui;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class TileEntityTarotsTable extends ModTileEntity {

	private static final int READ_COST = 2000;

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return false;
	}

	@Override
	public void onBlockBroken(World worldIn, BlockPos pos, IBlockState state) {

	}

	public void read(@Nonnull ItemStack tarotDeck, @Nonnull EntityPlayer reader) {
		if (!reader.world.isRemote) {
			if (checkDeck(tarotDeck) && consumePower(READ_COST)) {
				reader.openGui(Bewitchment.instance, LibGui.TAROT.ordinal(), reader.world, pos.getX(), pos.getY(), pos.getZ());
				NetworkHandler.HANDLER.sendTo(new TarotMessage(reader), (EntityPlayerMP) reader);
			} else {
				reader.sendStatusMessage(new TextComponentTranslation("item.tarots.error_reading"), true);
			}
		}
	}

	private boolean checkDeck(ItemStack tarotDeck) {
		return (tarotDeck.getItem() == ModItems.tarots && tarotDeck.hasTagCompound() && tarotDeck.getTagCompound().hasKey("read_id") && tarotDeck.getTagCompound().hasKey("read_name"));
	}

	private boolean consumePower(int power) {
		if (power == 0) return true;
		// if (magicPointsUser.hasValidAltar(world) || magicPointsUser.findClosestAltar(this.pos, this.world)) {
		// return magicPointsUser.getAltar(world).subtract(power);
		// }
		// TODO
		return false;
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {

	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {

	}
}
