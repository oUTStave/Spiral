package org.abimon.spiral.core.objects.models.collada

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

@JsonRootName("triangles")
class ColladaTrianglesPojo(
        @JacksonXmlProperty(isAttribute = true)
        val name: String? = null,
        @JacksonXmlProperty(isAttribute = true)
        val count: Int,
        @JacksonXmlProperty(isAttribute = true)
        val material: String? = null,

        val input: List<ColladaInputSharedPojo>
) {
    constructor(input: List<ColladaInputSharedPojo>, triangles: IntArray, material: String? = null) : this(input = input, count = triangles.size / 3, material = material) {
        this.triangles = triangles
    }

    @JsonProperty("p")
    private var trianglesString: String? = null

    var triangles: IntArray
        @JsonIgnore
        get() = trianglesString?.split(' ')?.map(String::toInt)?.toIntArray() ?: intArrayOf()
        set(intValues) {
            trianglesString = intValues.joinToString(" ", transform = Int::toString)
        }

    override fun toString(): String {
        return "ColladaTrianglesPojo(name=$name, count=$count, material=$material, input=$input, trianglesString=$trianglesString, p=${triangles.joinToString()})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ColladaTrianglesPojo) return false

        if (name != other.name) return false
        if (count != other.count) return false
        if (material != other.material) return false
        if (input != other.input) return false
        if (trianglesString != other.trianglesString) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + count
        result = 31 * result + (material?.hashCode() ?: 0)
        result = 31 * result + input.hashCode()
        result = 31 * result + (trianglesString?.hashCode() ?: 0)
        return result
    }
}