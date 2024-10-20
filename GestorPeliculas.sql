CREATE DATABASE GestorPeliculas;
USE GestorPeliculas;

CREATE TABLE usuario(
	id int auto_increment primary key,
	nombre_usuario varchar(255),
	contraseña varchar(255)
);

CREATE TABLE pelicula(
	id int auto_increment primary key,
	titulo varchar(255),
	genero varchar(255),
    año int,
    descripcion varchar(255),
    director varchar(255)
);

CREATE TABLE copia(
	id int auto_increment primary key,
	id_pelicula int,
	id_usuario int,
    estado enum('precintado', 'bueno', 'dañado'),
    soporte enum('VHS', 'DVD', 'Blu-ray', 'Digital')
);

ALTER TABLE copia
	ADD CONSTRAINT fk_pelicula FOREIGN KEY (id_pelicula) REFERENCES pelicula(id), 
    ADD CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id);

INSERT INTO usuario (nombre_usuario, contraseña) VALUES
('juanperez', 'password123'),
('mariagonzalez', 'mypassword'),
('pedrolopez', 'secret456'),
('anabermudez', 'pass789');

INSERT INTO pelicula (titulo, genero, año, descripcion, director) VALUES
('Origen', 'Sci-Fi', 2010, 'Un ladrón que roba secretos corporativos a través del uso de la tecnología de compartir sueños recibe una oportunidad para borrar su historial criminal', 'Christopher Nolan'),
('Matrix', 'Acción', 1999, 'Un hacker de ordenadores aprende de rebeldes misteriosos sobre la verdadera naturaleza de su realidad y su papel en la guerra contra sus controladores', 'Lana Wachowski'),
('Interstellar', 'Sci-Fi', 2014, 'Un equipo de exploradores viaja a través de un agujero de gusano en el espacio en un intento de asegurar la supervivencia de la humanidad', 'Christopher Nolan'),
('Star Wars: Episodio IV - Una nueva esperanza', 'Sci-Fi', 1977, 'Un joven granjero se une a una rebelión contra un imperio galáctico tiránico', 'George Lucas'),
('Pulp Fiction', 'Crimen', 1994, 'Las vidas de varios criminales se entrelazan en Los Ángeles.', 'Quentin Tarantino'),
('En busca de la felicidad', 'Drama', 2006, 'Un vendedor en apuros lucha por mantener a su hijo y encontrar el éxito.', 'Gabriele Muccino'),
('Seven', 'Thriller', 1995, 'Dos detectives persiguen a un asesino en serie que usa los siete pecados capitales como su modus operandi.', 'David Fincher'),
('Gran Torino', 'Drama', 2008, 'Un veterano de guerra se convierte en mentor de un joven inmigrante.', 'Clint Eastwood'),
('Shutter Island', 'Thriller', 2010, 'Dos US Marshals investigan la desaparición de una paciente en un hospital psiquiátrico.', 'Martin Scorsese'),
('El club de la lucha', 'Drama', 1999, 'Un hombre insatisfecho con su vida forma un club de lucha clandestino.', 'David Fincher'),
('Gladiator', 'Acción', 2000, 'Un general romano es traicionado y busca venganza como gladiador.', 'Ridley Scott'),
('American History X', 'Drama', 1998, 'Un ex skinhead intenta evitar que su hermano siga sus pasos.', 'Tony Kaye'),
('Django desencadenado', 'Western', 2012, 'Un ex esclavo se une a un cazarrecompensas para liberar a su esposa.', 'Quentin Tarantino'),
('El lobo de Wall Street', 'Biografía', 2013, 'La vida excesiva de un corredor de bolsa y su caída.', 'Martin Scorsese'),
('Malditos bastardos', 'Guerra', 2009, 'Un grupo de soldados judíos busca venganza durante la Segunda Guerra Mundial.', 'Quentin Tarantino'),
('Forrest Gump', 'Drama', 1994, 'La vida extraordinaria de un hombre con un coeficiente intelectual bajo.', 'Robert Zemeckis'),
('Titanic', 'Romance', 1997, 'Historia de amor entre dos pasajeros en el fatídico viaje del Titanic.', 'James Cameron'),
('El maquinista', 'Thriller', 2004, 'Un operario de fábrica sufre de insomnio y empieza a perder la cordura.', 'Brad Anderson'),
('El renacido', 'Aventura', 2015, 'Un cazador es dejado por muerto y busca venganza.', 'Alejandro González Iñárritu'),
('Taxi Driver', 'Drama', 1976, 'Un veterano de Vietnam se convierte en un vigilante solitario en Nueva York.', 'Martin Scorsese');

INSERT INTO copia (id_pelicula, id_usuario, estado, soporte) VALUES
(1, 1, 'bueno', 'DVD'),
(1, 1, 'bueno', 'Blu-ray'),
(2, 2, 'dañado', 'DVD'),
(3, 1, 'bueno', 'Blu-ray'),
(4, 2, 'bueno', 'DVD'),
(4, 1, 'bueno', 'Blu-ray'),
(5, 3, 'precintado', 'VHS'),
(6, 4, 'bueno', 'Digital'),
(2, 3, 'precintado', 'Blu-ray'),
(3, 4, 'dañado', 'DVD'),
(6, 2, 'precintado', 'Digital'),
(7, 3, 'bueno', 'Blu-ray'),
(8, 4, 'dañado', 'DVD'),
(9, 1, 'bueno', 'Digital'),
(10, 2, 'precintado', 'VHS'),
(11, 3, 'bueno', 'DVD'),
(12, 4, 'dañado', 'Blu-ray'),
(13, 1, 'precintado', 'Digital'),
(14, 2, 'bueno', 'DVD'),
(15, 3, 'dañado', 'Blu-ray'),
(16, 4, 'bueno', 'VHS'),
(17, 1, 'bueno', 'Digital'),
(18, 2, 'precintado', 'Blu-ray'),
(19, 3, 'dañado', 'DVD'),
(20, 4, 'bueno', 'VHS');