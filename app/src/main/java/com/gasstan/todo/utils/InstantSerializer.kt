package com.gasstan.todo.utils

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant
import java.time.LocalDateTime

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = LocalDateTime::class)
object InstantSerializer : KSerializer<Instant> {

  override fun serialize(encoder: Encoder, value: Instant) {
    encoder.encodeString(value.toString())
  }

  @RequiresApi(Build.VERSION_CODES.O)
  override fun deserialize(decoder: Decoder): Instant =
    Instant.parse(decoder.decodeString())

}
