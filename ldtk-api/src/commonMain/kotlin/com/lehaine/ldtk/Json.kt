// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json       = Json(JsonConfiguration.Stable)
// val coordinate = json.parse(Coordinate.serializer(), jsonString)

package com.lehaine.ldtk

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*

/**
 * This file is a JSON schema of files created by LDtk level editor (https://ldtk.io).
 *
 * This is the root of any Project JSON file. It contains:  - the project settings, - an
 * array of levels, - and a definition object (that can probably be safely ignored for most
 * users).
 */
@Serializable
data class ProjectJson(
    /**
     * Number of backup files to keep, if the `backupOnSave` is TRUE
     */
    val backupLimit: Int,

    /**
     * If TRUE, an extra copy of the project will be created in a sub folder, when saving.
     */
    val backupOnSave: Boolean,

    /**
     * Project background color
     */
    val bgColor: String,

    /**
     * Default grid size for new layers
     */
    val defaultGridSize: Int,

    /**
     * Default background color of levels
     */
    val defaultLevelBgColor: String,

    /**
     * Default new level height
     */
    val defaultLevelHeight: Int,

    /**
     * Default new level width
     */
    val defaultLevelWidth: Int,

    /**
     * Default X pivot (0 to 1) for new entities
     */
    val defaultPivotX: Float,

    /**
     * Default Y pivot (0 to 1) for new entities
     */
    val defaultPivotY: Float,

    /**
     * A structure containing all the definitions of this project
     */
    val defs: Definitions,

    /**
     * If TRUE, all layers in all levels will also be exported as PNG along with the project
     * file (default is FALSE)
     */
    @SerialName("exportPng")
    val exportPNG: Boolean,

    /**
     * If TRUE, a Tiled compatible file will also be generated along with the LDtk JSON file
     * (default is FALSE)
     */
    val exportTiled: Boolean,

    /**
     * If TRUE, one file will be saved for the project (incl. all its definitions) and one file
     * in a sub-folder for each level.
     */
    val externalLevels: Boolean,

    /**
     * An array containing various advanced flags (ie. options or other states). Possible
     * values: `DiscardPreCsvIntGrid`, `IgnoreBackupSuggest`
     */
    val flags: List<Flag>,

    /**
     * File format version
     */
    val jsonVersion: String,

    /**
     * All levels. The order of this array is only relevant in `LinearHorizontal` and
     * `linearVertical` world layouts (see `worldLayout` value). Otherwise, you should refer to
     * the `worldX`,`worldY` coordinates of each Level.
     */
    val levelDefinitions: List<LevelDefinition>,

    /**
     * If TRUE, the Json is partially minified (no indentation, nor line breaks, default is
     * FALSE)
     */
    @SerialName("minifyJson")
    val minifyJSON: Boolean,

    /**
     * Next Unique integer ID available
     */
    val nextUid: Int,

    /**
     * File naming pattern for exported PNGs
     */
    val pngFilePattern: String? = null,

    /**
     * Height of the world grid in pixels.
     */
    val worldGridHeight: Int,

    /**
     * Width of the world grid in pixels.
     */
    val worldGridWidth: Int,

    /**
     * An enum that describes how levels are organized in this project (ie. linearly or in a 2D
     * space). Possible values: `Free`, `GridVania`, `LinearHorizontal`, `LinearVertical`
     */
    val worldLayout: WorldLayout
)

/**
 * A structure containing all the definitions of this project
 *
 * If you're writing your own LDtk importer, you should probably just ignore *most* stuff in
 * the `defs` section, as it contains data that are mostly important to the editor. To keep
 * you away from the `defs` section and avoid some unnecessary JSON parsing, important data
 * from definitions is often duplicated in fields prefixed with a double underscore (eg.
 * `__identifier` or `__type`).  The 2 only definition types you might need here are
 * **Tilesets** and **Enums**.
 */
@Serializable
data class Definitions(
    /**
     * All entities, including their custom fields
     */
    val entities: List<EntityDefinition>,

    val enums: List<EnumDefinition>,

    /**
     * Note: external enums are exactly the same as `enums`, except they have a `relPath` to
     * point to an external source file.
     */
    val externalEnums: List<EnumDefinition>,

    val layers: List<LayerDefinition>,

    /**
     * An array containing all custom fields available to all levels.
     */
    val levelFields: List<FieldDefinition>,

    val tilesets: List<TilesetDefinition>
)

