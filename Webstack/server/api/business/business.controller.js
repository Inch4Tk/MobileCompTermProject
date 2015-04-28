'use strict';

var Business = require('./business.model');
var User = require('../user/user.model');

/**
 * Get list of businesses
 * restriction: 'admin'
 */
exports.index = function(req, res) {
  Business.find({}, function (err, businesses) {
    if(err) return res.send(500, err);
    res.json(200, businesses);
  });
};
