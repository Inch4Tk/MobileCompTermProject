'use strict';

var Business = require('./business.model');
var MenuItemPic = require('./business.menupic.model');
var Order = require('./order.model');
var Table = require('../table/table.model');
var User = require('../user/user.model');


/**
 * Get list of all orders that are active of a business
 */
exports.listAllBusinessOrders = function(req, res, next) {
  // User check
  Business.findById(req.params.id, function(err, business){
    if (err) return next(err);
    if (!business) return res.send(400);
    if (!req.user._id.equals(business.user)) return res.send(401);
    // Passed existence checks, find orders
    Order.find({business: req.params.id}, function (err, orders) {
      if(err) return res.send(500, err);
      res.json(200, orders);
    });
  });
};


/**
 * Get list of all orders that are active of a business
 */
exports.listActiveBusinessOrders = function(req, res, next) {
  // User check
  Business.findById(req.params.id, function(err, business){
    if (err) return next(err);
    if (!business) return res.send(400);
    if (!req.user._id.equals(business.user)) return res.send(401);
    // Passed existence checks, find orders
    Order.find({finished: false, business: req.params.id}, function (err, orders) {
      if(err) return res.send(500, err);
      res.json(200, orders);
    });
  });
};


/**
 * Get order by id
 */
exports.showOrder = function(req, res, next) {
  Order.findById(req.params.id, function(err, order){
    if(err) return res.send(500, err);
    res.json(200, order);
  });
};

