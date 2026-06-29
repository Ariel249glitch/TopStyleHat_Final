CREATE TABLE comunas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    region_id INT NOT NULL,

    CONSTRAINT fk_comuna_region
        FOREIGN KEY (region_id)
        REFERENCES regiones(id)



    INSERT INTO comunas (id, nombre, region_id) VALUES 
    (1, 'Santiago', 1),
    (2, 'Providencia', 1),
    (3, 'Viña del Mar', 2);
);