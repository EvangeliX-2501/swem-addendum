package com.evangelix.swemaddendum;

import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;

import java.util.Random;

public class TraitRegistrar {
    public static Random RANDOM = new Random();

    public enum Personality {
        VICIOUS(Component.translatable("personality.vicious.name"), Component.translatable("personality.vicious.description")),
        DOWN_TRODDEN(Component.translatable("personality.down_trodden.name"), Component.translatable("personality.down_trodden.description")),
        WILD(Component.translatable("personality.wild.name"), Component.translatable("personality.wild.description")),
        ENERGETIC(Component.translatable("personality.energetic.name"), Component.translatable("personality.energetic.description")),
        SLOTHFUL(Component.translatable("personality.slothful.name"), Component.translatable("personality.slothful.description")),
        GLUTTONOUS(Component.translatable("personality.gluttonous.name"), Component.translatable("personality.gluttonous.description")),
        BRAVE(Component.translatable("personality.brave.name"), Component.translatable("personality.brave.description")),
        BOUNCY(Component.translatable("personality.bouncy.name"), Component.translatable("personality.bouncy.description")),
        STUBBORN(Component.translatable("personality.stubborn.name"), Component.translatable("personality.stubborn.description")),
        NONE(Component.empty(), Component.empty());

        public final Component name;
        public final Component description;

        Personality(Component name, Component description) {
            this.name = name;
            this.description = description;
        }
    }

    public enum Cleanliness {
        PRISTINE(Component.translatable("cleanliness.pristine")),
        CLEAN(Component.translatable("cleanliness.clean")),
        GRIMY(Component.translatable("cleanliness.grimy")),
        DIRTY(Component.translatable("cleanliness.dirty").append(" (").append(Component.translatable("effect.minecraft.slowness")).append(" ").append(Component.translatable("enchantment.level.1")).append(")")),
        FILTHY(Component.translatable("cleanliness.filthy").append(" (").append(Component.translatable("effect.minecraft.slowness")).append(" ").append(Component.translatable("enchantment.level.2")).append(")"));
        public final Component name;

        Cleanliness(Component name) {
            this.name = name;
        }
    }

    public static final EntityDataAccessor<Personality> PERSONALITY = SynchedEntityData.defineId(AbstractSteed.class, SwemAddendumMain.PERSONALITY);
    public static final EntityDataAccessor<Cleanliness> CLEANLINESS = SynchedEntityData.defineId(AbstractSteed.class, SwemAddendumMain.CLEANLINESS);

    public static <T extends Enum<T>> T getRandom(Class<T> cls) {
        T[] constants = cls.getEnumConstants();
        return constants[RANDOM.nextInt(constants.length)];
    }

    public static <T extends Enum<T>> T getNext(T current) {
        Class<T> cls = current.getDeclaringClass();
        T[] constants = cls.getEnumConstants();
        return constants[(current.ordinal() + 1) % constants.length];
    }

    public static <T extends Enum<T>> T getNextMax(T current) {
        Class<T> cls = current.getDeclaringClass();
        T[] constants = cls.getEnumConstants();
        return constants[Math.min(current.ordinal() + 1, constants.length - 1)];
    }

    public static <T extends Enum<T>> T getPreviousMin(T current) {
        Class<T> cls = current.getDeclaringClass();
        T[] constants = cls.getEnumConstants();
        return constants[Math.max(current.ordinal() - 1, 0)];
    }

    public static void apply(AbstractSteed abstractSteed) {
        abstractSteed.getEntityData().define(PERSONALITY, Personality.NONE);
        abstractSteed.getEntityData().define(CLEANLINESS, Cleanliness.CLEAN);
    }
}
