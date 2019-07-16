# ms-products
Reactive Microservice using Spring Webflux with Mongo Reactive Repository

#Requirements
- Docker installed

# How to execute project:
1 - Inside of root directory from project execute the command: <b>sh build-ms-products.sh</b><br />
2 - Execute the command: <b>sh start-ms-products.sh</b>  <br/>

# Info
-Host: localhost:8080/ms-products/api/v1/ <br/>

# Examples of Request:

<b>Get all products</b><br/>
curl -X GET \
  http://localhost:8080/ms-products/api/v1/products \
  -H 'Content-Type: application/json' \
  -H 'customerId: api-products' \
  -d '{
	"description":"Coca Cola"
}'

<b>Create a product</b><br/>
curl -X POST \
  http://localhost:8080/ms-products/api/v1/products \
  -H 'Content-Type: application/json' \
  -H 'customerId: api-products' \
  -d '{
	"description":"Coca Cola"
}'


