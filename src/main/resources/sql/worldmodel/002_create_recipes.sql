CREATE TABLE recipes (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         description VARCHAR(500)
);

CREATE TABLE recipe_stockkeepunit_relation (
                                               recipe_id INT NOT NULL,
                                               stockkeepunit_id INT NOT NULL,
                                               relation_type ENUM('INPUT', 'OUTPUT', 'TOOL') NOT NULL,
                                               FOREIGN KEY (recipe_id) REFERENCES recipes(id),
                                               FOREIGN KEY (stockkeepunit_id) REFERENCES stockkeepunits(id),
                                               PRIMARY KEY (recipe_id, stockkeepunit_id, relation_type)
);
