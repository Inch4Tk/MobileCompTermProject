'use strict';

var mongoose = require('mongoose');
var Schema = mongoose.Schema;


var MenuItemSchema = new Schema({
  name: String,
  price: Number,
  picture: Buffer,
  description: String
});

var BusinessSchema = new Schema({
  name: String,
  user: Schema.Types.ObjectId,
  tables: [{type:Schema.Types.ObjectId, ref:'Table'}],
  menu: [MenuItemSchema]
});

module.exports = mongoose.model('Business', BusinessSchema);
