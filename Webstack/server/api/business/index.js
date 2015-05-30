'use strict';

var express = require('express');
var controller = require('./business.controller');
var config = require('../../config/environment');
var auth = require('../../auth/auth.service');

var router = express.Router();

router.get('/', auth.hasRole('admin'), controller.index);
router.get('/mybusiness', auth.isAuthenticated(), controller.mybusiness);
router.get('/:id', auth.isAuthenticated(), controller.show);
router.get('/menu/:id', controller.getMenu); // Get the menu from tableid
router.get('/menupic/:id', controller.getMenuPicture); // Get a picture from picture id

router.post('/', auth.isAuthenticated(), controller.create);
router.post('/mipic', auth.isAuthenticated(), controller.storeMenuItemPic);

module.exports = router;
