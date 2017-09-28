# APIRepository
This project is for Online shopping API, developed in Java 1.8 with Spring MVC 4.0, Restful WS, Hibernate 4.0 and MySQL 6, JSON, iText Reporting tool and Amazon Web Services. The project is catering both customer mobile app and administration mobile app. It will add, edit, update and delete data from Database depending on the request of the apps. It will generate a JSON response that is ready to use by the apps.

## Customer App API
1. Online shopping API will receive a request from the APPs for the list of Products.
2. Online shopping API will generate a response with the list of products, available stocks, price and compare price and picture link/url.
3. Online shopping API will receive a request from the APPs regarding the orders of the customer from their CART. This includes the customer details, product code, quantity, amount and total amount.

## Administration App API
1. Online shopping - administration API will generate a list of Sales Order.
2. Online shopping - administration API will receive a request to process the Sales Order item.
