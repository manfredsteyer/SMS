const query = require('./query');

module.exports = async function initDb() {

    await query('drop table if exists events');
    
    await query(`create table events(
        id bigint primary key auto_increment,
        title varchar(255),
        description text 
    )`);

    await query(`insert into 
        events(title, description)
        values('Spring Workshop', 'Everything about Spring')
    `);

    await query(`insert into 
        events(title, description)
        values('Kafka Workshop', 'Everything about Kafka')
    `);

    await query(`insert into 
        events(title, description)
        values('Node.js Workshop', 'Everything about Node.js')
    `);

}