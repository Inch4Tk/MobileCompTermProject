'use strict';

var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var TableSchema = new Schema({
  name: String,
  owner: Schema.Types.ObjectId
});

module.exports = mongoose.model('Table', TableSchema);