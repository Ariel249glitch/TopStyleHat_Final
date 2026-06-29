CREATE TABLE gorros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    gorro_id INT NOT NULL,

    FOREIGN KEY (gorro_id) REFERENCES gorro(id)
);