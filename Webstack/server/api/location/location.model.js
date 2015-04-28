'use strict';

var mongoose = require('mongoose');
var Schema = mongoose.Schema;


var TableSchema = new Schema({
  description: String
});

var Table = mongoose.model('Table', TableSchema);

var LocationSchema = new Schema({
  name: String,
  location: String,
  tables: [TableSchema]
});



module.exports = mongoose.model('Location', LocationSchema);
