package eu.midnightdust.swordblocking.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class SwordBlockingConfig {

    public static ForgeConfigSpec.BooleanValue enabled;
    public static ForgeConfigSpec.BooleanValue hideShield;
    public static ForgeConfigSpec.BooleanValue alwaysHideShield;
    public static ForgeConfigSpec.BooleanValue hideOffhandSlot;

    public static ForgeConfigSpec spec;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("Sword Blocking Configuration").push("general");

        enabled = builder
                .comment("Enable Sword Blocking")
                .define("enabled", true);

        hideShield = builder
                .comment("Hide Shield when blocking with weapon")
                .define("hideShield", true);

        alwaysHideShield = builder
                .comment("Always hide the shield")
                .define("alwaysHideShield", true);

        hideOffhandSlot = builder
                .comment("Hide the offhand slot when holding a shield")
                .define("hideOffhandSlot", false);

        builder.pop();
        spec = builder.build();
    }
}
