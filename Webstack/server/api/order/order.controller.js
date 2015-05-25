'use strict';

var Order = require('./order.model');
var Business = require('../business/business.model');
var Table = require('../table/table.model');
var User = require('../user/user.model');


/**
 * Get list of all orders of a business
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
 * Get list of all orders of a business that are active (not paid)
 */
exports.listActiveBusinessOrders = function(req, res, next) {
  // User check
  Business.findById(req.params.id, function(err, business){
    if (err) return next(err);
    if (!business) return res.send(400);
    if (!req.user._id.equals(business.user)) return res.send(401);
    // Passed existence checks, find orders
    Order.find({business: req.params.id})
      .where('status').lt(3)
      .exec(function (err, orders) {
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

/**
 * Get current (unpaid) orders by table id
 */
exports.showOrderTable = function(req, res, next) {
  Order.find({table: req.params.id})
    .where('status').lt(3)
    .exec(function(err, orders){
      if(err) return res.send(500, err);
      res.json(200, orders);
    });
};

/**
 * Create order associated with table id
 */
exports.create = function (req, res, next) {
  // Query for table
  Table.findById(req.params.id, function (err, t) {
    if (err) return res.send(500, err);
    
    // Query for business via table
    Business.findById(t.owner, function (err, b) {
      if (err) return res.send(500, err);
      
      // Create the itemlist
      var ilist = [];
      for (var i=0; i < req.body.items.length; ++i)
      {
        for (var j=0; j < b.menu.length; ++j)
        {
          // Check if name is the same as in menu, then add it to the ilist
          if (req.body.items[i].name == b.menu[j].name)
          {
            ilist.push(
              {name: b.menu[j].name, 
              price: b.menu[j].price, 
              amount: req.body.items[i].amount});
          }
        }
      }
      
      // Check if theres any items requests that match the menu
      if (ilist.length == 0)
        return res.send(500, err);
        
      // Create a new order
      var order = {
        status: 0,
        business: b._id,
        table: req.params.id,
        items: ilist
      };

      Order.create(order, function (err, order) {
        if (err) { return res.send(500, err); }
        return res.json(201, order);
      });
    });
  });
};

/**
 * Update the status of an order
 */
exports.updateStatus = function (req, res, next) {
  Order.findOneAndUpdate({_id: req.params.id}, 
    {status: req.body.status}, 
    {new: true},
    function (err, order) {
        if (err) { return res.send(500, err); }
        return res.json(201, order);
      });
};