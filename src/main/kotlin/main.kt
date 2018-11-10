import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.ser.std.StringSerializer  //might be wrong
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.javafaker.Faker
import org.apache.kafka.clients.producer.*
import java.util.*

private fun createProducer(brokers: String): Producer<String, String> {
    val props = Properties()
    props["bootstrap.servers"] = brokers
    props["key.serializer"] = StringSerializer::class.java.canonicalName
    props["value.serializer"] = StringSerializer::class.java.canonicalName
    return KafkaProducer<String, String>(props)
}

val faker = Faker()
val fakePerson = Person(
  firstName = faker.name().firstName(),
  lastName = faker.name().lastName(),
  birthDate = faker.date().birthday()
)

val jsonMapper = ObjectMapper().apply {
  registerKotlinModule()
  disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
  dateFormat = StdDateFormat()
}