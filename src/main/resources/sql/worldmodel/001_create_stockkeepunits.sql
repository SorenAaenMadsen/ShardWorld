CREATE TABLE stockkeepunits
(
    skuid         INT PRIMARY KEY,
    productname   VARCHAR(255) NOT NULL,
    initialprice  INT,
    unit          VARCHAR(10)  NOT NULL,
    description   VARCHAR(500),
    usagecategory varchar(255)
);
