# Custre - Custom Stripping Recipes

---

## Information
API mod for allowing users to define custom axe stripping recipes.
Mod works by replacing Minecraft inbuilt static code with dynamic
data driven approach, which allows users to enable/disable minecraft
stripping recipes and to create custom stripping recipes. There is
just one rule - the input and output block must extend PillarBlock.

## Usage
### Mod
This mod can be downloaded on [CurseForge](https://www.curseforge.com/minecraft/mc-mods/custre),
dropped in mods folder of minecraft directory and loaded by fabric mod loader.

### API
Mod is published on custom maven repository. You can fetch it and
include it in your mod in following way:
```groovy
repositories {
    maven {
        url = "https://prod-cebbys-repomanager.herokuapp.com"
    }
}
```
```groovy
dependencies {
    // Example include with version template
    include "lv.cebbys.mcmods:custre:${version}"
    
    // Example include of version 2.0.0
    include "lv.cebbys.mcmods:custre:2.0.0"
}
```

### Datapack
Custom stripping recipes can be created in following way:<br>
In the following datapack directory <code>data/${namespace}/recipes/custre/</code>
adding the following <code>{recipe_name}.json</code> files
```json
{
  "ingredient": "${namespace}:${block_extends_pillar}",
  "result": "${namespace}:${block_extends_pillar}"
}
```
Example:
```json
{
  "ingredient": "minecraft:oak_log",
  "result": "minecraft:stripped_oak_log"
}
```

---