package io.github.saifullah.xtream.ktx

import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive

/**
 * Extracts the content of a JsonElement as a String, returning null if the value is "NULL" (case-insensitive) or the element itself is null.
 */
internal fun JsonElement.contentOrNull(): String? {
    if (this !is JsonPrimitive) return null
    return this.jsonPrimitive.contentOrNull?.takeIf { !it.equals("NULL", ignoreCase = true) }
}

/**
 * Parses the JsonElement content as an Int, ensuring it contains only numeric characters.
 */
internal fun JsonElement.intOrNull(): Int? {
    return contentOrNull()?.takeIf { it.all { char -> char.isDigit() } }?.toIntOrNull()
}

/**
 * Parses the JsonElement content as a Long, ensuring it contains only numeric characters.
 */
internal fun JsonElement.longOrNull(): Long? {
    return contentOrNull()?.takeIf { it.all { char -> char.isDigit() } }?.toLongOrNull()
}

/**
 * Parses the JsonElement content as a Float, allowing numeric values with decimal points.
 */
internal fun JsonElement.floatOrNull(): Float? {
    return contentOrNull()?.takeIf { it.all { char -> char.isDigit() || char == '.' } }
        ?.toFloatOrNull()
}

/**
 * Parses the JsonElement content as a Double, allowing numeric values with decimal points.
 */
internal fun JsonElement.doubleOrNull(): Double? {
    return contentOrNull()?.takeIf { it.all { char -> char.isDigit() || char == '.' } }
        ?.toDoubleOrNull()
}

/**
 * Parses the JsonElement content as a Boolean.
 * Recognizes "true", "false" (case-insensitive), "1" (true), and "0" (false).
 */
internal fun JsonElement.booleanOrNull(): Boolean? {
    return contentOrNull()?.let {
        when (it) {
            "1" -> true
            "0" -> false
            else -> it.toBooleanStrictOrNull()
        }
    }
}

/**
 * Parses a JsonElement into a List<T>, supporting JsonArrays and JsonPrimitives.
 */
internal inline fun <reified T> JsonElement.parseToList(): List<T>? {
    return when (this) {
        is JsonArray -> parseToList()
        is JsonPrimitive -> parseToList()
        else -> emptyList()
    }
}

/**
 * Parses a JsonArray into a List<T> with type safety.
 */
internal inline fun <reified T> JsonArray.parseToList(): List<T> {
    return this.mapNotNull {
        when (T::class) {
            String::class -> it.contentOrNull() as? T
            Int::class -> it.intOrNull() as? T
            Long::class -> it.longOrNull() as? T
            Float::class -> it.floatOrNull() as? T
            Double::class -> it.doubleOrNull() as? T
            else -> null
        }
    }
}

/**
 * Parses a JsonPrimitive into a List<T> by splitting its content using common delimiters.
 * Supports space, comma, slash, dot, hyphen, and underscore as separators.
 */
internal inline fun <reified T> JsonPrimitive.parseToList(): List<T>? {
    return contentOrNull()?.split("\\s+|,|/|\\.|-|_".toRegex())?.mapNotNull {
        when (T::class) {
            String::class -> it as T
            Int::class -> it.takeIf { str -> str.all { char -> char.isDigit() } }
                ?.toIntOrNull() as? T

            Long::class -> it.takeIf { str -> str.all { char -> char.isDigit() } }
                ?.toLongOrNull() as? T

            Float::class -> it.takeIf { str -> str.all { char -> char.isDigit() || char == '.' } }
                ?.toFloatOrNull() as? T

            Double::class -> it.takeIf { str -> str.all { char -> char.isDigit() || char == '.' } }
                ?.toDoubleOrNull() as? T

            else -> null
        }
    }
}

/**
 * Safely casts the given [Decoder] to a [JsonDecoder].
 *
 * This function ensures that the decoder being used is a JSON-based decoder.
 * If the cast is unsuccessful, it throws a [SerializationException].
 *
 * @return The [JsonDecoder] instance.
 * @throws SerializationException if the decoder is not a [JsonDecoder].
 */
internal fun Decoder.safeJsonDecoder(): JsonDecoder {
    return this as? JsonDecoder ?: throw SerializationException("Expected JsonDecoder")
}
