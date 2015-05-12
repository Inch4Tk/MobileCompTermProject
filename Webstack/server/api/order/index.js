'use strict';

var express = require('express');
var controller = require('./business.controller');
var config = require('../../config/environment');
var auth = require('../../auth/auth.service');

var router = express.Router();

// Gets
router.get('/business/:id/active', auth.isAuthenticated(), controller.listActiveBusinessOrders);
router.get('/business/:id/all', auth.isAuthenticated(), controller.listAllBusinessOrders);
router.get('/:id', controller.showOrder);

// Post requests are for the android part because is doesnt know what to update
router.post('/:id', controller.create);


// Put requests are for the website

module.exports = router;
