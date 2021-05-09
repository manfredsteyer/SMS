const { Kafka } = require('kafkajs')

const kafka = new Kafka({
    clientId: 'invoice',
    brokers: [process.env.KAFKA_HOST || 'broker:9092']
});

const consumer = kafka.consumer({ groupId: 'test-client4' });


process.on('SIGINT', function () {
    console.debug('Disconnecting from Kafka ...');
    consumer.disconnect().then(_ => process.exit()).catch(_ => process.exit());
});

(async function () {

    await consumer.connect();
    await consumer.subscribe({ topic: 'ticketBooked' });

    await consumer.run({
        autoCommit: true,
        eachMessage: async ({topic, partition, message}) => {
            if (message.value) {
                console.log(`
***************** 
Creating Invoice for Ticket
${message.value.toString()} 
*****************
`);
            }
        }
    });

})();

