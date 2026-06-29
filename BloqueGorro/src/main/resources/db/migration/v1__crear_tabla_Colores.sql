CREATE TABLE gorro_color (
    id INT AUTO_INCREMENT PRIMARY KEY,
    gorro_id INT NOT NULL,
    color_id INT NOT NULL,

    CONSTRAINT fk_gorro_color_gorro
        FOREIGN KEY (gorro_id)
        REFERENCES gorro(id),

    CONSTRAINT fk_gorro_color_color
        FOREIGN KEY (color_id)
        REFERENCES color(id)
);