@Serializable
data class EntityDefinition(
    /**
     * Base entity color
     */
    val color: String,

    /**
     * Array of field definitions
     */
    val fieldDefs: List<FieldDefinition>,

    val fillOpacity: Float,

    /**
     * Pixel height
     */
    val height: Int,

    val hollow: Boolean,

    /**
     * Unique String identifier
     */
    val identifier: String,

    /**
     * Only applies to entities resizable on both X/Y. If TRUE, the entity instance width/height
     * will keep the same aspect ratio as the definition.
     */
    val keepAspectRatio: Boolean,

    /**
     * Possible values: `DiscardOldOnes`, `PreventAdding`, `MoveLastOne`
     */
    val limitBehavior: LimitBehavior,

    /**
     * If TRUE, the maxCount is a "per world" limit, if FALSE, it's a "per level". Possible
     * values: `PerLayer`, `PerLevel`, `PerWorld`
     */
    val limitScope: LimitScope,

    val lineOpacity: Float,

    /**
     * Max instances count
     */
    val maxCount: Int,

    /**
     * Pivot X coordinate (from 0 to 1.0)
     */
    val pivotX: Float,

    /**
     * Pivot Y coordinate (from 0 to 1.0)
     */
    val pivotY: Float,

    /**
     * Possible values: `Rectangle`, `Ellipse`, `Tile`, `Cross`
     */
    val renderMode: RenderMode,

    /**
     * If TRUE, the entity instances will be resizable horizontally
     */
    val resizableX: Boolean,

    /**
     * If TRUE, the entity instances will be resizable vertically
     */
    val resizableY: Boolean,

    /**
     * Display entity name in editor
     */
    val showName: Boolean,

    /**
     * An array of strings that classifies this entity
     */
    val tags: List<String>,

    /**
     * Tile ID used for optional tile display
     */
    @SerialName("tileId")
    val tileID: Int? = null,

    /**
     * Possible values: `Cover`, `FitInside`, `Repeat`, `Stretch`
     */
    val tileRenderMode: TileRenderMode,

    /**
     * Tileset ID used for optional tile display
     */
    @SerialName("tilesetId")
    val tilesetID: Int? = null,

    /**
     * Unique Int identifier
     */
    val uid: Int,

    /**
     * Pixel width
     */
    val width: Int
)

/**
 * This section is mostly only intended for the LDtk editor app itself. You can safely
 * ignore it.
 */
@Serializable
data class FieldDefinition(
    /**
     * Human readable value type (eg. `Int`, `Float`, `Point`, etc.). If the field is an array,
     * this field will look like `Array<...>` (eg. `Array<Int>`, `Array<Point>` etc.)
     */
    @SerialName("__type")
    val type: String,

    /**
     * Optional list of accepted file extensions for FilePath value type. Includes the dot:
     * `.ext`
     */
    val acceptFileTypes: List<String>? = null,

    /**
     * Array max length
     */
    val arrayMaxLength: Int? = null,

    /**
     * Array min length
     */
    val arrayMinLength: Int? = null,

    /**
     * TRUE if the value can be null. For arrays, TRUE means it can contain null values
     * (exception: array of Points can't have null values).
     */
    val canBeNull: Boolean,

    /**
     * Default value if selected value is null or invalid.
     */
    val defaultOverride: DefaultOverrideInfo? = null,

    val editorAlwaysShow: Boolean,
    val editorCutLongValues: Boolean,

    /**
     * Possible values: `Hidden`, `ValueOnly`, `NameAndValue`, `EntityTile`, `PointStar`,
     * `PointPath`, `RadiusPx`, `RadiusGrid`
     */
    val editorDisplayMode: EditorDisplayMode,

    /**
     * Possible values: `Above`, `Center`, `Beneath`
     */
    val editorDisplayPos: EditorDisplayPos,

    /**
     * Unique String identifier
     */
    val identifier: String,

    /**
     * TRUE if the value is an array of multiple values
     */
    val isArray: Boolean,

    /**
     * Max limit for value, if applicable
     */
    val max: Float? = null,

    /**
     * Min limit for value, if applicable
     */
    val min: Float? = null,

    /**
     * Optional regular expression that needs to be matched to accept values. Expected format:
     * `/some_reg_ex/g`, with optional "i" flag.
     */
    val regex: String? = null,

    /**
     * Possible values: &lt;`null`&gt;, `LangPython`, `LangRuby`, `LangJS`, `LangLua`, `LangC`,
     * `LangHaxe`, `LangMarkdown`, `LangJson`, `LangXml`
     */
    val textLangageMode: TextLangageMode? = null,

    /**
     * Internal type enum
     */
    @SerialName("type")
    val fieldDefinitionType: JsonObject?,

    /**
     * Unique Intidentifier
     */
    val uid: Int
)


