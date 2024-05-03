package team.creative.friendermite;

import java.util.Set;

import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import team.creative.friendermite.mixin.GoalSelectorAccessor;
import team.creative.friendermite.mixin.NearestAttackableTargetGoalAccessor;

@EventBusSubscriber
@Mod(value = Friendermite.modid)
public class Friendermite {
    
    public static final String modid = "friendermite";
    public static final String version = "1.0";
    
    @SubscribeEvent
    public static void entitySpawned(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof EnderMan man) {
            Set<WrappedGoal> set = ((GoalSelectorAccessor) man.targetSelector).getAvailableGoals();
            for (WrappedGoal goal : set) {
                if (goal.getGoal() instanceof NearestAttackableTargetGoalAccessor g && g.getTargetType() == Endermite.class) {
                    man.targetSelector.removeGoal(goal.getGoal());
                    return;
                }
            }
        }
    }
    
}
