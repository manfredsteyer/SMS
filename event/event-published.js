const { Kafka } = require('kafkajs')

const kafka = new Kafka({
  clientId: 'event',
  brokers: [ process.env.KAFKA_HOST || 'localhost:9092']
});

const producer = kafka.producer();

module.exports = async function eventPublished(event) {
  await producer.connect();

  const r = await producer.send({
    topic: 'eventPublished',
    messages: [
      {
        value: JSON.stringify({
          eventId: event.id,
          start: new Date().toISOString(),
          end: new Date().toISOString(),
          title: event.title,
          ticketTypes: []
        }),
      },
    ],
  });

  console.log('r', r);

  await producer.disconnect();
}