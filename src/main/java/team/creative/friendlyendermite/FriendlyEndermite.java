package team.creative.friendlyendermite;

import java.lang.reflect.Field;
import java.util.Set;

import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

@Mod.EventBusSubscriber
@Mod(value = FriendlyEndermite.modid)
public class FriendlyEndermite {
	
	public static final String modid = "friendlyendermite";
	public static final String version = "1.0";
	
	private static final Field goals = ObfuscationReflectionHelper.findField(GoalSelector.class, "field_220892_d");
	private static final Field targetClassField = ObfuscationReflectionHelper.findField(NearestAttackableTargetGoal.class, "field_75307_b");
	
	@SubscribeEvent
	public static void entitySpawned(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof EndermanEntity) {
			EndermanEntity man = (EndermanEntity) event.getEntity();
			try {
				Set<PrioritizedGoal> set = (Set<PrioritizedGoal>) goals.get(man.targetSelector);
				for (PrioritizedGoal goal : set) {
					if (goal.getGoal() instanceof NearestAttackableTargetGoal && targetClassField.get(goal) == EndermiteEntity.class) {
						man.targetSelector.removeGoal(goal.getGoal());
						return;
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
}