@Serializable
data class DefaultOverrideInfo(val id: String, val params: List<@Polymorphic Any>)

/**
 * Possible values: `Hidden`, `ValueOnly`, `NameAndValue`, `EntityTile`, `PointStar`,
 * `PointPath`, `RadiusPx`, `RadiusGrid`
 */
@Serializable
enum class EditorDisplayMode(val value: String) {
    EntityTile("EntityTile"),
    Hidden("Hidden"),
    NameAndValue("NameAndValue"),
    PointPath("PointPath"),
    PointStar("PointStar"),
    RadiusGrid("RadiusGrid"),
    RadiusPx("RadiusPx"),
    ValueOnly("ValueOnly");

    companion object : KSerializer<EditorDisplayMode> {
        override val descriptor: SerialDescriptor
            get() {
                return PrimitiveSerialDescriptor("quicktype.EditorDisplayMode", PrimitiveKind.STRING)
            }

        override fun deserialize(decoder: Decoder): EditorDisplayMode = when (val value = decoder.decodeString()) {
            "EntityTile" -> EntityTile
            "Hidden" -> Hidden
            "NameAndValue" -> NameAndValue
            "PointPath" -> PointPath
            "PointStar" -> PointStar
            "RadiusGrid" -> RadiusGrid
            "RadiusPx" -> RadiusPx
            "ValueOnly" -> ValueOnly
            else -> throw IllegalArgumentException("EditorDisplayMode could not parse: $value")
        }

        override fun serialize(encoder: Encoder, value: EditorDisplayMode) {
            return encoder.encodeString(value.value)
        }
    }
}

/**
 * Possible values: `Above`, `Center`, `Beneath`
 */
@Serializable
enum class EditorDisplayPos(val value: String) {
    Above("Above"),
    Beneath("Beneath"),
    Center("Center");

    companion object : KSerializer<EditorDisplayPos> {
        override val descriptor: SerialDescriptor
            get() {
                return PrimitiveSerialDescriptor("quicktype.EditorDisplayPos", PrimitiveKind.STRING)
            }

        override fun deserialize(decoder: Decoder): EditorDisplayPos = when (val value = decoder.decodeString()) {
            "Above" -> Above
            "Beneath" -> Beneath
            "Center" -> Center
            else -> throw IllegalArgumentException("EditorDisplayPos could not parse: $value")
        }

        override fun serialize(encoder: Encoder, value: EditorDisplayPos) {
            return encoder.encodeString(value.value)
        }
    }
}

@Serializable
enum class TextLangageMode(val value: String) {
    LangC("LangC"),
    LangHaxe("LangHaxe"),
    LangJS("LangJS"),
    LangJSON("LangJson"),
    LangLua("LangLua"),
    LangMarkdown("LangMarkdown"),
    LangPython("LangPython"),
    LangRuby("LangRuby"),
    LangXML("LangXml");

    companion object : KSerializer<TextLangageMode> {
        override val descriptor: SerialDescriptor
            get() {
                return PrimitiveSerialDescriptor("quicktype.TextLangageMode", PrimitiveKind.STRING)
            }

        override fun deserialize(decoder: Decoder): TextLangageMode = when (val value = decoder.decodeString()) {
            "LangC" -> LangC
            "LangHaxe" -> LangHaxe
            "LangJS" -> LangJS
            "LangJson" -> LangJSON
            "LangLua" -> LangLua
            "LangMarkdown" -> LangMarkdown
            "LangPython" -> LangPython
            "LangRuby" -> LangRuby
            "LangXml" -> LangXML
            else -> throw IllegalArgumentException("TextLangageMode could not parse: $value")
        }

        override fun serialize(encoder: Encoder, value: TextLangageMode) {
            return encoder.encodeString(value.value)
        }
    }
}

/**
 * Possible values: `DiscardOldOnes`, `PreventAdding`, `MoveLastOne`
 */
@Serializable
enum class LimitBehavior(val value: String) {
    DiscardOldOnes("DiscardOldOnes"),
    MoveLastOne("MoveLastOne"),
    PreventAdding("PreventAdding");

