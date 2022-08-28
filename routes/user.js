const express = require('express');
const userController = require('../controllers/userController.js');

const router = express.Router();
router.post('/signUp', userController.save);
router.get('/getByEmail/:email',userController.get);
router.put('/updateByEmail/:email',userController.update);
router.delete('/deleteByEmail/:email',userController.delete);
router.post('/login', userController.login)

module.exports = router