const { Kafka } = require('kafkajs')

const kafka = new Kafka({
    clientId: 'event-nodejs-app',
    brokers: ['localhost:9092']
});

const consumer = kafka.consumer({ groupId: 'test-client4' });


process.on('SIGINT', function () {
    console.debug('Disconnecting from Kafka ...');
    consumer.disconnect().then(_ => process.exit()).catch(_ => process.exit());
});

(async function () {

    await consumer.connect();
    await consumer.subscribe({ topic: 'eventPublished' });

    await consumer.run({
        autoCommit: true,
        eachMessage: async ({topic, partition, message}) => {
            console.log('message', message);
            if (message.value) {
                console.log('message.value', message.value.toString());
            }
        }
    });

})();

