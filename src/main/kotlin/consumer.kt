import org.apache.kafka.clients.producer.ProducerRecord
import java.time.*

fun main(args: Array<String>) {
  val consumer = createConsumer()
  val producer = createProducer()

  consumer.subscribe(listOf(personsTopic))
  while(true) {
    val records = consumer.poll(Duration.ofSeconds(1))
    records.iterator().forEach {
      val personJson = it.value()
      val person = jsonMapper.readValue(personJson, Person::class.java)
      val birthDateLocal = person.birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
      val age = Period.between(birthDateLocal, LocalDate.now()).years
      val future = producer.send(
        ProducerRecord(agesTopic, "${person.firstName} ${person.lastName}", "$age")
      )
      val got = future.get()
      println("DONE: $got")
    }
  }
}