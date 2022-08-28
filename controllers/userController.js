
const models = require('../models')
const validator = require('fastest-validator');
const bcryptjs = require('bcryptjs');
const jwt = require('jsonwebtoken');

//Create users
function saveUser(req, res) {

    const user = {
        username: req.body.username,
        email: req.body.email,
        password: req.body.password
    }
    //Creating Validation Schema
    const schema = {
        username: { type: "string", optional: false },
        email: { type: "email", optional: false },
        password: { type: "string", optional: false, pattern: "(?=^.{6,}$)(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-z0-9]).*" }
    }
    const v = new validator();
    const validationResponse = v.validate(user, schema);
    if (validationResponse != true) {
        return res.status(500).json({
            message: "Validation Failed",
            error: validationResponse
        })
    }
    //Creating Salt
    bcryptjs.genSalt(10, function (err, salt) {
        //Hashing the password
        bcryptjs.hash(req.body.password, salt, function (err, hash) {
            const user2 = {
                username: req.body.username,
                email: req.body.email,
                password: hash
            }
            if (user2.username != null && user2.email != null && user.password != null) {
                if (user2.username.length != 0 && user2.email.length != 0 && user.password.length != 0) {
                    models.User.create(user2).then(result => {
                        res.status(201).json({
                            message: "Signed Up Successfully",
                            user: result
                        })
                    }).catch(error => {
                        if (error.name == 'SequelizeUniqueConstraintError') {
                            res.status(500).json({
                                message: "Email Id Already In Use!"
                            })
                        }
                    })
                }
                else {
                    res.status(500).json({
                        message: "Field Cannot Be Empty!"
                    })
                }
            }
            else {
                res.status(500).json({
                    message: "Field Cannot Be Null Or Field Missing!"
                })
            }
        });
    });
}



//get Users by emailid
function getUserById(req, res) {
    const email = req.params.email;
    models.User.findOne({ where: { email: email } }).then(result => {
        res.status(201).json({
            user: {
                userName: result.dataValues.username,
                email: result.dataValues.email,
                password: result.dataValues.password,
                id: result.dataValues.id
            }
        })
    }).catch(error => {
        res.status(500).json({
            message: "Not Found!"
        })
    })

}



//Update User by emailId
function updateUserByEmail(req, res) {
    const email = req.params.email;
    const updatedUser = {
        email: req.body.email,
        password: req.body.password
    }
    //Creating Validation Schema
    const schema = {
        email: { type: "email", optional: false },
        password: { type: "string", optional: false, pattern: "(?=^.{6,}$)(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-z0-9]).*" }
    }

    const v = new validator();
    const validationResponse = v.validate(updatedUser, schema);
    if (validationResponse != true) {
        return res.status(500).json({
            message: "Validation Failed",
            error: validationResponse
        })
    }
    //Creating Salt
    bcryptjs.genSalt(10, function (err, salt) {
        //Hashing the password
        bcryptjs.hash(req.body.password, salt, function (err, hash) {
            const user2 = {
                email: req.body.email,
                password: hash
            }
            if (updatedUser.email != null && updatedUser.password != null) {
                if (updatedUser.email.length != 0 && updatedUser.password.length != 0) {
                    models.User.update(user2, { where: { email: email } }).then(result => {
                        if (result[0] == 1) {
                            //console.log(result[0])
                            res.status(201).json({
                                message: "Details Updated Successfully!",
                                user: user2
                            })
                        }
                        else {
                            res.status(500).json({
                                message: "User Not Found!"
                            })
                        }
                    }).catch(error => {
                        console.log(error)
                        if (error.name == 'SequelizeUniqueConstraintError') {
                            res.status(500).json({
                                message: "Email Id Already In Use!"
                            })
                        } else {
                            res.status(500).json({
                                message: "Unable To Connect!"
                            })
                        }
                    })
                } else {
                    res.status(500).json({
                        message: "Field Cannot Be Empty!"
                    })
                }
            } else {
                res.status(500).json({
                    message: "Field Cannot Be Null Or Field Missing!"
                })
            }
        });
    });
}



//delete user by emailId
function deleteUserByEmail(req, res) {
    const email = req.params.email;
    models.User.destroy({ where: { email: email } }).then(count => {
        if (!count) {
            return res.status(500).json({
                message: "User Not Exists Or Already Deleted!"
            });
        }
        res.status(201).json({
            message: "User Deleted Successfully!"
        })
    });
}


//Login Function
function login(req,res){
    //checking the email id passed  exists in DB or not
    models.User.findOne({where:{email:req.body.email}}).then(user =>{
        //console.log(user.password)
        if(user === null){
            res.status(201).json({
                message: "User Not Registered!"
            })
        }
        else{
            //comparing hashed password from table and password text passed from client
            bcryptjs.compare(req.body.password,user.password, function(err,result){
            //if both password compared and result is true then create access token for user
            if(result === true){
                //in jwt package's sign method create an object with details we want to include in our token
                const token = jwt.sign({
                    email:user.email,
                    userId:user.id
                },'secret', function(err,token){//jwt.sign takes 3 argument-object,'secret'word, callback function
                    res.status(201).json({
                        message:"Authenticated Successfully!",
                        token:token
                    })
                });
            } else{
                res.status(500).json({
                    message:"Please Enter Valid Credentials!"
                })
            }
            })
        }
    }).catch(error =>{
        res.status(500).json({
            message:"Something Went Wrong. Please Try Again!"
        })
    })
}



module.exports = {
    save: saveUser, get: getUserById, update: updateUserByEmail, delete: deleteUserByEmail, login:login
}