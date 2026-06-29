CREATE TABLE boletas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,

    metodo_pago_id INT,
    metodo_envio_id INT,

    gorro_id INT NOT NULL,
    cliente_id INT NOT NULL,

    FOREIGN KEY (metodo_pago_id) REFERENCES metodo_pago(id),
    FOREIGN KEY (metodo_envio_id) REFERENCES metodo_envio(id)


    INSERT INTO boletas (fecha, metodo_pago_id, metodo_envio_id, gorro_id, cliente_id) VALUES 
    ('2026-06-26', 1, 1, 99, 88), -- Usa Pago Tarjeta (1) y Envío Express (1)
    ('2026-06-25', 2, 2, 99, 89); -- Usa Pago Transferencia (2) y Envío Normal (2)

);




