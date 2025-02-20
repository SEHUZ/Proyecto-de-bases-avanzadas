CREATE DATABASE ClinicaPrivada;
USE ClinicaPrivada;

-- Creación de la tabla Usuarios
CREATE TABLE Usuarios (
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    correoElectronico VARCHAR(100) UNIQUE NOT NULL,
    contrasenia VARCHAR(30) NOT NULL,
    rol ENUM("médico", "paciente") NOT NULL
);

-- Creación de la tabla Médicos
CREATE TABLE medicos (
    idMedico INT PRIMARY KEY AUTO_INCREMENT,
    idUsuario INT NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidoPaterno VARCHAR(100) NOT NULL,
    apellidoMaterno VARCHAR(100),
    cedula VARCHAR(50) UNIQUE NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    FOREIGN KEY (idUsuario) REFERENCES Usuarios(idUsuario)
);

-- Creación de la tabla Pacientes
CREATE TABLE Pacientes (
    idPaciente INT PRIMARY KEY AUTO_INCREMENT,
    idUsuario INT NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidoPaterno VARCHAR(100) NOT NULL,
    apellidoMaterno VARCHAR(100),
    fechaNacimiento DATE NOT NULL,
    telefono VARCHAR(10) NOT NULL,
    FOREIGN KEY (idUsuario) REFERENCES Usuarios(idUsuario)
);

-- Creación de la tabla DireccionesPacientes
CREATE TABLE DireccionesPacientes (
    idDireccion INT PRIMARY KEY AUTO_INCREMENT,
    idPaciente INT NOT NULL UNIQUE,
    Calle VARCHAR(50) NOT NULL,
    Numero VARCHAR(10) NOT NULL,
    CP VARCHAR(10) NOT NULL,
    FOREIGN KEY (idPaciente) REFERENCES Pacientes(idPaciente)
);

-- Creación de la tabla HorariosMedicos
CREATE TABLE HorariosMedicos (
    idHorario INT PRIMARY KEY AUTO_INCREMENT,
    idMedico INT NOT NULL,
    horaEntrada TIME NOT NULL,
    horaSalida TIME NOT NULL,
    diaSemana ENUM('Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo') NOT NULL,
    FOREIGN KEY (idMedico) REFERENCES Medicos(idMedico)
);

-- Creación de la tabla Citas
CREATE TABLE Citas (
    idCita INT PRIMARY KEY AUTO_INCREMENT,
    idPaciente INT NOT NULL,
    idMedico INT NOT NULL,
    Estado VARCHAR(20) NOT NULL,
    FechaHora DATETIME NOT NULL,
    FOREIGN KEY (idPaciente) REFERENCES Pacientes(idPaciente),
    FOREIGN KEY (idMedico) REFERENCES Medicos(idMedico)
);

-- Creación de la tabla CitasEmergencias
CREATE TABLE CitasEmergencias (
    idCitaEmergencia INT PRIMARY KEY AUTO_INCREMENT,
    idCita INT NOT NULL UNIQUE,
    folio VARCHAR(8) NOT NULL,
    FOREIGN KEY (idCita) REFERENCES Citas(idCita)
);

-- Creación de la tabla CitasNormales
CREATE TABLE CitasNormales (
    idCitaNormal INT PRIMARY KEY AUTO_INCREMENT,
    idCita INT NOT NULL UNIQUE,
    FOREIGN KEY (idCita) REFERENCES Citas(idCita)
);

-- Creación de la tabla Consultas
CREATE TABLE Consultas (
    idConsulta INT PRIMARY KEY AUTO_INCREMENT,
    Diagnostico VARCHAR(300) NOT NULL,
    Estado VARCHAR(30) NOT NULL,
    FechaHora DATETIME NOT NULL,
    Tratamiento VARCHAR(300),
    idCita INT NOT NULL,
    FOREIGN KEY (idCita) REFERENCES Citas(idCita)
);

-- Creacion tabla auditorias
CREATE TABLE Auditorias (
    idAuditoriaINT INT PRIMARY KEY AUTO_INCREMENT,
    tipoOperacion VARCHAR(300),
    FechaHora DATETIME NOT NULL,
    detalles VARCHAR(300),
    idCita INT NOT NULL,
    FOREIGN KEY (idCita) REFERENCES Citas(idCita)
    );


INSERT INTO usuarios (correoElectronico, contrasenia, rol) VALUES 
("dr.juan@example.com", "password123", "médico"),
("raul@gmail.com", "unicornio123", "paciente"),
("sebastian@hotmail.com", "diamantepuro", "paciente"),
("dr.abraham@gmail.com", "perrochilo", "medico");

INSERT INTO medicos (idUsuario, nombres, apellidoPaterno, apellidoMaterno, cedula, especialidad) VALUES 
(1, "Abraham", "Johnson", "Bringas", "1231456788", "Cardiologia"),
(4, "Peter", "Peterson", "Patin", "79456988", "Urologia");

INSERT INTO pacientes (idUsuario, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono) VALUES
(2, "Jaime Eduardo", "Lerma", "Cuevas", "2000-06-19", "6442595242"),
(3, 'María', 'Pérez', 'Ramírez', '1990-05-15', '5551234567');

INSERT INTO DireccionesPacientes (idPaciente, Calle, Numero, CP) VALUES 
(1, "Calle Principal", "3210", "85150"),
(2, "Calle Guerrero", "2008", "80138");

INSERT INTO HorariosMedicos (idMedico, horaEntrada, horaSalida, diaSemana)VALUES 
(1, '09:00:00', '17:00:00', "Lunes"),
(1, '09:00:00', '17:00:00', "Martes"),
(1, '09:00:00', '17:00:00', "Miercoles"),
(1, '09:00:00', '17:00:00', "Jueves"),
(1, '09:00:00', '17:00:00', "Viernes");

INSERT INTO HorariosMedicos (idMedico, horaEntrada, horaSalida, diaSemana) VALUES 
(2, '07:00:00', '15:00:00', 'Sabado'),
(2, '07:00:00', '15:00:00', 'Domingo');

INSERT INTO Citas (idPaciente, idMedico, Estado, FechaHora) VALUES 
(1, 1, 'Pendiente', '2025-02-20 10:30:00'),
(1, 1, 'Confirmada', '2025-02-21 15:00:00');

INSERT INTO CitasEmergencias (idCita, folio) VALUES 
(1, "18673962");

INSERT INTO CitasNormales (idCita) VALUES 
(2);

INSERT INTO Consultas (Diagnostico, Estado, FechaHora, Tratamiento, idCita)
VALUES ("Hipertensión", "En progreso", "2025-02-20 11:00:00", "Recomendación de dieta baja en sal", 2);