    companion object : KSerializer<LimitBehavior> {
        override val descriptor: SerialDescriptor
            get() {
                return PrimitiveSerialDescriptor("quicktype.LimitBehavior", PrimitiveKind.STRING)
            }

        override fun deserialize(decoder: Decoder): LimitBehavior = when (val value = decoder.decodeString()) {
            "DiscardOldOnes" -> DiscardOldOnes
            "MoveLastOne" -> MoveLastOne
            "PreventAdding" -> PreventAdding
            else -> throw IllegalArgumentException("LimitBehavior could not parse: $value")
        }

        override fun serialize(encoder: Encoder, value: LimitBehavior) {
            return encoder.encodeString(value.value)
        }
    }
}

/**
 * If TRUE, the maxCount is a "per world" limit, if FALSE, it's a "per level". Possible
 * values: `PerLayer`, `PerLevel`, `PerWorld`
 */
@Serializable
enum class LimitScope(val value: String) {
    PerLayer("PerLayer"),
    PerLevel("PerLevel"),
    PerWorld("PerWorld");

    companion object : KSerializer<LimitScope> {
        override val descriptor: SerialDescriptor
            get() {
                return PrimitiveSerialDescriptor("quicktype.LimitScope", PrimitiveKind.STRING)
            }

        override fun deserialize(decoder: Decoder): LimitScope = when (val value = decoder.decodeString()) {
            "PerLayer" -> PerLayer
            "PerLevel" -> PerLevel
            "PerWorld" -> PerWorld
            else -> throw IllegalArgumentException("LimitScope could not parse: $value")
        }

        override fun serialize(encoder: Encoder, value: LimitScope) {
            return encoder.encodeString(value.value)
        }
    }
}

/**
 * Possible values: `Rectangle`, `Ellipse`, `Tile`, `Cross`
 */
@Serializable
enum class RenderMode(val value: String) {
    Cross("Cross"),
    Ellipse("Ellipse"),
    Rectangle("Rectangle"),
    Tile("Tile");

    companion object : KSerializer<RenderMode> {
        override val descriptor: SerialDescriptor
            get() {
                return PrimitiveSerialDescriptor("quicktype.RenderMode", PrimitiveKind.STRING)
            }

        override fun deserialize(decoder: Decoder): RenderMode = when (val value = decoder.decodeString()) {
            "Cross" -> Cross
            "Ellipse" -> Ellipse
            "Rectangle" -> Rectangle
            "Tile" -> Tile
            else -> throw IllegalArgumentException("RenderMode could not parse: $value")
        }

        override fun serialize(encoder: Encoder, value: RenderMode) {
            return encoder.encodeString(value.value)
        }
    }
}

/**
 * Possible values: `Cover`, `FitInside`, `Repeat`, `Stretch`
 */
@Serializable
enum class TileRenderMode(val value: String) {
    Cover("Cover"),
    FitInside("FitInside"),
    Repeat("Repeat"),
    Stretch("Stretch");

    companion object : KSerializer<TileRenderMode> {
        override val descriptor: SerialDescriptor
            get() {
                return PrimitiveSerialDescriptor("quicktype.TileRenderMode", PrimitiveKind.STRING)
            }

        override fun deserialize(decoder: Decoder): TileRenderMode = when (val value = decoder.decodeString()) {
            "Cover" -> Cover
            "FitInside" -> FitInside
            "Repeat" -> Repeat
            "Stretch" -> Stretch
            else -> throw IllegalArgumentException("TileRenderMode could not parse: $value")
        }

        override fun serialize(encoder: Encoder, value: TileRenderMode) {
            return encoder.encodeString(value.value)
        }
    }
}

@Serializable
data class EnumDefinition(
    val externalFileChecksum: String? = null,

    /**
     * Relative path to the external file providing this Enum
     */
    val externalRelPath: String? = null,

    /**
     * Tileset UID if provided
     */
    val iconTilesetUid: Int? = null,

    /**
     * Unique String identifier
     */
    val identifier: String,

    /**
     * Unique Int identifier
     */
    val uid: Int,

    /**
     * All possible enum values, with their optional Tile infos.
     */
    val values: List<EnumValueDefinition>
)

@Serializable
data class EnumValueDefinition(
    /**
     * An array of 4 Int values that refers to the tile in the tileset image: `[ x, y, width,
     * height ]`
     */
    @SerialName("__tileSrcRect")
    val tileSrcRect: List<Int>,

    /**
     * Enum value
     */
    val id: String,

    /**
     * The optional ID of the tile
     */
    @SerialName("tileId")
    val tileID: Int? = null
)

