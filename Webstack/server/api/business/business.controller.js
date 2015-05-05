'use strict';

var Business = require('./business.model');
var Table = require('../table/table.model');
var User = require('../user/user.model');

/**
 * Get list of all businesses
 * restriction: 'admin'
 */
exports.index = function(req, res) {
  Business.find({}, function (err, businesses) {
    if(err) return res.send(500, err);
    res.json(200, businesses);
  });
};

/**
 * Get list of businesses from a user
 */
exports.mybusiness = function(req, res) {
  Business.find({user:req.user._id}, function (err, businesses) {
    if(err) return res.send(500, err);
    res.json(200, businesses);
  });
};

/**
 * Get info to a specific business, + all tables attached
 */
exports.show = function (req, res, next) {
  var businessID = req.params.id;

  Business
  .findById(businessID)
  .populate('tables')
  .exec( function (err, business) {
    if (err) return next(err);
    if (!business) return res.send(400);
    if (!req.user._id.equals(business.user)) return res.send(401);
    res.json(200, business);
  });
  
};

/**
 * Creates a new business
 */
exports.create = function (req, res, next) {
  var tables = req.body.tables;
  req.body.tables = [];
  var newBusiness = new Business(req.body);
  
  for (var i = 0; i < tables.length; i++) {
    var table = new Table(tables[i]);
    newBusiness.tables.push(table._id);
    table.save();
  }
  
  newBusiness.save(function(err, business) {
    if (err) return res.send(500);
    res.json(200, business);
  });
};
