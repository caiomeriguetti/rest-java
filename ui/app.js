var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var multer = require('multer'); // v1.0.5
app.use(bodyParser.json()); // for parsing application/json
app.use(bodyParser.urlencoded({ extended: true })); // for parsing application/x-www-form-

//static files
app.use(express.static('frontend'));

app.get('/servers', function (req, res) {

});

var listener = app.listen(3000, function () {
  console.log('UI running');
});