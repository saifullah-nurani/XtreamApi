package io.github.saifullah.xtream.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class XtreamCategory(
    @SerialName("category_id") val categoryId: String, // 838
    @SerialName("category_name") val categoryName: String, // 2024
    @SerialName("parent_id") val parentId: Int // 0
)