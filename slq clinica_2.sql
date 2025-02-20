create DATABASE ClinicaPrivada;
USE ClinicaPrivada;
-- Creación de la tabla Usuarios
CREATE TABLE Usuarios (
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    correoElectronico VARCHAR(100) UNIQUE NOT NULL,
    contrasenia VARCHAR(255) NOT NULL, -- Se aumentó la longitud para mayor seguridad
    rol ENUM('médico', 'paciente') NOT NULL
);

-- Creación de la tabla Médicos
CREATE TABLE Medicos (
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
    calle VARCHAR(50) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    codigoPostal VARCHAR(10) NOT NULL,
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

-- Creación de la tabla Estados de Citas
CREATE TABLE EstadosCita (
    idEstado INT PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO EstadosCita (descripcion) VALUES ('Agendada'), ('Cancelada'), ('No asistió paciente'), ('No atendida');

-- Creación de la tabla Citas
CREATE TABLE Citas (
    idCita INT PRIMARY KEY AUTO_INCREMENT,
    idPaciente INT NOT NULL,
    idMedico INT NOT NULL,
    idEstado INT NOT NULL DEFAULT 1, -- 'Agendada' por defecto
    fechaHora DATETIME NOT NULL,
    FOREIGN KEY (idPaciente) REFERENCES Pacientes(idPaciente),
    FOREIGN KEY (idMedico) REFERENCES Medicos(idMedico),
    FOREIGN KEY (idEstado) REFERENCES EstadosCita(idEstado)
);

-- Agregar una columna generada para almacenar solo la fecha de la cita
ALTER TABLE Citas ADD COLUMN fecha DATE GENERATED ALWAYS AS (DATE(fechaHora)) STORED;

-- Agregar la restricción UNIQUE para evitar citas duplicadas en el mismo día con el mismo médico
ALTER TABLE Citas ADD CONSTRAINT unique_cita UNIQUE (idPaciente, idMedico, fecha);

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
    diagnostico VARCHAR(300) NOT NULL,
    estado VARCHAR(30) NOT NULL,
    fechaHora DATETIME NOT NULL,
    tratamiento VARCHAR(300),
    idCita INT NOT NULL,
    FOREIGN KEY (idCita) REFERENCES Citas(idCita)
);

-- Creación de la tabla Auditorias
CREATE TABLE Auditorias (
    idAuditoria INT PRIMARY KEY AUTO_INCREMENT,
    tipoOperacion VARCHAR(300),
    fechaHora DATETIME NOT NULL,
    detalles VARCHAR(300),
    idCita INT NOT NULL,
    FOREIGN KEY (idCita) REFERENCES Citas(idCita)
);

-- Trigger para evitar modificar datos de pacientes con citas activas
DELIMITER //
CREATE TRIGGER BeforeUpdatePaciente BEFORE UPDATE ON Pacientes
FOR EACH ROW
BEGIN
    IF (SELECT COUNT(*) FROM Citas WHERE idPaciente = OLD.idPaciente AND idEstado = 1) > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se pueden modificar datos con citas activas';
    END IF;
END //
DELIMITER ;
ALTER TABLE CitasEmergencias ADD COLUMN fechaExpiracion DATETIME NOT NULL;
-- Trigger para cambiar el estado de citas no atendidas después de 10 minutos
DELIMITER //
CREATE TRIGGER AfterCitaEmergencia BEFORE UPDATE ON CitasEmergencias
FOR EACH ROW
BEGIN
    IF NOW() > OLD.fechaExpiracion THEN
        UPDATE Citas SET idEstado = (SELECT idEstado FROM EstadosCita WHERE descripcion = 'No atendida')
        WHERE idCita = OLD.idCita;
    END IF;
END //
DELIMITER ;

-- Procedimiento almacenado para cancelar cita con validación de 24 horas
DELIMITER //
CREATE PROCEDURE CancelarCita(IN citaID INT)
BEGIN
    DECLARE citaFecha DATETIME;
    SELECT fechaHora INTO citaFecha FROM Citas WHERE idCita = citaID;
    
    IF TIMESTAMPDIFF(HOUR, NOW(), citaFecha) < 24 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Las citas solo pueden cancelarse con 24 horas de anticipación';
    ELSE
        UPDATE Citas SET idEstado = (SELECT idEstado FROM EstadosCita WHERE descripcion = 'Cancelada') WHERE idCita = citaID;
    END IF;
END //
DELIMITER ;

-- Vista para historial de consultas filtrado por fecha y especialidad
CREATE VIEW HistorialConsultas AS
SELECT Citas.idPaciente, Citas.fechaHora, Medicos.especialidad, Consultas.diagnostico, Consultas.tratamiento
FROM Consultas
JOIN Citas ON Consultas.idCita = Citas.idCita
JOIN Medicos ON Citas.idMedico = Medicos.idMedico;

-- Índices para mejorar rendimiento
CREATE INDEX idx_fechaHora ON Citas(fechaHora);
CREATE INDEX idx_paciente ON Citas(idPaciente);
CREATE INDEX idx_medico ON Citas(idMedico);

-- Transacción para asignación segura de citas
DELIMITER //
CREATE PROCEDURE AgendarCita(IN pacienteID INT, IN medicoID INT, IN fecha DATETIME)
BEGIN
    START TRANSACTION;
    INSERT INTO Citas (idPaciente, idMedico, fechaHora) VALUES (pacienteID, medicoID, fecha);
    COMMIT;
END //
DELIMITER ;


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

INSERT INTO DireccionesPacientes (idPaciente, Calle, Numero, codigoPostal) VALUES 
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
INSERT INTO EstadosCita (descripcion) VALUES ('Pendiente'), ('Confirmada');

INSERT INTO Citas (idPaciente, idMedico, idEstado, FechaHora) 
VALUES  
(1, 1, (SELECT idEstado FROM EstadosCita WHERE descripcion = 'Pendiente'), '2025-02-20 10:30:00'), 
(1, 1, (SELECT idEstado FROM EstadosCita WHERE descripcion = 'Confirmada'), '2025-02-21 15:00:00');

select * from citasEmergencias;
INSERT INTO CitasEmergencias (idCita, folio,fechaExpiracion) VALUES 
(1, "18673962",current_date());

INSERT INTO CitasNormales (idCita) VALUES 
(2);

INSERT INTO Consultas (Diagnostico, Estado, FechaHora, Tratamiento, idCita)
VALUES ("Hipertensión", "En progreso", "2025-02-20 11:00:00", "Recomendación de dieta baja en sal", 2);
