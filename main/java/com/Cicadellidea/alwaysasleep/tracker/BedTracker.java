package com.Cicadellidea.alwaysasleep.tracker;

import com.Cicadellidea.alwaysasleep.AlwaysAsleep;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class BedTracker {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void bedUsing(PlayerInteractEvent.RightClickBlock event)
    {
        var pos = event.getPos();
        var level = event.getLevel();
        var blockState = level.getBlockState(event.getPos());
        if(blockState.getBlock() instanceof BedBlock)
        {
            if(!level.isClientSide())
            {
                if(blockState.getValue(BlockStateProperties.BED_PART)== BedPart.FOOT){
                    pos = pos.relative(blockState.getValue(BlockStateProperties.HORIZONTAL_FACING));
                    blockState = level.getBlockState(pos);
                }
                

                Player player = event.getEntity();
                ServerPlayer serverPlayer = (ServerPlayer) player;
                if(blockState.getValue(BlockStateProperties.OCCUPIED)){
                    List<Villager> list = level.getEntitiesOfClass(Villager.class, new AABB(pos), LivingEntity::isSleeping);
                    if (!list.isEmpty())
                    {
                        list.get(0).stopSleeping();
                        event.setCanceled(true);
                        return;

                    }


                }
                if(level.dimensionType().bedWorks())
                {
                    serverPlayer.setRespawnPosition(level.dimension(),pos,player.getYRot(),false,true);
                    if(!level.isDay())
                    {
                        player.startSleeping(pos);
                        var ServerLevel = (ServerLevel) level;
                        ServerLevel.updateSleepingPlayerList();
                        event.setCanceled(true);
                        return;

                    }
                }
                /*
                else
                {
                    level.removeBlock(pos, false);
                    BlockPos blockpos = pos.relative(blockState.getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite());
                    if (level.getBlockState(blockpos).getBlock() instanceof BedBlock) {
                        level.removeBlock(blockpos, false);
                    }

                    Vec3 vec3 = pos.getCenter();
                    level.explode((Entity)null, level.damageSources().badRespawnPointExplosion(vec3), (ExplosionDamageCalculator)null, vec3, 5.0F, true, Level.ExplosionInteraction.BLOCK);
                    return;
                }

                 */


            }
        }

    }
}
