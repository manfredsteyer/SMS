const mysql = require('mysql2');

const config = {
    "host": process.env.MYSQL_HOST || "localhost",
    "user": process.env.MYSQL_USER || "root",
    "database": process.env.MYSQL_DATABASE || "db_example",
    "password": process.env.MYSQL_PASSWORD || "P@ssw0rd"
};

module.exports = function query(sql, params) {
    const con = mysql.createConnection(config);
    
    return new Promise((resolve, reject) => {

        con.connect(function(err) {
            if (err) {
                console.error(err);
                reject(err);
                return;
            }
            con.query(sql, params, function (err, result) {
              if (err) {
                console.error(err);
                reject(err);
                return;
              }
              console.debug('result', result);
              resolve(result);
            });
          });

    }).finally(_ => con.end());
}
