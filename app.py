
from flask import Flask,Response, request
from pymongo import MongoClient
import json
from bson.objectid import ObjectId

cluster=MongoClient("mongodb+srv://Jyothi:luffy123@cluster1.e4bi4.mongodb.net/?retryWrites=true&w=majority")
db=cluster["Articles"]
collection=db["fs.chunks"]

app = Flask(__name__)

# try:
#     mongo =pymongo.MongoClient(
#         host="localhost",
#         port=80,
#         serverSelectionTimeoutMS =10000
#     )
#     db=mongo.Articles
#     mongo.server_info()
# except Exception as ex:
#         print(ex)


@app.route("/users",methods=["GET"])
def get_some_users():
    try:
        data =list(collection.find({}))
        for users in data:
            users["_id"] = str(users["_id"])
        return Response(
            response=json.dumps(data),
            status=200,
            mimetype="application/json"
        )
    except Exception as ex:
        print(ex)
        return Response( response=json.dumps({"message":"cannot read users"}),
            status=500,
            mimetype="application/json"
        )

@app.route("/users",methods=["POST"])
def create_user():
    try:
        Users={
            "authorname":request.form["authorname"]}
        dbResponse =db.fs.chunks.insert_one(Users)
        print(dbResponse.inserted_id)
        return Response(
            response=json.dumps(
                {"message":"user created",
                "id":f"{dbResponse.inserted_id}"}),
            status=200,
            mimetype="application/json"
        )
    except Exception as ex:
        print(ex)
    
@app.route("/users/<id>", methods=["PATCH"])
def update_user(id):
    try:
        dbResponse = db.fs.chunks.update_one(
            {"_id":ObjectId(id)},
            {"$set":{"authorname":request.form["authorname"]}}
        )
        if dbResponse.modified_count==1:
            return Response(
                response=json.dumps(
                {"message":"user updated"}),
            status=200,
            mimetype="application/json"
        )
        else:
             return Response(
                response=json.dumps(
                {"message":"nothing to updated"}),
            status=200,
            mimetype="application/json"
        )

    except Exception as ex:
        print(ex)
        return Response(
            response= json.dumps(
                {"message":"sorry cannot update user"},
                status=500,
                mimetype="application/json"
            )
        )


@app.route("/users/<id>", methods=["DELETE"])
def delete_user(id):
    try:
        dbResponse =db.fs.chunks.delete_one({"_id":ObjectId(id)})
        for attr in dir(dbResponse):
            print(f"***{attr}***")
        return Response(
            response= json.dumps(
                {"message":" user deleted ","id":f"{id}"}),
                status=200,
                mimetype="application/json"
        )

    except Exception as ex:
            print(ex)
            return Response(
            response= json.dumps(
                {"message":"cannot delete user"},
                status=500,
                mimetype="application/json"
            )
        )



if __name__=="__main__":
    app.run(port=80, debug=True)