const { Kafka } = require('kafkajs')

const kafka = new Kafka({
    clientId: 'invoice',
    brokers: [process.env.KAFKA_HOST || 'broker:9092']
});

const consumer = kafka.consumer({ groupId: 'invoice' });

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

                const ticket = JSON.parse(message.value.toString('utf-8'));

                console.log(`
***********************************************************
Subject: Invoice


Dear ${ticket.holder.firstName},

Thanks for buying a ticket for ${ticket.ticketType.event.title}.

Please transfer the amount of € ${ticket.ticketType.price} 
to our bank account.

Looking forward to seeing you there.

Best wishes,
Your Workshop-Team
***********************************************************
`);
            }
        }
    });

})();

