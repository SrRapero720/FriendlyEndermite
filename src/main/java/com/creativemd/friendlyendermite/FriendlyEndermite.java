package com.creativemd.friendlyendermite;

import java.lang.reflect.Field;

import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

@Mod.EventBusSubscriber
@Mod(modid = FriendlyEndermite.modid, name = "Friendly Endermite", version = FriendlyEndermite.version)
public class FriendlyEndermite {
	
	public static final String modid = "friendlyendermite";
	public static final String version = "1.0";
	
	private static Field targetClassField = ReflectionHelper.findField(EntityAINearestAttackableTarget.class, new String[] { "targetClass", "field_75307_b" });
	
	@SubscribeEvent
	public static void entitySpawned(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof EntityEnderman) {
			EntityEnderman man = (EntityEnderman) event.getEntity();
			try {
				for (EntityAITaskEntry task : man.targetTasks.taskEntries) {
					if (task.action instanceof EntityAINearestAttackableTarget && targetClassField.get(task.action) == EntityEndermite.class) {
						man.targetTasks.removeTask(task.action);
						return;
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
}