@Serializable
data class LayerDefinition(
    /**
     * Type of the layer (*IntGrid, Entities, Tiles or AutoLayer*)
     */
    @SerialName("__type")
    val type: String,

    /**
     * Contains all the auto-layer rule definitions.
     */
    val autoRuleGroups: JsonArray,

    val autoSourceLayerDefUid: Int? = null,

    /**
     * Reference to the Tileset UID being used by this auto-layer rules
     */
    val autoTilesetDefUid: Int? = null,

    /**
     * Opacity of the layer (0 to 1.0)
     */
    val displayOpacity: Float,

    /**
     * An array of tags to forbid some Entities in this layer
     */
    val excludedTags: List<String>,

    /**
     * Width and height of the grid in pixels
     */
    val gridSize: Int,

    /**
     * Unique String identifier
     */
    val identifier: String,

    /**
     * An array that defines extra optional info for each IntGrid value. The array is sorted
     * using value (ascending).
     */
    val intGridValues: List<IntGridValueDefinition>,

    /**
     * X offset of the layer, in pixels (IMPORTANT: this should be added to the `LayerInstance`
     * optional offset)
     */
    val pxOffsetX: Int,

    /**
     * Y offset of the layer, in pixels (IMPORTANT: this should be added to the `LayerInstance`
     * optional offset)
     */
    val pxOffsetY: Int,

    /**
     * An array of tags to filter Entities that can be added to this layer
     */
    val requiredTags: List<String>,

    /**
     * If the tiles are smaller or larger than the layer grid, the pivot value will be used to
     * position the tile relatively its grid cell.
     */
    val tilePivotX: Float,

    /**
     * If the tiles are smaller or larger than the layer grid, the pivot value will be used to
     * position the tile relatively its grid cell.
     */
    val tilePivotY: Float,

    /**
     * Reference to the Tileset UID being used by this Tile layer
     */
    val tilesetDefUid: Int? = null,

    /**
     * Type of the layer as Haxe Enum Possible values: `IntGrid`, `Entities`, `Tiles`,
     * `AutoLayer`
     */
    @SerialName("type")
    val layerDefinitionType: Type,

    /**
     * Unique Int identifier
     */
    val uid: Int
)

/**
 * IntGrid value definition
 */
@Serializable
data class IntGridValueDefinition(
    val color: String,

    /**
     * Unique String identifier
     */
    val identifier: String? = null,

    /**
     * The IntGrid value itself
     */
    val value: Int
)

/**
 * Type of the layer as Haxe Enum Possible values: `IntGrid`, `Entities`, `Tiles`,
 * `AutoLayer`
 */
@Serializable
enum class Type(val value: String) {
    AutoLayer("AutoLayer"),
    Entities("Entities"),
    IntGrid("IntGrid"),
    Tiles("Tiles");

    companion object : KSerializer<Type> {
        override val descriptor: SerialDescriptor
            get() {
                return PrimitiveSerialDescriptor("quicktype.Type", PrimitiveKind.STRING)
            }

        override fun deserialize(decoder: Decoder): Type = when (val value = decoder.decodeString()) {
            "AutoLayer" -> AutoLayer
            "Entities" -> Entities
            "IntGrid" -> IntGrid
            "Tiles" -> Tiles
            else -> throw IllegalArgumentException("Type could not parse: $value")
        }

        override fun serialize(encoder: Encoder, value: Type) {
            return encoder.encodeString(value.value)
        }
    }
}

/**
 * The `Tileset` definition is the most important part among project definitions. It
 * contains some extra informations about each integrated tileset. If you only had to parse
 * one definition section, that would be the one.
 */
@Serializable
data class TilesetDefinition(
    /**
     * The following data is used internally for various optimizations. It's always synced with
     * source image changes.
     */
    val cachedPixelData: JsonObject? = null,

    /**
     * Unique String identifier
     */
    val identifier: String,

    /**
     * Distance in pixels from image borders
     */
    val padding: Int,

    /**
     * Image height in pixels
     */
    val pxHei: Int,

    /**
     * Image width in pixels
     */
    val pxWid: Int,

    /**
     * Path to the source file, relative to the current project JSON file
     */
    val relPath: String,

    /**
     * Array of group of tiles selections, only meant to be used in the editor
     */
    val savedSelections: JsonArray,

    /**
     * Space in pixels between all tiles
     */
    val spacing: Int,

    val tileGridSize: Int,

    /**
     * Unique Intidentifier
     */
    val uid: Int
)

@Serializable
enum class Flag(val value: String) {
    DiscardPreCSVIntGrid("DiscardPreCsvIntGrid"),
    IgnoreBackupSuggest("IgnoreBackupSuggest");

