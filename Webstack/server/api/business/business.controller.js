'use strict';

var Business = require('./business.model');
var MenuItemPic = require('./business.menupic.model');
var Table = require('../table/table.model');
var User = require('../user/user.model');

// Requires multiparty and filesystem for storing images
var fs = require('fs');
var multiparty = require('multiparty');


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
    table.owner = newBusiness._id;
    newBusiness.tables.push(table._id);
    table.save();
  }
  console.log(newBusiness);
  newBusiness.save(function(err, business) {
    if (err) return res.send(500);
    res.json(201, business);
  });
};

/**
 * Stores a menu item in the mongo db and then returns the id
 */
exports.storeMenuItemPic = function(req, res, next) {
  var form = new multiparty.Form();
  
  form.parse(req, function (err, fields, files) {
    if (err) return res.json(500);
    
    // Async read the buffered file from fs
    fs.readFile(files.file[0].path, function(err, data){
      fs.unlink(files.file[0].path); // Delete temp file
      if (err) {
        return res.json(500);
      }
      // Store in mongoose
      MenuItemPic.create({data:data}, function(err, pic){
        if(err) { return res.json(500); }
        return res.json(201, pic._id);
      });
    });
  });
};

/**
 * Gets the menu of the correct business via the tableid
 */
exports.getMenu = function(req, res, next) {
  var tableId = req.params.id
  // First resolve the table
  Table.findById(tableId, function(err, table){
    if(err) { return res.json(500); }
    // Now resolve the business over the table
    Business.findById(table.owner, function(err, business){
      if(err) { return res.json(500); }
      // Return the menu
      return res.json(201, business.menu);
    });
  });
};

/**
 * Gets the picture by its id
 */
exports.getMenuPicture = function(req, res, next) {
  var pictureId = req.params.id
  // First resolve the table
  MenuItemPic.findById(pictureId, function(err, pic){
    if(err) { return res.json(500); }
    return res.json(201, pic);
  });
};