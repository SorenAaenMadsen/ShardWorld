CREATE TABLE recipes
(
    recipeid                                  INT PRIMARY KEY,
    recipename                                VARCHAR(255) NOT NULL,
    processdescription                        VARCHAR(2500),
    calenderwaittimefromproductiontoavailable INT,
    workTime_times10Minutes                   INT
);

CREATE TABLE recipe_input_sku
(
    recipeid INT NOT NULL,
    skuid    INT NOT NULL,
    amount   INT NOT NULL,
    FOREIGN KEY (recipeid) REFERENCES recipes (recipeid),
    FOREIGN KEY (skuid) REFERENCES stockkeepunits (skuid),
    PRIMARY KEY (recipeid, skuid)
);

CREATE TABLE recipe_output_sku
(
    recipeid INT NOT NULL,
    skuid    INT NOT NULL,
    amount   INT NOT NULL,
    FOREIGN KEY (recipeid) REFERENCES recipes (recipeid),
    FOREIGN KEY (skuid) REFERENCES stockkeepunits (skuid),
    PRIMARY KEY (recipeid, skuid)
);

CREATE TABLE recipe_toolreq_sku
(
    recipeid INT NOT NULL,
    skuid    INT NOT NULL,
    amount   INT NOT NULL,
    FOREIGN KEY (recipeid) REFERENCES recipes (recipeid),
    FOREIGN KEY (skuid) REFERENCES stockkeepunits (skuid),
    PRIMARY KEY (recipeid, skuid)
);
