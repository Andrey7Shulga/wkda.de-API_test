# wkda.de-API_test

This project was originally designed to check out the following API-methods functionality:

http://www.wkda.de/papi/v1/car-types/manufacturer (http://www.wkda.de/papi/v1/car-types/manufacturer), 
http://www.wkda.de/papi/v1/car-types/main-types?manufacturer= (http://www.wkda.de/papi/v1/car-types/main-types?manufacturer=){manufacturer}
http://www.wkda.de/papi/v1/car-types (http://www.wkda.de/papi/v1/car-types/main-types?manufacturer=)/built-dates?manufacturer={manufacturer}&main-type={mainType}
http://www.wkda.de/papi/v1/car-types/main-types-details?manufacturer={manufacturer}&main-type={mainType}&built-date={builtYear}&body-type={bodyType} (http://www.wkda.de/papi/v1/car-types/main-types-details?manufacturer=%7Bmanufacturer%7D&main-type=%7BmainType%7D&built-date=%7BbuiltYear%7D&body-type=%7BbodyType%7D)

1) For instance,
having the request (Manufacturer: Bentley (107), Main-Type: Azure, Built-Date: 2007, Body-Type: Cabrio (1007)) API request returns two models:
- 6.8 V8 Biturbo 336,
- 6.8 V8 229.

Make it certain thing, requesting:
http://www.wkda.de/papi/v1/car-types/main-types-details?manufacturer=107&main-type=Azure&built-date=2007&body-type=1007 (http://www.wkda.de/papi/v1/car-types/main-types-details?manufacturer=107&main-type=Azure&built-date=2007&body-type=1007)
Response:
{
    "wkda": {
        "6.8 V8 Biturbo:336.00": "Azure 6.8 V8 Biturbo (336 kW / 457 PS)",
        "6.8 V8:299.00": "Azure 6.8 V8 (299 kW / 407 PS)"
    }
}

2) Furthemore,
make a POST request in order to add new models. Use as a sample:
POST
http://www.wkda.de/papi/v1/car-types/manufacturer (http://www.wkda.de/papi/v1/car-types/manufacturer), 
Body:
{“wkda”:{“603”:”GAZ”}}

So, a future GET request should return a new model inside of the whole manufacturer list.
Unfortunately, back-end developers have not finished this task causing the GET request to be failed. 
