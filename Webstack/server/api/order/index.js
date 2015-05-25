'use strict';

var express = require('express');
var controller = require('./order.controller');
var config = require('../../config/environment');
var auth = require('../../auth/auth.service');

var router = express.Router();

// Gets
router.get('/business/:id/active', auth.isAuthenticated(), controller.listActiveBusinessOrders);
router.get('/business/:id/all', auth.isAuthenticated(), controller.listAllBusinessOrders);
router.get('/table/:id', controller.showOrderTable);
router.get('/:id', controller.showOrder);

// Creation
router.post('/:id/status', auth.isAuthenticated(), controller.updateStatus);
router.post('/:id', controller.create);

module.exports = router;
