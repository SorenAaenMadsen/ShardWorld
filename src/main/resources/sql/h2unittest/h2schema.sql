CREATE TABLE stockkeepunits (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                name VARCHAR(255) NOT NULL,
                                description VARCHAR(500)
);

CREATE TABLE recipes (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         description VARCHAR(500)
);

CREATE TABLE recipe_stockkeepunit_relation (
                                               recipe_id INT NOT NULL,
                                               stockkeepunit_id INT NOT NULL,
                                               relation_type VARCHAR(10) NOT NULL CHECK (relation_type IN ('INPUT', 'OUTPUT', 'TOOL')),
                                               FOREIGN KEY (recipe_id) REFERENCES recipes(id),
                                               FOREIGN KEY (stockkeepunit_id) REFERENCES stockkeepunits(id),
                                               PRIMARY KEY (recipe_id, stockkeepunit_id, relation_type)
);