    companion object : KSerializer<Flag> {
        override val descriptor: SerialDescriptor
            get() {
                return PrimitiveSerialDescriptor("quicktype.Flag", PrimitiveKind.STRING)
            }

        override fun deserialize(decoder: Decoder): Flag = when (val value = decoder.decodeString()) {
            "DiscardPreCsvIntGrid" -> DiscardPreCSVIntGrid
            "IgnoreBackupSuggest" -> IgnoreBackupSuggest
            else -> throw IllegalArgumentException("Flag could not parse: $value")
        }

        override fun serialize(encoder: Encoder, value: Flag) {
            return encoder.encodeString(value.value)
        }
    }
}

/**
 * This section contains all the level data. It can be found in 2 distinct forms, depending
 * on Project current settings:  - If "*Separate level files*" is **disabled** (default):
 * full level data is *embedded* inside the main Project JSON file, - If "*Separate level
 * files*" is **enabled**: level data is stored in *separate* standalone `.ldtkl` files (one
 * per level). In this case, the main Project JSON file will still contain most level data,
 * except heavy sections, like the `layerInstances` array (which will be null). The
 * `externalRelPath` string points to the `ldtkl` file.  A `ldtkl` file is just a JSON file
 * containing exactly what is described below.
 */
@Serializable
data class LevelDefinition(
    /**
     * Background color of the level (same as `bgColor`, except the default value is
     * automatically used here if its value is `null`)
     */
    @SerialName("__bgColor")
    val bgColor: String,

    /**
     * Position informations of the background image, if there is one.
     */
    @SerialName("__bgPos")
    val bgPos: LevelBackgroundPosition? = null,

    /**
     * An array listing all other levels touching this one on the world map. In "linear" world
     * layouts, this array is populated with previous/next levels in array, and `dir` depends on
     * the linear horizontal/vertical layout.
     */
    @SerialName("__neighbours")
    val neighbours: List<NeighbourLevel>?,

    /**
     * Background color of the level. If `null`, the project `defaultLevelBgColor` should be
     * used.
     */
    @SerialName("bgColor")
    val levelBgColor: String? = null,

    /**
     * Background image X pivot (0-1)
     */
    val bgPivotX: Float,

    /**
     * Background image Y pivot (0-1)
     */
    val bgPivotY: Float,

    /**
     * An enum defining the way the background image (if any) is positioned on the level. See
     * `__bgPos` for resulting position info. Possible values: &lt;`null`&gt;, `Unscaled`,
     * `Contain`, `Cover`, `CoverDirty`
     */
    @SerialName("bgPos")
    val levelBgPos: BgPos? = null,

    /**
     * The *optional* relative path to the level background image.
     */
    val bgRelPath: String? = null,

    /**
     * This value is not null if the project option "*Save levels separately*" is enabled. In
     * this case, this **relative** path points to the level Json file.
     */
    val externalRelPath: String? = null,

    /**
     * An array containing this level custom field values.
     */
    val fieldInstances: List<FieldInstance>,

    /**
     * Unique String identifier
     */
    val identifier: String,

    /**
     * An array containing all Layer instances. **IMPORTANT**: if the project option "*Save
     * levels separately*" is enabled, this field will be `null`.<br/>  This array is **sorted
     * in display order**: the 1st layer is the top-most and the last is behind.
     */
    val layerInstances: List<LayerInstance>? = null,

    /**
     * Height of the level in pixels
     */
    val pxHei: Int,

    /**
     * Width of the level in pixels
     */
    val pxWid: Int,

    /**
     * Unique Int identifier
     */
    val uid: Int,

    /**
     * World X coordinate in pixels
     */
    val worldX: Int,

    /**
     * World Y coordinate in pixels
     */
    val worldY: Int
)

/**
 * Level background image position info
 */
@Serializable
data class LevelBackgroundPosition(
    /**
     * An array of 4 float values describing the cropped sub-rectangle of the displayed
     * background image. This cropping happens when original is larger than the level bounds.
     * Array format: `[ cropX, cropY, cropWidth, cropHeight ]`
     */
    val cropRect: List<Float>,

    /**
     * An array containing the `[scaleX,scaleY]` values of the **cropped** background image,
     * depending on `bgPos` option.
     */
    val scale: List<Float>,

    /**
     * An array containing the `[x,y]` pixel coordinates of the top-left corner of the
     * **cropped** background image, depending on `bgPos` option.
     */
    val topLeftPx: List<Int>
)

