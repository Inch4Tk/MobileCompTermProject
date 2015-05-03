'use strict';

var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var TableSchema = new Schema({
  name: String
});

module.exports = mongoose.model('Table', TableSchema);