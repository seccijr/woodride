begin
create (_0:`Price` {`currency`:"€", `value`:12.100000})
create (_3:`Product` {`color`:"WHITE", `date`:1401370194, `name`:"Woodride Goodride", `onSales`:false, `pattern`:"PICTURE", `picture`:"WOODRIDEGOODRIDE1.png", `ref`:"WOODRIDEGOODRIDE1", `sort`:"SHIRT"})
create (_12807:`Country` {`name`:"Spain"})
create (_12808:`Price` {`currency`:"€", `value`:11.100000})
create (_12809:`City` {`name`:"Madrid"})
create (_12810:`Street` {`name`:"Cocherón de la Villa", `type`:"CALLE"})
create (_12811:`PostalCode` {`number`:28031})
create (_12812:`Location`:`Office` {`details`:"nº 13, bajo A"})
create (_12813:`Price` {`currency`:"€", `value`:6.000000})
create (_12814:`Lot` {`number`:1})
create (_25614:`Location`:`Shop` {`details`:"nº 13, piso 7, letra B"})
create (_25615:`Lot` {`number`:5})
create (_25617:`Sale` {`date`:1401282101})
create (_25618:`Sale` {`date`:1401282102})
create (_25619:`Sale` {`date`:1401282103})
create (_25620:`Sale` {`date`:1401282104})
create (_25621:`Product` {`color`:"BLACK", `date`:1401370195, `name`:"Black narrow sleeves", `onSales`:false, `pattern`:"LOGO", `picture`:"BLACKNARROWSLEEVES1.png", `ref`:"BLACKNARROWSLEEVES1", `sort`:"SHIRT"})
create (_25622:`Lot` {`number`:2})
create (_25623:`Price` {`currency`:"€", `value`:7})
create (_25624:`Sale` {`date`:1401282104})
create (_25625:`Sale` {`date`:1401282105})
create (_25626:`Lot` {`number`:3})
create (_25627:`Product` {`color`:"WHITE", `date`:1401370197, `name`:"White Brooklyn Bridge", `onSales`:false, `pattern`:"LOGO-PICTURE", `picture`:"WHITEBROOKLYNBRIDGE1.png", `ref`:"WHITEBROOKLYNBRIDGE1", `sort`:"SHIRT"})
create (_25628:`Sale` {`date`:1401289713})
create (_25629:`Lot` {`number`:4})
create (_25630:`Product` {`color`:"WHITE", `date`:1401370196, `name`:"Surfer back", `onSales`:false, `pattern`:"PICTURE", `picture`:"SURFERBACK1.png", `ref`:"SURFERBACK1", `sort`:"SHIRT"})
create (_25631:`Sale` {`date`:1401289714})
create (_26812:`Identity` {`avatarUrl`:"", `email`:"secci.jr@gmail.com", `firstName`:"Carlos Ignacio", `id`:"secci.jr@gmail.com", `lastName`:"Pérez Sechi"})
create (_26813:`AuthProvider` {`id`:"google", `method`:"oauth2"})
create (_26814:`AuthInfo` {`expiration`:1403691217, `refresh`:"GHIJKL", `token`:"ABCDEF", `tokenType`:"bearer", `type`:"oauth2_token"})
create (_26815:`AuthInfo` {`hasher`:"bcrypt", `password`:"$2a$10$es.2EE5YgyAe6vjbCvXJzudOVgng40I1vYvBlcMawcU3rWMhwsEre", `salt`:"", `type`:"password"})
create (_26816:`Identity` {`avatarUrl`:"", `email`:"ci.perez@yahoo.es", `firstName`:"Carlos Ignacio", `id`:"secci", `lastName`:"Pérez Sechi"})
create (_26817:`AuthProvider` {`id`:"local", `method`:"userpass"})
create _12809-[:`CITY_OF`]->_12807
create _12810-[:`STREET_OF`]->_12809
create _12811-[:`POSTAL_CODE_OF`]->_12809
create _12812-[:`LOCATION_IN`]->_12811
create _12812-[:`LOCATION_IN`]->_12810
create _12814-[:`COST_PRICE` {`date`:1401289285}]->_12813
create _12814-[:`LOCATED_AT` {`quantity`:5}]->_25614
create _12814-[:`LOCATED_AT` {`quantity`:10}]->_12812
create _12814-[:`LOT_OF`]->_3
create _12814-[:`OBJECT_OF`]->_25618
create _12814-[:`OBJECT_OF`]->_25619
create _12814-[:`SALE_PRICE` {`date`:1401289285}]->_0
create _12814-[:`SALE_PRICE` {`date`:1401289285}]->_12808
create _25614-[:`LOCATION_IN`]->_12810
create _25614-[:`LOCATION_IN`]->_12811
create _25615-[:`COST_PRICE` {`date`:1401289285}]->_12813
create _25615-[:`LOCATED_AT` {`quantity`:5}]->_25614
create _25615-[:`LOT_OF`]->_3
create _25615-[:`OBJECT_OF`]->_25617
create _25615-[:`OBJECT_OF`]->_25620
create _25615-[:`SALE_PRICE` {`date`:1401289285}]->_0
create _25615-[:`SALE_PRICE` {`date`:1401289285}]->_12808
create _25622-[:`COST_PRICE` {`date`:1401289285}]->_25623
create _25622-[:`LOCATED_AT` {`quantity`:10}]->_12812
create _25622-[:`LOT_OF`]->_25621
create _25622-[:`OBJECT_OF`]->_25624
create _25622-[:`OBJECT_OF`]->_25625
create _25622-[:`SALE_PRICE` {`date`:1401289285}]->_0
create _25622-[:`SALE_PRICE` {`date`:1401289285}]->_12808
create _25626-[:`COST_PRICE` {`date`:1401289285}]->_25623
create _25626-[:`LOCATED_AT` {`quantity`:10}]->_12812
create _25626-[:`LOT_OF`]->_25627
create _25626-[:`OBJECT_OF`]->_25628
create _25626-[:`SALE_PRICE` {`date`:1401289285}]->_12808
create _25626-[:`SALE_PRICE` {`date`:1401289284}]->_0
create _25629-[:`COST_PRICE` {`date`:1401289285}]->_25623
create _25629-[:`LOCATED_AT` {`quantity`:10}]->_12812
create _25629-[:`LOT_OF`]->_25630
create _25629-[:`OBJECT_OF`]->_25631
create _25629-[:`SALE_PRICE` {`date`:1401289285}]->_12808
create _25629-[:`SALE_PRICE` {`date`:1401289284}]->_0
create _26812-[:`AUTHENTICATED_BY`]->_26813
create _26812-[:`AUTH_INFO` {`date`:1403681217}]->_26814
create _26816-[:`AUTHENTICATED_BY`]->_26817
create _26816-[:`AUTH_INFO` {`date`:1403787590}]->_26815
;
commit
