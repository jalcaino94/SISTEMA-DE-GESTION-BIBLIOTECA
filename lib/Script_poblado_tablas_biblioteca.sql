USE biblioteca_db;

-- Insertar Categorías
INSERT INTO categorias (nombre) VALUES ('Literatura'), ('Ciencias'), ('Historia'), ('Tecnología');

-- Insertar Usuarios (Bibliotecario y Estudiante)
INSERT INTO usuarios (nombre, rut, correo, contrasena, rol) VALUES 
('Administrador', '11.111.111-1', 'admin@biblioteca.cl', '1234', 'bibliotecario'),
('Juan Pérez', '22.222.222-2', 'juan.perez@biblioteca.cl', '1234', 'estudiante');

-- Insertar Libros iniciales
INSERT INTO libros (titulo, autor, isbn, editorial, stock, id_categoria) VALUES 
('Don Quijote de la Mancha', 'Miguel de Cervantes', '978-0142437230', 'Planeta', 5, 1),
('Breve historia del tiempo', 'Stephen Hawking', '978-0553380163', 'Crítica', 3, 2),
('Estructuras de Datos en Java', 'Mark Allen Weiss', '978-0133761313', 'Pearson', 4, 4);

-- Insertar Estudiantes de prueba
INSERT INTO estudiantes (nombre, rut, curso, correo) VALUES 
('Carlos Muñoz', '33.333.333-3', '4° Medio A', 'carlos.munoz@colegio.cl'),
('María Gonzalez', '44.444.444-4', '3° Medio B', 'maria.gonzalez@colegio.cl');