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

/**
 * Creates a new business
 */
exports.create = function (req, res, next) {
  var newBusiness = new Business(req.body);
  var userId = req.params.id;
  newBusiness.users.push(userId);
  newBusiness.save(function(err, business) {
    if (err) return validationError(res, err);
  });)
};
