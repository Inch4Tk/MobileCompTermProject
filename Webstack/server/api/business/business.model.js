'use strict';

var mongoose = require('mongoose');
var Schema = mongoose.Schema;


var BusinessSchema = new Schema({
  name: String,
  locations: [Schema.Types.ObjectId],
  users: [Schema.Types.ObjectId]
});

module.exports = mongoose.model('Business', BusinessSchema);
