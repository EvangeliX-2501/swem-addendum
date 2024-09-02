package com.evangelix.swemaddendum;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TextureGen {
    public static Random random = new Random();

    public static ResourceLocation getRandomClientCoat(String folderName) {
        PackRepository packRepository = Minecraft.getInstance().getResourcePackRepository();
        List<ResourceLocation> allTextures = new ArrayList<>();
        for(Pack pack: packRepository.getSelectedPacks()) {
            if(pack.getId().equals("vanilla") || pack.getId().equals("mod_resources")) {
                continue;
            }

            SwemAddendumMain.LOGGER.debug("Looking for coats in: " + pack.getId());

            try(PackResources packResources = pack.open()) {
                Set<String> namespaces = packResources.getNamespaces(PackType.CLIENT_RESOURCES);
                for(String namespace : namespaces) {
                    packResources.listResources(PackType.CLIENT_RESOURCES, namespace, folderName, (resourceLocation, inputStreamIoSupplier) -> {
                        if(resourceLocation.toString().endsWith(".png") && !resourceLocation.toString().endsWith("_wings.png")) {
                            allTextures.add(resourceLocation);
                        }
                    });
                }
            } catch (Exception e) {
                SwemAddendumMain.LOGGER.error("Failed to randomly get a client coat from: " + folderName);
                return MissingTextureAtlasSprite.getLocation();
            }
        }
        if(allTextures.size() == 0) {
            SwemAddendumMain.LOGGER.error("Failed to randomly get a client coat from: " + folderName);
            return MissingTextureAtlasSprite.getLocation();
        }
        return allTextures.get(random.nextInt(allTextures.size()));
    }

    public static ResourceLocation getRandomServerCoat(String folderName) {
        PackRepository packRepository = Minecraft.getInstance().getResourcePackRepository();
        List<ResourceLocation> allTextures = new ArrayList<>();
        for(Pack pack : packRepository.getSelectedPacks()) {
            if(pack.getPackSource() != PackSource.SERVER) {
                continue;
            }

            SwemAddendumMain.LOGGER.debug("Looking for coats in: " + pack.getId());

            try(PackResources packResources = pack.open()) {
                Set<String> namespaces = packResources.getNamespaces(PackType.CLIENT_RESOURCES);
                for(String namespace : namespaces) {
                    packResources.listResources(PackType.CLIENT_RESOURCES, namespace, folderName, (resourceLocation, inputStreamIoSupplier) -> {
                        if(resourceLocation.toString().endsWith(".png") && !resourceLocation.toString().endsWith("_wings.png")) {
                            allTextures.add(resourceLocation);
                        }
                    });
                }
            } catch (Exception e) {
                SwemAddendumMain.LOGGER.error("Failed to randomly get server coat from: " + folderName);
                return MissingTextureAtlasSprite.getLocation();
            }
        }
        if(allTextures.size() == 0) {
            SwemAddendumMain.LOGGER.error("Failed to randomly get a server coat from: " + folderName);
            return MissingTextureAtlasSprite.getLocation();
        }
        return allTextures.get(random.nextInt(allTextures.size()));
    }

    public static List<ResourceLocation> getAllCoats(String folderName) {
        PackRepository packRepository = Minecraft.getInstance().getResourcePackRepository();
        List<ResourceLocation> allTextures = new ArrayList<>();
        for(Pack pack : packRepository.getSelectedPacks()) {
            try(PackResources packResources = pack.open()) {
                Set<String> namespaces = packResources.getNamespaces(PackType.CLIENT_RESOURCES);
                for(String namespace : namespaces) {
                    packResources.listResources(PackType.CLIENT_RESOURCES, namespace, folderName, ((resourceLocation, inputStreamIoSupplier) -> {
                        if(resourceLocation.toString().endsWith(".png") && !resourceLocation.toString().endsWith("_wings.png")) {
                            allTextures.add(resourceLocation);
                        }
                    }));
                }
            } catch (Exception e) {
                SwemAddendumMain.LOGGER.error("Failed getting coat data from " + pack.getId() + ": " + e);
            }
        }
        return allTextures;
    }
}
