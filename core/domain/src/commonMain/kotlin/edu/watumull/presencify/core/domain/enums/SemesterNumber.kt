package edu.watumull.presencify.core.domain.enums

import edu.watumull.presencify.core.domain.DisplayLabelProvider
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = SemesterNumberSerializer::class)
enum class SemesterNumber(val value: Int) : DisplayLabelProvider {
    SEMESTER_1(1),
    SEMESTER_2(2),
    SEMESTER_3(3),
    SEMESTER_4(4),
    SEMESTER_5(5),
    SEMESTER_6(6),
    SEMESTER_7(7),
    SEMESTER_8(8);

    override fun toDisplayLabel(): String = "Semester $value"

    companion object {
        fun fromValue(value: Int): SemesterNumber? = entries.find { it.value == value }
    }
}

object SemesterNumberSerializer : KSerializer<SemesterNumber> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("SemesterNumber", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: SemesterNumber) {
        encoder.encodeInt(value.value)
    }

    override fun deserialize(decoder: Decoder): SemesterNumber {
        val value = decoder.decodeInt()
        return SemesterNumber.fromValue(value) ?: throw SerializationException("Unknown semester number: $value")
    }
}