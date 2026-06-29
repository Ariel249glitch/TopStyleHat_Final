CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    comuna_id INT NOT NULL,

    CONSTRAINT fk_cliente_comuna
        FOREIGN KEY (comuna_id)
        REFERENCES comunas(id)


    INSERT INTO clientes (nombre, direccion, comuna_id) VALUES 
    ('Juan Pérez', 'Av. Libertador Bernardo O''Higgins 1234', 1),
    ('María José', 'Av. Providencia 567', 2),
    ('Carlos Silva', 'Álvarez 890', 3);

);