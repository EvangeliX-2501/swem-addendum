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

            SwemAddendumMain.LOGGER.debug("Trying to find coats in: " + pack.getId());

            try(PackResources packResources = pack.open()) {
                Set<String> namespaces = packResources.getNamespaces(PackType.CLIENT_RESOURCES);
                for(String namespace : namespaces) {
                    allTextures.addAll(
                            packResources.getResources(PackType.CLIENT_RESOURCES, namespace, folderName, 0, (name) -> name.endsWith(".png") && !name.endsWith("_wings.png"))
                    );
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

            SwemAddendumMain.LOGGER.debug("Trying to find coats in: " + pack.getId());

            try(PackResources packResources = pack.open()) {
                Set<String> namespaces = packResources.getNamespaces(PackType.CLIENT_RESOURCES);
                for(String namespace : namespaces) {
                    allTextures.addAll(
                            packResources.getResources(PackType.CLIENT_RESOURCES, namespace, folderName, 0, (name) -> name.endsWith(".png") && !name.endsWith("_wings.png"))
                    );
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
}
