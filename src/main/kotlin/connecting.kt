import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.*
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

val defaultBrokers = "localhost:9092"

fun createProducer(brokers: String = defaultBrokers): Producer<String, String> {
  val props = Properties()
  props["bootstrap.servers"] = brokers
  props["key.serializer"] = StringSerializer::class.java.canonicalName
  props["value.serializer"] = StringSerializer::class.java.canonicalName
  return KafkaProducer<String, String>(props)
}

fun createConsumer(brokers: String = defaultBrokers): Consumer<String, String> {
  val props = Properties()
  props["bootstrap.servers"] = brokers
  props["group.id"] = "person-processor"
  props["key.deserializer"] = StringDeserializer::class.java
  props["value.deserializer"] = StringDeserializer::class.java
  return KafkaConsumer<String, String>(props)
}

val jsonMapper = ObjectMapper().apply {
  registerKotlinModule()
  disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
  dateFormat = StdDateFormat()
}

val personsTopic = "persons"
val agesTopic = "ages"