@Serializable
data class FieldInstance(
    /**
     * Field definition identifier
     */
    @SerialName("__identifier")
    val identifier: String,

    /**
     * Type of the field, such as `Int`, `Float`, `Enum(my_enum_name)`, `Bool`, etc.
     */
    @SerialName("__type")
    val type: String,

    /**
     * Actual value of the field instance. The value type may vary, depending on `__type`
     * (Integer, Boolean, String etc.)<br/>  It can also be an `Array` of those same types.
     */
    @SerialName("__value")
    val value: JsonObject?,

    /**
     * Reference of the **Field definition** UID
     */
    val defUid: Int,

    /**
     * Editor internal raw values
     */
    val realEditorValues: JsonArray
)

@Serializable
data class LayerInstance(
    /**
     * Grid-based height
     */
    @SerialName("__cHei")
    val cHei: Int,

    /**
     * Grid-based width
     */
    @SerialName("__cWid")
    val cWid: Int,

    /**
     * Grid size
     */
    @SerialName("__gridSize")
    val gridSize: Int,

    /**
     * Layer definition identifier
     */
    @SerialName("__identifier")
    val identifier: String,

    /**
     * Layer opacity as Float [0-1]
     */
    @SerialName("__opacity")
    val opacity: Float,

    /**
     * Total layer X pixel offset, including both instance and definition offsets.
     */
    @SerialName("__pxTotalOffsetX")
    val pxTotalOffsetX: Int,

    /**
     * Total layer Y pixel offset, including both instance and definition offsets.
     */
    @SerialName("__pxTotalOffsetY")
    val pxTotalOffsetY: Int,

    /**
     * The definition UID of corresponding Tileset, if any.
     */
    @SerialName("__tilesetDefUid")
    val tilesetDefUid: Int? = null,

    /**
     * The relative path to corresponding Tileset, if any.
     */
    @SerialName("__tilesetRelPath")
    val tilesetRelPath: String? = null,

    /**
     * Layer type (possible values: IntGrid, Entities, Tiles or AutoLayer)
     */
    @SerialName("__type")
    val type: String,

    /**
     * An array containing all tiles generated by Auto-layer rules. The array is already sorted
     * in display order (ie. 1st tile is beneath 2nd, which is beneath 3rd etc.).<br/><br/>
     * Note: if multiple tiles are stacked in the same cell as the result of different rules,
     * all tiles behind opaque ones will be discarded.
     */
    val autoLayerTiles: List<TileInstance>,

    val entityInstances: List<EntityInstance>,
    val gridTiles: List<TileInstance>,

    /**
     * **WARNING**: this deprecated value will be *removed* completely on version 0.9.0+
     * Replaced by: `intGridCsv`
     */
    val intGrid: List<IntGridValueInstance>? = null,

    /**
     * A list of all values in the IntGrid layer, stored from left to right, and top to bottom
     * (ie. first row from left to right, followed by second row, etc). `0` means "empty cell"
     * and IntGrid values start at 1. This array size is `__cWid` x `__cHei` cells.
     */
    @SerialName("intGridCsv")
    val intGridCSV: List<Int>?,

    /**
     * Reference the Layer definition UID
     */
    val layerDefUid: Int,

    /**
     * Reference to the UID of the level containing this layer instance
     */
    @SerialName("levelId")
    val levelID: Int,

    /**
     * This layer can use another tileset by overriding the tileset UID here.
     */
    val overrideTilesetUid: Int? = null,

    /**
     * X offset in pixels to render this layer, usually 0 (IMPORTANT: this should be added to
     * the `LayerDef` optional offset, see `__pxTotalOffsetX`)
     */
    val pxOffsetX: Int,

    /**
     * Y offset in pixels to render this layer, usually 0 (IMPORTANT: this should be added to
     * the `LayerDef` optional offset, see `__pxTotalOffsetY`)
     */
    val pxOffsetY: Int,

    /**
     * Random seed used for Auto-Layers rendering
     */
    val seed: Int,

    /**
     * Layer instance visibility
     */
    val visible: Boolean
)

/**
 * This structure represents a single tile from a given Tileset.
 */
@Serializable
data class TileInstance(
    /**
     * Internal data used by the editor.<br/>  For auto-layer tiles: `[ruleId, coordId]`.<br/>
     * For tile-layer tiles: `[coordId]`.
     */
    val d: List<Int>,

    /**
     * "Flip bits", a 2-bits integer to represent the mirror transformations of the tile.<br/>
     * - Bit 0 = X flip<br/>   - Bit 1 = Y flip<br/>   Examples: f=0 (no flip), f=1 (X flip
     * only), f=2 (Y flip only), f=3 (both flips)
     */
    val f: Int,

    /**
     * Pixel coordinates of the tile in the **layer** (`[x,y]` format). Don't forget optional
     * layer offsets, if they exist!
     */
    val px: List<Int>,

    /**
     * Pixel coordinates of the tile in the **tileset** (`[x,y]` format)
     */
    val src: List<Int>,

    /**
     * The *Tile ID* in the corresponding tileset.
     */
    val t: Int
)

