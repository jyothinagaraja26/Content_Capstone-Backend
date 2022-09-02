
from importlib.metadata import files
from re import search
from flask import Flask,Response, request
from pymongo import MongoClient
import json
from bson.objectid import ObjectId

cluster=MongoClient("mongodb+srv://Content_team:Content_Team_GL@cluster0.8esbc.mongodb.net/?retryWrites=true&w=majority")
db=cluster["articles"]
db2=cluster["articles_astro"]
collection=db["fs.files"]
coll_for_each_articles=db["fs.chunks"]
collection_of_astro=db2["fs.files"]


app = Flask(__name__)

# 1.List all articles ( already available ) 

@app.route("/fetchall",methods=["GET"])
def get_all_articles():
    try:
        data =list(collection.find({}))
        for users in data:
            users["_id"] = str(users["_id"])   
        return Response(
            response=json.dumps(data,default=str),
            status=200,
            mimetype="application/json"
        )
    except Exception as ex:
        print(ex)
        return Response( response=json.dumps({"message":"cannot read users"}),
            status=500,
            mimetype="application/json"
        )

# 2.List details of one article based on one articles  ID  

@app.route("/fetchbyid/<id>",methods=["GET"])
def get_articles_withid(id):
    try:
        dbResponse =collection.find_one({"_id":ObjectId(id)})
        return Response(
            response= json.dumps(dbResponse,default=str),
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
#3.List articles based on interest /recommended topics 

#4.List articles based on partial keyword ( global search – title /keywords / Author ) 

#5.	For each user logged in
# a.List all articles ( already available ) 

@app.route("/getallarticlesbyeachuser",methods=["GET"])
def get_all_articlesbyuser():
    try:
        data =list(collection_of_astro.find({}))
        for users in data:
            users["_id"] = str(users["_id"])   
        return Response(
            response=json.dumps(data,default=str),
            status=200,
            mimetype="application/json"
        )
    except Exception as ex:
        print(ex)
        return Response( response=json.dumps({"message":"cannot read users"}),
            status=500,
            mimetype="application/json"
        )
#b.	create particular article 
@app.route("/createnewarticlebyuser",methods=["POST"])
def create_articlebyuser():
    try:
        Users={
            "author":request.form["author"]
            }
        dbResponse =collection_of_astro.insert_one(Users)
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
        return Response(
            response= json.dumps(
                {"message":"sorry couldnot create user"},
                status=500,
                mimetype="application/json"
            )
        )

 #c.update user articles   
@app.route("/updatearticlesbyuser/<id>", methods=["PATCH"])
def update_article(id):
    try:
        dbResponse = collection_of_astro.update_one(
            {"_id":ObjectId(id)},
            {"$set":{"author":request.form["author"]}}
        )
        if dbResponse.modified_count==1:
            return Response(
                response=json.dumps(
                {"message":"user updated"},default=str),
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

#d. delete articles by user
@app.route("/deletearticlesbyuser/<id>", methods=["DELETE"])
def delete_article(id):
    try:
        dbResponse =collection_of_astro.delete_one({"_id":ObjectId(id)})
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
    app.run(debug=True)