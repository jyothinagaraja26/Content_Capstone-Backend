const express = require ('express');
const bodyparser = require('body-parser');
const cors = require('cors')

const app = express();

app.use(express.json());
app.use(bodyparser.json({ limit: '30mb', extended: true }))
app.use(bodyparser.urlencoded({ limit: '30mb', extended: true }))
app.use(cors());

const userRoute = require('./routes/user.js');

app.use("/user", userRoute);


app.get('/', (req,res)=> {
    res.send("Hello World!!!")
});

module.exports = app;