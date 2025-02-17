# Proyecto-de-bases-avanzadas
Repositorio para trabajar en el proyecto de bases de datos avanzadas. EQUIPO: Sebastian Borquez, Jose Aguilar, Daniel Miramontes

SCRIPT DE LA BASE DE DATOS DE LA CLINICA: 

CREATE DATABASE clinicaprivada;

-- Creación de la tabla Usuarios
CREATE TABLE Usuarios (
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    correoElectronico VARCHAR(100) UNIQUE NOT NULL,
    contrasenia VARCHAR(30) NOT NULL,
    rol ENUM(“médico”, “paciente”) NOT NULL
);

-- Creación de la tabla Médicos
CREATE TABLE Médicos (
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
