package com.lehaine.gdx

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.lehaine.ldtk.LayerInstance
import com.lehaine.ldtk.LayerTiles
import com.lehaine.ldtk.Project
import com.lehaine.ldtk.TilesetDefinition

open class GdxLayerTiles(project: Project, tilesetDefJson: TilesetDefinition, json: LayerInstance) :
    LayerTiles(project, tilesetDefJson, json), Renderable {

    /**
     * Renders the layer. Due to LDtks coordinate system being flipped for LibGDX we need to negate the Y-pos and transform
     * it by the level height
     * @param batch the batch to use for drawing
     * @param targetTexture the target tiles texture to render
     */
    override fun render(batch: Batch, targetTexture: Texture?) {
        val tileset = tileset as? GdxTileset ?: error("Unable to load tileset for $identifier layer!")

        for (cy in 0..cHeight) {
            for (cx in 0..cWidth) {
                if (hasAnyTileAt(cx, cy)) {
                    getTileStackAt(cx, cy).forEach { tileInfo ->
                        tileset.getLDtkTile(
                            tileInfo.tileId, tileInfo.flipBits, targetTexture
                        )?.also {
                            batch.draw(
                                it.region.texture,
                                (cx * gridSize + pxTotalOffsetX).toFloat(),
                                -(cy * gridSize + pxTotalOffsetY - cHeight * gridSize).toFloat(), // LDtk is y-down, so invert it
                                0f,
                                0f,
                                gridSize.toFloat(),
                                gridSize.toFloat(),
                                1f,
                                1f,
                                0f,
                                it.region.regionX,
                                it.region.regionY,
                                it.region.regionWidth,
                                it.region.regionHeight,
                                it.flipX,
                                it.flipY
                            )
                        }
                    }
                }
            }
        }
    }
}