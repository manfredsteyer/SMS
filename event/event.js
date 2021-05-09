const express = require('express');
const query = require('./query');
const eventPublished = require('./event-published');
const router = express.Router();

router.get('/', async function (req, res) {
    try {
        const result = await query(`select * from events order by title`);
        res.send(result);
    }
    catch (err) {
        res.status(500).send(err);
    }
});

router.delete('/:id', async function (req, res) {
    const id = req.params.id;

    try {
        const result = await query(`delete from events where id = ?`, [id]);
        res.sendStatus(200);
    }
    catch (err) {
        res.status(500).send(err);
    }
});

router.post('/', async function (req, res) {
    const body = req.body;

    if (!body.title || !body.description) {
        res.status(400).send({ message: 'title and description expected!' });
        return;
    }

    try {
        const result = await query(
            `insert into events(title, description) values(?, ?)`, 
            [body.title, body.description]);
        
        const event = {id: result.insertId, ...body};

        eventPublished(event);

        res.status(201).send(event);
    }
    catch (err) {
        res.status(500).send(err);
    }
});

module.exports = router;