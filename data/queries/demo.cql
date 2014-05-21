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