@Serializable
data class EntityInstance(
    /**
     * Grid-based coordinates (`[x,y]` format)
     */
    @SerialName("__grid")
    val grid: List<Int>,

    /**
     * Entity definition identifier
     */
    @SerialName("__identifier")
    val identifier: String,

    /**
     * Pivot coordinates  (`[x,y]` format, values are from 0 to 1) of the Entity
     */
    @SerialName("__pivot")
    val pivot: List<Float>,

    /**
     * Optional Tile used to display this entity (it could either be the default Entity tile, or
     * some tile provided by a field value, like an Enum).
     */
    @SerialName("__tile")
    val tile: EntityInstanceTile? = null,

    /**
     * Reference of the **Entity definition** UID
     */
    val defUid: Int,

    /**
     * An array of all custom fields and their values.
     */
    val fieldInstances: List<FieldInstance>,

    /**
     * Entity height in pixels. For non-resizable entities, it will be the same as Entity
     * definition.
     */
    val height: Int,

    /**
     * Pixel coordinates (`[x,y]` format) in current level coordinate space. Don't forget
     * optional layer offsets, if they exist!
     */
    val px: List<Int>,

    /**
     * Entity width in pixels. For non-resizable entities, it will be the same as Entity
     * definition.
     */
    val width: Int
)

/**
 * Tile data in an Entity instance
 */
@Serializable
data class EntityInstanceTile(
    /**
     * An array of 4 Int values that refers to the tile in the tileset image: `[ x, y, width,
     * height ]`
     */
    val srcRect: List<Int>,

    /**
     * Tileset ID
     */
    val tilesetUid: Int
)

/**
 * IntGrid value instance
 */
@Serializable
data class IntGridValueInstance(
    /**
     * Coordinate ID in the layer grid
     */
    @SerialName("coordId")
    val coordID: Int,

    /**
     * IntGrid value
     */
    val v: Int
)

@Serializable
enum class BgPos(val value: String) {
    Contain("Contain"),
    Cover("Cover"),
    CoverDirty("CoverDirty"),
    Unscaled("Unscaled");

    companion object : KSerializer<BgPos> {
        override val descriptor: SerialDescriptor
            get() {
                return PrimitiveSerialDescriptor("quicktype.BgPos", PrimitiveKind.STRING)
            }

        override fun deserialize(decoder: Decoder): BgPos = when (val value = decoder.decodeString()) {
            "Contain" -> Contain
            "Cover" -> Cover
            "CoverDirty" -> CoverDirty
            "Unscaled" -> Unscaled
            else -> throw IllegalArgumentException("BgPos could not parse: $value")
        }

        override fun serialize(encoder: Encoder, value: BgPos) {
            return encoder.encodeString(value.value)
        }
    }
}

/**
 * Nearby level info
 */
@Serializable
data class NeighbourLevel(
    /**
     * A single lowercase character tipping on the level location (`n`orth, `s`outh, `w`est,
     * `e`ast).
     */
    val dir: String,

    val levelUid: Int
)

/**
 * An enum that describes how levels are organized in this project (ie. linearly or in a 2D
 * space). Possible values: `Free`, `GridVania`, `LinearHorizontal`, `LinearVertical`
 */
@Serializable
enum class WorldLayout(val value: String) {
    Free("Free"),
    GridVania("GridVania"),
    LinearHorizontal("LinearHorizontal"),
    LinearVertical("LinearVertical");

    companion object : KSerializer<WorldLayout> {
        override val descriptor: SerialDescriptor
            get() {
                return PrimitiveSerialDescriptor("quicktype.WorldLayout", PrimitiveKind.STRING)
            }

        override fun deserialize(decoder: Decoder): WorldLayout = when (val value = decoder.decodeString()) {
            "Free" -> Free
            "GridVania" -> GridVania
            "LinearHorizontal" -> LinearHorizontal
            "LinearVertical" -> LinearVertical
            else -> throw IllegalArgumentException("WorldLayout could not parse: $value")
        }

        override fun serialize(encoder: Encoder, value: WorldLayout) {
            return encoder.encodeString(value.value)
        }
    }
}
