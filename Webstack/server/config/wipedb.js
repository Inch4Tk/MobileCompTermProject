/**
 * Wipe all the db data of known collections. Is done before populating.
 * to disable, edit config/environment/index.js, and set `wipeDB: false`
 */

'use strict';


var Business = require('../api/business/business.model');
var MenuPic = require('../api/business/business.menupic.model');
var Order = require('../api/order/order.model');
var Thing = require('../api/thing/thing.model');
var Table = require('../api/table/table.model');
var User = require('../api/user/user.model');

Business.find({}).remove(function() {
  
});

Table.find({}).remove(function() {
  
});

Thing.find({}).remove(function() {
  
});

MenuPic.find({}).remove(function() {
});

Order.find({}).remove(function() {
});

User.find({}).remove(function() {
  console.log('Finished wiping database');
});

