'use strict';

var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var MenuPictureSchema = new Schema({
  data: Buffer
});

module.exports = mongoose.model('MenuPicture', MenuPictureSchema);