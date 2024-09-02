## SWEM Addendum

SWEM Addendum uses a dynamic resources mechanic that allows you to create entirely new,
dynamic textures with a simple resource pack.

No coding, at least from you, required!

### Make sure to set your resource pack up correctly.
Import the GeckoLib geo models into Blockbench and create your texture.

For example, import 'swem_horse.geo.json' into Blockbench, and create your new texture(s).

For pegasus wing textures: If you have the texture pegasus_1.png, name the wing texture pegasus_1_wings.png. 
Just add '_wings.png' to the end of your texture name.

You must have an 'assets' folder within your texture pack, and a working MCMETA file. 
Example:

```
my_wondrous_texture_pack > assets & mcmeta > horse_textures > my_horsie.png
                                             ^ start with this folder when using the summon commands, NOT the pack name!
```

Command Example:
```
/summon swemaddendum:steed ~ ~ ~ {Texture:"horse_coats/my_custom_texture_name.png"}
```