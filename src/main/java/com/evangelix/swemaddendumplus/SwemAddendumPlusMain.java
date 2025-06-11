package com.evangelix.swemaddendumplus;

import com.evangelix.swemaddendumplus.abstract_steed.AbstractSteed;
import com.evangelix.swemaddendumplus.breeds.american_quarter_horse.AmericanQuarterHorse;
import com.evangelix.swemaddendumplus.breeds.arabian.Arabian;
import com.evangelix.swemaddendumplus.breeds.breton.Breton;
import com.evangelix.swemaddendumplus.breeds.donkey.Donkey;
import com.evangelix.swemaddendumplus.breeds.fjord.Fjord;
import com.evangelix.swemaddendumplus.breeds.friesian.Friesian;
import com.evangelix.swemaddendumplus.breeds.irish_draught.IrishDraught;
import com.evangelix.swemaddendumplus.breeds.kladruber.Kladruber;
import com.evangelix.swemaddendumplus.breeds.knabstrupper.Knabstrupper;
import com.evangelix.swemaddendumplus.breeds.marwari.Marwari;
import com.evangelix.swemaddendumplus.breeds.mule.Mule;
import com.evangelix.swemaddendumplus.breeds.mustang.Mustang;
import com.evangelix.swemaddendumplus.breeds.pegasus.Pegasus;
import com.evangelix.swemaddendumplus.breeds.shire.Shire;
import com.evangelix.swemaddendumplus.breeds.thoroughbred.Thoroughbred;
import com.evangelix.swemaddendumplus.breeds.turkoman.Turkoman;
import com.evangelix.swemaddendumplus.breeds.warmblood.Warmblood;
import com.evangelix.swemaddendumplus.item.JumpBreaker;
import com.evangelix.swemaddendumplus.item.SpeedBreaker;
import com.evangelix.swemaddendumplus.item.StickOfAcknowledgement;
import com.mojang.logging.LogUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(SwemAddendumPlusMain.MODID)
public class SwemAddendumPlusMain
{
    public static final String MODID = "swemaddendumplus";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
    public static <T extends AbstractSteed> RegistryObject<EntityType<T>> registerSteed(String name, EntityType.EntityFactory<T> steedEntityFactory) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.of(steedEntityFactory, MobCategory.CREATURE).sized(1.6f, 1.99f).build((new ResourceLocation(MODID, name)).toString()));
    }

    public static final RegistryObject<EntityType<AmericanQuarterHorse>> AMERICAN_QUARTER_HORSE = registerSteed("american_quarter_horse", AmericanQuarterHorse::new);
    public static final RegistryObject<EntityType<Arabian>> ARABIAN = registerSteed("arabian", Arabian::new);
    public static final RegistryObject<EntityType<Breton>> BRETON = registerSteed("breton", Breton::new);
    public static final RegistryObject<EntityType<Donkey>> DONKEY = registerSteed("donkey", Donkey::new);
    public static final RegistryObject<EntityType<Fjord>> FJORD = registerSteed("fjord", Fjord::new);
    public static final RegistryObject<EntityType<Friesian>> FRIESIAN = registerSteed("friesian", Friesian::new);
    public static final RegistryObject<EntityType<IrishDraught>> IRISH_DRAUGHT = registerSteed("steed", IrishDraught::new);
    public static final RegistryObject<EntityType<Kladruber>> KLADRUBER = registerSteed("kladruber", Kladruber::new);
    public static final RegistryObject<EntityType<Knabstrupper>> KNABSTRUPPER = registerSteed("knabstrupper", Knabstrupper::new);
    public static final RegistryObject<EntityType<Marwari>> MARWARI = registerSteed("marwari", Marwari::new);
    public static final RegistryObject<EntityType<Mule>> MULE = registerSteed("mule", Mule::new);
    public static final RegistryObject<EntityType<Mustang>> MUSTANG = registerSteed("mustang", Mustang::new);
    public static final RegistryObject<EntityType<Pegasus>> PEGASUS = registerSteed("pegasus", Pegasus::new);
    public static final RegistryObject<EntityType<Shire>> SHIRE = registerSteed("shire", Shire::new);
    public static final RegistryObject<EntityType<Thoroughbred>> THOROUGHBRED = registerSteed("thoroughbred", Thoroughbred::new);
    public static final RegistryObject<EntityType<Turkoman>> TURKOMAN = registerSteed("turkoman", Turkoman::new);
    public static final RegistryObject<EntityType<Warmblood>> WARMBLOOD = registerSteed("warmblood", Warmblood::new);

    public static final EntityDataSerializer<ResourceLocation> RESOURCE_LOCATION = EntityDataSerializer.simple(FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::readResourceLocation);
    static {
        EntityDataSerializers.registerSerializer(RESOURCE_LOCATION);
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<StickOfAcknowledgement> STICK_OF_ACKNOWLEDGEMENT = ITEMS.register("stick_of_acknowledgement", StickOfAcknowledgement::new);
    public static final RegistryObject<JumpBreaker> JUMP_BREAKER = ITEMS.register("jump_breaker", JumpBreaker::new);
    public static final RegistryObject<SpeedBreaker> SPEED_BREAKER = ITEMS.register("speed_breaker", SpeedBreaker::new);


    public SwemAddendumPlusMain()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ENTITY_TYPES.register(eventBus);
        ITEMS.register(eventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }
}
