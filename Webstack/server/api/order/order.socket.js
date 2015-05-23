/**
 * Broadcast updates to client when the model changes
 */

'use strict';

var order = require('./order.model');

exports.regist = function(socket) {
  order.schema.post('create', function (doc) {
    onSave(socket, doc);
  });
  order.schema.post('findOneAndUpdate', function (doc) {
    onSave(socket, doc);
  });
};

function onSave(socket, doc, cb) {
  socket.emit('order:save', doc);
}