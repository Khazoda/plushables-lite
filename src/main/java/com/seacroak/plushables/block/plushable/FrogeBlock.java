package com.seacroak.plushables.block.plushable;

import com.seacroak.plushables.PlushablesMod;
import com.seacroak.plushables.block.BaseInteractablePlushable;
import com.seacroak.plushables.networking.ParticlePayload;
import com.seacroak.plushables.networking.PlushablesNetworking;
import com.seacroak.plushables.networking.SoundPayload;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.item.TooltipType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

import java.util.List;


public class FrogeBlock extends BaseInteractablePlushable {

  public FrogeBlock() {
    super(AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOL).strength(0.7f).nonOpaque().luminance(value -> 8).pistonBehavior(PistonBehavior.DESTROY));
    this.cooldownPeriod = 30;
  }
  @Override
  public VoxelShape getShape() {
    VoxelShape shape = VoxelShapes.empty();
    shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0, 0.25, 0.8125, 0.1875, 0.4375));
    shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0.0625, 0.21875, 0.75, 0.4375, 0.78125));
    shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.4375, 0.28125, 0.75, 0.5625, 0.46875));
    shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0.4375, 0.28125, 0.4375, 0.5625, 0.46875));
    shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0, 0.5625, 0.875, 0.1875, 0.75));
    return shape;
  }

  @Override
  protected ActionResult serverSendEffectPackets(ServerWorld serverWorld, PlayerEntity player, BlockPos pos) {
    SoundPayload.sendPlayerPacketToClients(serverWorld, new SoundPayload(player, pos, SoundEvents.ENTITY_FROG_AMBIENT, 1f));
    ParticlePayload.sendParticlePacketToClients(serverWorld, new ParticlePayload
        (player, pos, "minecraft:wax_on", 5, new Vec3d(0, 0, 0), 5f));
    return ActionResult.CONSUME;
  }

  @Override
  protected ActionResult clientRunEffects(World world, BlockPos pos) {
    PlushablesNetworking.playSoundOnClient(SoundEvents.ENTITY_FROG_AMBIENT, world, pos, 1f, 1f);
    PlushablesNetworking.spawnParticlesOnClient(ParticleTypes.WAX_ON, world, pos, 5, new Vec3d(0, 0, 0), 5f);
    return ActionResult.SUCCESS;
  }

  @Override
  public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
    tooltip.add(Text.translatable("block." + PlushablesMod.MOD_ID + ".froge.tooltip"));
    super.appendTooltip(stack, context, tooltip, options);
  }
}
