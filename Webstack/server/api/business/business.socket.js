/**
 * Broadcast updates to client when the model changes
 */

'use strict';

var business = require('./business.model');

//exports.register = function(socket) {
//  business.schema.post('save', function (doc) {
//    onSave(socket, doc);
//  });
//  business.schema.post('remove', function (doc) {
//    onRemove(socket, doc);
//  });
//}
//
//function onSave(socket, doc, cb) {
//  socket.emit('business:save', doc);
//}
//
//function onRemove(socket, doc, cb) {
//  socket.emit('business:remove', doc);
//}