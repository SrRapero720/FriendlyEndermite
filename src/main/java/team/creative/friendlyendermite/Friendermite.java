package team.creative.friendlyendermite;

import java.lang.reflect.Field;
import java.util.Set;

import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@Mod.EventBusSubscriber
@Mod(value = Friendermite.modid)
public class Friendermite {
    
    public static final String modid = "friendermite";
    public static final String version = "1.0";
    
    private static final Field goals = ObfuscationReflectionHelper.findField(GoalSelector.class, "f_25345_");
    private static final Field targetClassField = ObfuscationReflectionHelper.findField(NearestAttackableTargetGoal.class, "f_26048_");
    
    @SubscribeEvent
    public static void entitySpawned(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof EnderMan) {
            EnderMan man = (EnderMan) event.getEntity();
            try {
                Set<WrappedGoal> set = (Set<WrappedGoal>) goals.get(man.targetSelector);
                for (WrappedGoal goal : set) {
                    if (goal.getGoal() instanceof NearestAttackableTargetGoal && targetClassField.get(goal.getGoal()) == Endermite.class) {
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
