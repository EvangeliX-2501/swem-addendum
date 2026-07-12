package com.evangelix.swemaddendum;

import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;
import com.evangelix.swemaddendum.breeds.american_quarter_horse.AmericanQuarterHorse;
import com.evangelix.swemaddendum.breeds.arabian.Arabian;
import com.evangelix.swemaddendum.breeds.breton.Breton;
import com.evangelix.swemaddendum.breeds.donkey.Donkey;
import com.evangelix.swemaddendum.breeds.fjord.Fjord;
import com.evangelix.swemaddendum.breeds.friesian.Friesian;
import com.evangelix.swemaddendum.breeds.irish_draught.IrishDraught;
import com.evangelix.swemaddendum.breeds.kladruber.Kladruber;
import com.evangelix.swemaddendum.breeds.knabstrupper.Knabstrupper;
import com.evangelix.swemaddendum.breeds.marwari.Marwari;
import com.evangelix.swemaddendum.breeds.mule.Mule;
import com.evangelix.swemaddendum.breeds.mustang.Mustang;
import com.evangelix.swemaddendum.breeds.pegasus.Pegasus;
import com.evangelix.swemaddendum.breeds.shire.Shire;
import com.evangelix.swemaddendum.breeds.thoroughbred.Thoroughbred;
import com.evangelix.swemaddendum.breeds.turkoman.Turkoman;
import com.evangelix.swemaddendum.breeds.warmblood.Warmblood;
import com.evangelix.swemaddendum.item.StickOfAcknowledgement;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.slf4j.Logger;

import java.util.function.Supplier;

@Mod(SwemAddendumMain.MODID)
public class SwemAddendumMain
{
    public static final String MODID = "swemaddendum";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, MODID);
    public static final Supplier<EntityDataSerializer<ResourceLocation>> RESOURCE_LOCATION = ENTITY_DATA_SERIALIZERS.register("resource_location", () -> EntityDataSerializer.forValueType(ResourceLocation.STREAM_CODEC));


    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MODID);
    public static <T extends AbstractSteed> Supplier<EntityType<T>> registerSteed(String name, EntityType.EntityFactory<T> entityFactory) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.of(entityFactory, MobCategory.CREATURE)
                .sized(1.6f, 1.99f)
                .attach(EntityAttachment.PASSENGER, 0f, 1.99f, -0.05f)
                .attach(EntityAttachment.PASSENGER, 0f, 1.99f, -0.75f)
                .build(ResourceLocation.fromNamespaceAndPath(MODID, name).toString()));
    }

    public static final Supplier<EntityType<AmericanQuarterHorse>> AMERICAN_QUARTER_HORSE = registerSteed("american_quarter_horse", AmericanQuarterHorse::new);
    public static final Supplier<EntityType<Arabian>> ARABIAN = registerSteed("arabian", Arabian::new);
    public static final Supplier<EntityType<Breton>> BRETON = registerSteed("breton", Breton::new);
    public static final Supplier<EntityType<Donkey>> DONKEY = registerSteed("donkey", Donkey::new);
    public static final Supplier<EntityType<Fjord>> FJORD = registerSteed("fjord", Fjord::new);
    public static final Supplier<EntityType<Friesian>> FRIESIAN = registerSteed("friesian", Friesian::new);
    public static final Supplier<EntityType<IrishDraught>> IRISH_DRAUGHT = registerSteed("irish_draught", IrishDraught::new);
    public static final Supplier<EntityType<Kladruber>> KLADRUBER = registerSteed("kladruber", Kladruber::new);
    public static final Supplier<EntityType<Knabstrupper>> KNABSTRUPPER = registerSteed("knabstrupper", Knabstrupper::new);
    public static final Supplier<EntityType<Marwari>> MARWARI = registerSteed("marwari", Marwari::new);
    public static final Supplier<EntityType<Mule>> MULE = registerSteed("mule", Mule::new);
    public static final Supplier<EntityType<Mustang>> MUSTANG = registerSteed("mustang", Mustang::new);
    public static final Supplier<EntityType<Pegasus>> PEGASUS = registerSteed("pegasus", Pegasus::new);
    public static final Supplier<EntityType<Shire>> SHIRE = registerSteed("shire", Shire::new);
    public static final Supplier<EntityType<Thoroughbred>> THOROUGHBRED = registerSteed("thoroughbred", Thoroughbred::new);
    public static final Supplier<EntityType<Turkoman>> TURKOMAN = registerSteed("turkoman", Turkoman::new);
    public static final Supplier<EntityType<Warmblood>> WARMBLOOD = registerSteed("warmblood", Warmblood::new);




    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, MODID);
    public static final Supplier<StickOfAcknowledgement> STICK_OF_ACKNOWLEDGEMENT = ITEMS.register("stick_of_acknowledgement", StickOfAcknowledgement::new);

    public SwemAddendumMain(IEventBus modEventBus) {
        ENTITY_DATA_SERIALIZERS.register(modEventBus);
        ENTITY_TYPES.register(modEventBus);
        ITEMS.register(modEventBus);
    }
}
