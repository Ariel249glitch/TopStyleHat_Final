CREATE TABLE gorro_material (
    id INT AUTO_INCREMENT PRIMARY KEY,
    gorro_id INT NOT NULL,
    material_id INT NOT NULL,

    CONSTRAINT fk_gorro_material_gorro
        FOREIGN KEY (gorro_id)
        REFERENCES gorro(id),

    CONSTRAINT fk_gorro_material_material
        FOREIGN KEY (material_id)
        REFERENCES material(id)
);