package com.lehaine.gdx

import com.lehaine.ldtk.IntGridValue
import com.lehaine.ldtk.LayerInstanceJson
import com.lehaine.ldtk.LayerIntGrid

open class GdxLayerIntGrid(
    intGridValues: List<IntGridValue>,
    json: LayerInstanceJson
) : LayerIntGrid(intGridValues, json) {
}