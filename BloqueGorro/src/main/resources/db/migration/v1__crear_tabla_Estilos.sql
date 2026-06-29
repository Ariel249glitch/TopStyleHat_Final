CREATE TABLE gorro_estilo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    gorro_id INT NOT NULL,
    estilo_id INT NOT NULL,

    CONSTRAINT fk_gorro_estilo_gorro
        FOREIGN KEY (gorro_id)
        REFERENCES gorro(id),

    CONSTRAINT fk_gorro_estilo_estilo
        FOREIGN KEY (estilo_id)
        REFERENCES estilo(id)
);