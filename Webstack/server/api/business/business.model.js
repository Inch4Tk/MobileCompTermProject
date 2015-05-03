'use strict';

var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var BusinessSchema = new Schema({
  name: String,
  user: Schema.Types.ObjectId,
  tables: [Schema.Types.ObjectId]
});

module.exports = mongoose.model('Business', BusinessSchema);
