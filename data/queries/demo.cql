// Create a Country
CREATE (c:Country {name: "Spain"})

// Create a city in Spain
MATCH (c:Country {name: "Spain"})
CREATE (ci:City {name: "Madrid"})-[r:CITY_OF]->(c)

// Create a Street in Madrid, Spain
MATCH (ci:City {name: "Madrid"})-[cof]->(c:Country {name: "Spain"})
CREATE (s:Street {name: "Goya", type: "CALLE"})-[r:STREET_OF]->(ci)

// Create a postal code in Madrid
MATCH (ci:City {name: "Madrid"})
CREATE (p:PostalCode {number: "28001"})-[r:POSTAL_CODE_OF]->(ci)

// Create a location in Goya, 28001, Madrid, Spain
MATCH (p:PostalCode {number: "28001"})-[pof]->(ci:City {name: "Madrid"})-[cof]->(c:Country {name: "Spain"}), (s:Street {name: "Goya", type: "CALLE"})-[sof]->(cii:City {name: "Madrid"})
CREATE (s)<-[lii:LOCATION_IN]-(l:Location {details: "nº 13, piso 7 letra B"})-[li:LOCATION_IN]->(p)

// Create a price for lot
CREATE (p:Price {value: 6.0, currency: "€"})

// Create a lot in location
MATCH (pr:Price {value: 6.0, currency: "€"}),(s:Street {name: "Goya", type: "CALLE"})<-[lii:LOCATION_IN]-(loc:Location {details: "nº 13, piso 7 letra B"})-[li:LOCATION_IN]->(p:PostalCode {number: "28001"})
CREATE (pr)<-[coff:COST_PRICE]-(lot:Lot {number:1, quantity: 10})-[loa:LOCATED_AT]->(loc)

// Relate a lot with product
MATCH (p:Product {ref: "WOODRIDEGOODRIDE1"}),(lot:Lot {number:1, quantity: 10})
CREATE (p)<-[r:LOT_OF]-(lot)

// Create a Product
START l = node(12814)
CREATE (l)-[:LOT_OF]->(p:Product {
	name: "Woodride, Black narrow sleeves",
	pattern: "LOGO",
	ref: "BLACKNARROWSLEEVES1",
	color: "BLACK",
	picture: "BLACKNARROWSLEEVES1.jpg",
	sort: "SHIRT"
})

// Create a product with related prices
START p = node(25621), main_price_2 = node (12808), cost_price = node(1), main_price_1 = node(0)
CREATE (main_price_2)-[:MAIN_PRICE{date: 1400538039956}]->(p)<-[:MAIN_PRICE{date: 1397761082291}]-(main_price_1),
    (cost_price)-[:COST_PRICE{date: 1397761082291}]->(p)

// Create a lot with cost price relating
START loc = node(12812), p = node(25621)
CREATE (loc)<-[:LOCATED_AT]-(l:Lot{number: 2, quantity: 10})-[:LOT_OF]->(p),
(l)-[:COST_PRICE]->(:Price{value: 7, currency: "€"})

// Create Sales for product
START p = node(25621)
CREATE (:Sale{date: 1401282104 })<-[:OBJECT_OF]-(p)-[:OBJECT_OF]->(:Sale{date: 1401282105 })

// Create a Lot with a Product, Main Price and Cost Price
START loc = node(12812), cost = node(25623), main_price_1 = node(0), main_price_2 = node(12808)
CREATE
(loc)<-[:LOCATED_AT]-(l:Lot{number: 4, quantity: 10})-[:COST_PRICE]->(cost),
(l)-[:LOT_OF]->(p:Product {
	name: "Woodride, Surfer back",
	pattern: "PICTURE",
	ref: "SURFERBACK1",
	color: "WHITE",
	picture: "SURFERBACK1.jpg",
	sort: "SHIRT"
})-[:MAIN_PRICE{date: 1401289284}]->(main_price_1),
(p)-[:MAIN_PRICE{date: 1401289285}]->(main_price_2),
(p)-[:OBJECT_OF]->(s: Sale{date: 1401289714})

// Create a sale
START p = node(25627)
CREATE (s:Sale{date: 1401289713})<-[:OBJECT_OF]-(p)