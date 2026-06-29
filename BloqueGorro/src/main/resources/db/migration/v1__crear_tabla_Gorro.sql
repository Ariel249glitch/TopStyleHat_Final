CREATE TABLE gorro(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    talla VARCHAR(3) NOT NULL,
    precio INT NOT NULL,
    tipo_id INT NOT NULL,
    sexo_id INT NOT NULL,

    tipo_id INT,
    sexo_id INT,

    CONSTRAINT fk_gorro_tipo
        FOREIGN KEY (tipo_id)
        REFERENCES tipo(id),

    CONSTRAINT fk_gorro_sexo
        FOREIGN KEY (sexo_id)
        REFERENCES sexo(id)

        INSERT INTO gorro (id, nombre, talla, precio, tipo_id, sexo_id) VALUES 
    (1, 'Gorro Snapback Clásico', 'L', 14990, 1, 1),
    (2, 'Beanie de Invierno', 'S', 9990, 2, 1);
);