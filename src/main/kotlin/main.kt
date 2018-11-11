import com.github.javafaker.Faker
import org.apache.kafka.clients.producer.*

fun main(args: Array<String>) {
  val producer = createProducer("localhost:9092")

  val faker = Faker()
  val fakePerson = Person(
    firstName = faker.name().firstName(),
    lastName = faker.name().lastName(),
    birthDate = faker.date().birthday()
  )

  val fakePersonJson = jsonMapper.writeValueAsString(fakePerson)
  val futureResult = producer.send(ProducerRecord(personsTopic, fakePersonJson))
  val x = futureResult.get()
  println("DONE: $x")
}