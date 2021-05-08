'use strict';

const express = require('express');

// Constants
const PORT = 8080;
const HOST = '0.0.0.0';

// App
const app = express();

app.use(express.static('public'));


app.get('/test', (req, res) => {
  res.send('Hello World!');
});

app.get('/pingdb', (req, res) => {
    var mysql = require('mysql2');

    var con = mysql.createConnection({
      host: "db",
      user: "user",
      password: "password"
    });
    
    con.connect(function(err) {
      if (err) {
          res.send('error: ' + JSON.stringify(err));
          return;
      }
      res.send("Connected!");
    });});

app.listen(PORT, HOST);
console.log(`Running on http://${HOST}:${PORT}`);