-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-11-2026 a las 04:25:45
-- Versión del servidor: 10.11.16-MariaDB
-- Versión de PHP: 8.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `salud360`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `appointments`
--

CREATE TABLE `appointments` (
                                `appointment_id` int(11) NOT NULL,
                                `patient_id` int(11) NOT NULL,
                                `center_id` int(11) NOT NULL,
                                `date_init` datetime NOT NULL,
                                `date_end` datetime NOT NULL,
                                `description` text DEFAULT NULL,
                                `cost` decimal(12,2) NOT NULL DEFAULT 0.00,
                                `appointment_state` varchar(30) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `doctors`
--

CREATE TABLE `doctors` (
                           `id` int(11) NOT NULL,
                           `doc_type` varchar(30) NOT NULL,
                           `name` varchar(100) NOT NULL,
                           `last_name` varchar(100) NOT NULL,
                           `phone` varchar(20) DEFAULT NULL,
                           `email` varchar(120) DEFAULT NULL,
                           `address` varchar(200) DEFAULT NULL,
                           `password` varchar(255) DEFAULT NULL,
                           `birth_date` date DEFAULT NULL,
                           `specialty` varchar(100) NOT NULL,
                           `professional_card` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `medical_centers`
--

CREATE TABLE `medical_centers` (
                                   `center_id` int(11) NOT NULL,
                                   `center_type` varchar(100) NOT NULL,
                                   `address` varchar(200) NOT NULL,
                                   `city` varchar(100) NOT NULL,
                                   `doctor_id` int(11) NOT NULL,
                                   `is_available` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `patients`
--

CREATE TABLE `patients` (
                            `id` int(11) NOT NULL,
                            `doc_type` varchar(30) NOT NULL,
                            `name` varchar(100) NOT NULL,
                            `last_name` varchar(100) NOT NULL,
                            `phone` varchar(20) DEFAULT NULL,
                            `email` varchar(120) DEFAULT NULL,
                            `address` varchar(200) DEFAULT NULL,
                            `password` varchar(255) DEFAULT NULL,
                            `birth_date` date DEFAULT NULL,
                            `patient_type` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `appointments`
--
ALTER TABLE `appointments`
    ADD PRIMARY KEY (`appointment_id`),
  ADD KEY `fk_appointment_patient` (`patient_id`),
  ADD KEY `fk_appointment_center` (`center_id`);

--
-- Indices de la tabla `doctors`
--
ALTER TABLE `doctors`
    ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `professional_card` (`professional_card`);

--
-- Indices de la tabla `medical_centers`
--
ALTER TABLE `medical_centers`
    ADD PRIMARY KEY (`center_id`),
  ADD KEY `fk_center_doctor` (`doctor_id`);

--
-- Indices de la tabla `patients`
--
ALTER TABLE `patients`
    ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `appointments`
--
ALTER TABLE `appointments`
    MODIFY `appointment_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `medical_centers`
--
ALTER TABLE `medical_centers`
    MODIFY `center_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `appointments`
--
ALTER TABLE `appointments`
    ADD CONSTRAINT `fk_appointment_center` FOREIGN KEY (`center_id`) REFERENCES `medical_centers` (`center_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_appointment_patient` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `medical_centers`
--
ALTER TABLE `medical_centers`
    ADD CONSTRAINT `fk_center_doctor` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
