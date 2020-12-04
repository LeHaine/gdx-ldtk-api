package com.lehaine.ldtk

open class LayerEntities(json: LayerInstanceJson) : Layer(json) {

    private val _entities = mutableListOf<Entity>()
    val entities get() = _entities.toList()

    init {

    }

    protected fun instantiateEntities(classPath: String) {
        json.entityInstances.forEach { entityInstanceJson ->
            instantiateEntity(classPath, entityInstanceJson).also { _entities.add(it) }
        }
    }

    protected fun instantiateEntity(classPath: String, json: EntityInstanceJson): Entity {
        val clazz = Class.forName("$classPath\$Entity_${json.__identifier}")
        val entity =
            clazz.getDeclaredConstructor(EntityInstanceJson::class.java).newInstance(json)
                    as Entity
        json.fieldInstances.forEach {
            if ("LocalEnum" in it.__type) {
                val type = it.__type.substring(it.__type.indexOf(".") + 1)
                val field = clazz.getDeclaredField(it.__identifier)
                val setter = clazz.getDeclaredMethod(
                    "set${it.__identifier.capitalize()}",
                    Class.forName("$classPath\$$type")
                )
                val valueOf = field.type.getMethod("valueOf", String::class.java)
                val value = valueOf.invoke(null, it.__value)
                setter.invoke(entity, value)
            } else {
                val classType = when (it.__type) {
                    "Int" -> Int::class.java
                    "Bool" -> Boolean::class.java
                    "Float" -> Double::class.java
                    "String" -> String::class.java
                    "Color" -> Color::class.java
                    "Point" -> Point::class.java
                    else -> {
                        println("Unsupported type: ${it.__type}")
                        null
                    }
                }
                if (classType != null) {
                    val setter = clazz.getDeclaredMethod(
                        "set${it.__identifier.capitalize()}",
                        classType
                    )
                    val value = when (it.__type) {
                        "Int" -> {
                            (it.__value as Double).toInt()
                        }
                        "Color" -> {
                            val value = Project.hexToInt(it.__value as String)
                            val hexValue = it.__value
                            Color(value, hexValue)
                        }
                        "Point" -> {
                            if (it.__value != null) {
                                val map = it.__value as Map<*, *>
                                val cx = (map["cx"] as Double).toInt()
                                val cy = (map["cy"] as Double).toInt()
                                Point(cx, cy)
                            } else {
                                null
                            }
                        }
                        else -> {
                            it.__value
                        }
                    }
                    setter.invoke(entity, value)
                }
            }
        }
        return entity
    }

    override fun toString(): String {
        return "LayerEntities(entities=$entities)"
    }

}