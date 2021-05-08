const { Kafka } = require('kafkajs')

const kafka = new Kafka({
  clientId: 'event-nodejs-app',
  brokers: ['localhost:9092']
});

const producer = kafka.producer();

(async function () {
  await producer.connect();

  const r = await producer.send({
    topic: 'eventPublished',
    messages: [
      {
        value: JSON.stringify({
          event__Id: 7,
          start: new Date().toISOString(),
          end: new Date().toISOString(),
          title: 'Test Event!',
          ticketTypes: []
        }),
        // headers: {
        //   'targeType': 'com.example.ticket.model.Event'
        // }

      },
    ],
  });

  // const r = await producer.send({
  //   topic: 'ticketBooked',
  //   messages: [
  //     { 
  //         value: JSON.stringify({
  //           ticketTypeId: 4,
  //           purchaseDate: '2021-04-05',
  //           holder: {
  //             firstName: 'Hans',
  //             lastName: 'Mustermann'
  //           }
  //         })

  //     },
  //   ],
  // });

  console.log('r', r);

  await producer.disconnect();
})();