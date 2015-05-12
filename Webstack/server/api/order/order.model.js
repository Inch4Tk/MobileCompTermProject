'use strict';

var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var OrderSchema = new Schema({
  finished: Boolean,
  business: Schema.Types.ObjectId, // Not really necessary, but for convenience
  table: Schema.Types.ObjectId,
  suborders: [{
    status: Number, // 0 = unseen, 1 = processing, 2 = finished
    timestamp: { type: Date, default: Date.now},
    items: [{ name: String, price: Number, amount: Number }] }
  ]
});

module.exports = mongoose.model('Order', OrderSchema);