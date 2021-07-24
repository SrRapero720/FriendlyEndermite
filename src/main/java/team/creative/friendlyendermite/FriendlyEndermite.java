package team.creative.friendlyendermite;

import java.lang.reflect.Field;
import java.util.Set;

import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

@Mod.EventBusSubscriber
@Mod(value = FriendlyEndermite.modid)
public class FriendlyEndermite {
    
    public static final String modid = "friendlyendermite";
    public static final String version = "1.0";
    
    private static final Field goals = ObfuscationReflectionHelper.findField(GoalSelector.class, "f_25345_");
    private static final Field targetClassField = ObfuscationReflectionHelper.findField(NearestAttackableTargetGoal.class, "field_75307_b");
    
    @SubscribeEvent
    public static void entitySpawned(EntityJoinWorldEvent event) {
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
