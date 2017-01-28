db.roles.drop();
db.roles.createIndex({"name":1}, {"unique":true});
