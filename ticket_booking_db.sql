-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 29, 2024 at 02:23 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ticket_booking_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(1, 'Admin08', '(7f*H7jTek6f7($Z'),
(2, 'SystemAdmin', 'securePass123'),
(3, 'NetworkMaster', 'adminSecure456'),
(4, 'DatabaseGuardian', 'passwordMaster789'),
(5, 'AppController', 'secureAppAccess'),
(6, 'ServerSupervisor', 'superSecurePass'),
(7, 'SecurityOfficer', 'adminSecurity123'),
(8, 'DataAnalystLead', 'dataSecure456'),
(9, 'CloudEngineer', 'cloudPass789'),
(10, 'ITManager', 'itAdmin123'),
(11, 'DevOpsLead', 'devOpsSecure456'),
(12, 'SystemArchitect', 'architectPass789'),
(13, 'NetworkSpecialist', 'networkAdmin123'),
(14, 'DatabaseExpert', 'dbExpertSecure456'),
(15, 'AppDeveloper', 'appDevPass789'),
(16, 'ServerTechnician', 'serverTech123'),
(17, 'SecurityAnalyst', 'securityAnalyst456'),
(18, 'DataScientist', 'dataScientistPass789'),
(19, 'CloudArchitect', 'cloudArch123'),
(20, 'ITSupportLead', 'itSupportSecure456'),
(23, 'Admin8', 'Shivam811');

-- --------------------------------------------------------

--
-- Stand-in structure for view `allbus`
-- (See below for the actual view)
--
CREATE TABLE `allbus` (
`BusID` int(11)
,`NumberPlate` varchar(15)
,`NumberOfSeats` int(11)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `allbusids`
-- (See below for the actual view)
--
CREATE TABLE `allbusids` (
`BusID` int(11)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `alldiscountpass`
-- (See below for the actual view)
--
CREATE TABLE `alldiscountpass` (
`DiscountPassID` int(11)
,`PassName` varchar(30)
,`DiscountPercentage` float
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `alldiscountpassids`
-- (See below for the actual view)
--
CREATE TABLE `alldiscountpassids` (
`DiscountPassID` int(11)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `allroute`
-- (See below for the actual view)
--
CREATE TABLE `allroute` (
`RouteID` int(11)
,`StartLocation` varchar(50)
,`EndLocation` varchar(50)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `allrouteids`
-- (See below for the actual view)
--
CREATE TABLE `allrouteids` (
`RouteID` int(11)
);

-- --------------------------------------------------------

--
-- Table structure for table `bus`
--

CREATE TABLE `bus` (
  `BusID` int(11) NOT NULL,
  `NumberPlate` varchar(15) DEFAULT NULL,
  `NumberOfSeats` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bus`
--

INSERT INTO `bus` (`BusID`, `NumberPlate`, `NumberOfSeats`) VALUES
(1, 'MH01XY1234', 50),
(2, 'GJ02AB4567', 45),
(3, 'DL03CD7890', 60),
(4, 'UP04EF1234', 55),
(5, 'RJ05GH5678', 40),
(6, 'MP06IJ7890', 50),
(7, 'WB07KL1234', 45),
(8, 'TN08MN4567', 60),
(9, 'AP09OP7890', 55),
(10, 'KA10QR1234', 40),
(11, 'KL11ST5678', 50),
(12, 'TS12UV7890', 45),
(13, 'CG13WX1234', 60),
(14, 'OR14YZ4567', 55),
(15, 'JH15AB7890', 40),
(16, 'JK16CD1234', 50),
(17, 'HP17EF4567', 45),
(18, 'PB18GH7890', 60),
(19, 'CH19IJ1234', 55),
(20, 'AR20KL4567', 40),
(21, 'MH22YZ5678', 48),
(22, 'GJ23AB1234', 52),
(23, 'DL24CD4567', 45),
(24, 'UP25EF7890', 50),
(25, 'RJ26GH1234', 42);

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `CustomerID` int(11) NOT NULL,
  `CustomerName` varchar(30) DEFAULT NULL,
  `CustomerNumber` char(10) DEFAULT NULL,
  `CustomerEmail` varchar(20) DEFAULT NULL,
  `CustomerDOB` date DEFAULT NULL,
  `Password` varchar(400) DEFAULT NULL,
  `DiscountPassID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`CustomerID`, `CustomerName`, `CustomerNumber`, `CustomerEmail`, `CustomerDOB`, `Password`, `DiscountPassID`) VALUES
(1, 'Olivia Martinez', '9876543211', 'oliviam@example.com', '1995-11-23', 'password123', 5),
(2, 'Ethan Brown', '1234567891', 'ethanb@example.com', '1988-04-15', 'password456', 2),
(3, 'Sophia Lee', '7894561231', 'sophial@example.com', '1992-07-08', 'password789', 1),
(4, 'Noah Davis', '3456789013', 'noahd@example.com', '1991-02-21', 'password101', 10),
(5, 'Ava Miller', '5678901235', 'avam@example.com', '1993-09-12', 'password111', 15),
(6, 'William Johnson', '9012345679', 'williamj@example.com', '1987-03-25', 'password122', 8),
(7, 'Mia Carter', '2345678956', 'mia69@gmail.com', '2004-08-02', 'password133', 23),
(8, 'James Smith', '6789012346', 'jamess@example.com', '1989-11-11', 'password144', 12),
(9, 'Evelyn Anderson', '8901234568', 'evelyn@example.com', '1990-05-04', 'password155', 18),
(10, 'Benjamin Wilson', '1234567892', 'benjaminw@example.co', '1992-08-20', 'password166', 6),
(11, 'Charlotte Thompson', '9876543212', 'charlottet@example.c', '1986-02-13', 'password177', 1),
(12, 'Oliver White', '4567891232', 'oliverw@example.com', '1993-10-07', 'password188', 13),
(13, 'Abigail Brown', '7894561233', 'abigailb@example.com', '1988-03-28', 'password199', 4),
(14, 'Lucas Davis', '3456789014', 'lucasd@example.com', '1991-11-15', 'password200', 11),
(15, 'Emily Miller', '5678901236', 'emilym@example.com', '1994-04-09', 'password211', 16),
(16, 'Logan Johnson', '9012345670', 'loganj@example.com', '1987-07-22', 'password222', 9),
(17, 'Grace Carter', '2345678903', 'gracec@example.com', '1995-01-16', 'password233', 7),
(18, 'Henry Smith', '6789012347', 'henrys@example.com', '1990-09-05', 'password244', 14),
(19, 'Amelia Anderson', '8901234569', 'amelia@example.com', '1991-02-28', 'password255', 19),
(20, 'Ethan Wilson', '1234567893', 'ethanw@example.com', '1993-11-13', 'password266', 20),
(24, 'shivam shukla', '7890123458', 'ss@gmail.com', '2005-11-08', 'Shivam811', 12),
(27, 'abc aa', '7572908024', 'abc@gmail.com', '2005-11-08', 'ca3eb21aa955a042d93a9bd81eceab347656d74f95009b7307fe9299c795c7dd', 15);

--
-- Triggers `customer`
--
DELIMITER $$
CREATE TRIGGER `after_customer_insert` AFTER INSERT ON `customer` FOR EACH ROW BEGIN
    INSERT INTO Passenger (PassengerName, PassengerNumber, PassengerEmail, PassengerDOB, DiscountPassID, AssociatedWith)
    VALUES (NEW.CustomerName, NEW.CustomerNumber, NEW.CustomerEmail, NEW.CustomerDOB, NEW.DiscountPassID, NEW.CustomerID);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `discountpass`
--

CREATE TABLE `discountpass` (
  `DiscountPassID` int(11) NOT NULL,
  `PassName` varchar(30) DEFAULT NULL,
  `DiscountPercentage` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `discountpass`
--

INSERT INTO `discountpass` (`DiscountPassID`, `PassName`, `DiscountPercentage`) VALUES
(1, 'No Pass', 0),
(2, 'Womens Pass', 10),
(3, 'Veteran Pass', 20),
(4, 'Army Pass', 15),
(5, 'Disabled Pass', 30),
(6, 'Senior Citizen Pass', 15),
(7, 'Student Pass', 25),
(8, 'Family Pass', 12),
(9, 'Group Pass', 8),
(10, 'Loyalty Pass', 5),
(11, 'Employee Pass', 30),
(12, 'Referral Pass', 10),
(13, 'Birthday Pass', 20),
(14, 'Anniversary Pass', 15),
(15, 'Weekend Pass', 8),
(16, 'Weekday Pass', 5),
(17, 'Early Bird Pass', 10),
(18, 'Night Owl Pass', 15),
(19, 'Seasonal Pass', 20),
(20, 'Holiday Pass', 25),
(21, 'Special Occasion Pass', 12),
(22, 'Student Family Pass', 18),
(23, 'Senior Couple Pass', 22),
(24, 'Military Family Pass', 25),
(25, 'Disabled Companion Pass', 35),
(26, 'Frequent Traveler Pass', 10);

-- --------------------------------------------------------

--
-- Table structure for table `passenger`
--

CREATE TABLE `passenger` (
  `PassengerID` int(11) NOT NULL,
  `PassengerName` varchar(30) DEFAULT NULL,
  `PassengerNumber` char(10) DEFAULT NULL,
  `PassengerEmail` varchar(20) DEFAULT NULL,
  `PassengerDOB` date DEFAULT NULL,
  `DiscountPassID` int(11) DEFAULT NULL,
  `AssociatedWith` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `passenger`
--

INSERT INTO `passenger` (`PassengerID`, `PassengerName`, `PassengerNumber`, `PassengerEmail`, `PassengerDOB`, `DiscountPassID`, `AssociatedWith`) VALUES
(1, 'Olivia Martinez', '9876543211', 'oliviam@example.com', '1995-11-23', 5, 1),
(2, 'Ethan Brown', '1234567891', 'ethanb@example.com', '1988-04-15', 2, 2),
(3, 'Sophia Lee', '7894561231', 'sophial@example.com', '1992-07-08', 1, 3),
(4, 'Noah Davis', '3456789013', 'noahd@example.com', '1991-02-21', 10, 4),
(5, 'Ava Miller', '5678901235', 'avam@example.com', '1993-09-12', 15, 5),
(6, 'William Johnson', '9012345679', 'williamj@example.com', '1987-03-25', 8, 6),
(7, 'Mia Carter', '2345678902', 'miac@example.com', '1994-06-18', 3, 7),
(8, 'James Smith', '6789012346', 'jamess@example.com', '1989-11-11', 12, 8),
(9, 'Evelyn Anderson', '8901234568', 'evelyn@example.com', '1990-05-04', 18, 9),
(10, 'Benjamin Wilson', '1234567892', 'benjaminw@example.co', '1992-08-20', 6, 10),
(11, 'Charlotte Thompson', '9876543212', 'charlottet@example.c', '1986-02-13', 1, 11),
(12, 'Oliver White', '4567891232', 'oliverw@example.com', '1993-10-07', 13, 12),
(13, 'Abigail Brown', '7894561233', 'abigailb@example.com', '1988-03-28', 4, 13),
(14, 'Lucas Davis', '3456789014', 'lucasd@example.com', '1991-11-15', 11, 14),
(15, 'Emily Miller', '5678901236', 'emilym@example.com', '1994-04-09', 16, 15),
(16, 'Logan Johnson', '9012345670', 'loganj@example.com', '1987-07-22', 9, 16),
(17, 'Grace Carter', '2345678903', 'gracec@example.com', '1995-01-16', 7, 17),
(18, 'Henry Smith', '6789012347', 'henrys@example.com', '1990-09-05', 14, 18),
(19, 'Amelia Anderson', '8901234569', 'amelia@example.com', '1991-02-28', 19, 19),
(20, 'Ethan Wilson', '1234567893', 'ethanw@example.com', '1993-11-13', 20, 20),
(21, 'John Doe', '1234667890', 'john.doe@passengers.', '1990-01-01', 1, 1),
(22, 'Jane Smith', '9876548211', 'jane.smith@passenger', '1992-02-02', 2, 2),
(23, 'Michael Johnson', '1357944681', 'michael.johnson@pass', '1988-03-03', 3, 3),
(24, 'Emily Brown', '2468077572', 'emily.brown@passenge', '1995-04-04', 3, 4),
(25, 'David Lee', '5678901735', 'david.lee@passengers', '1985-05-05', 1, 5),
(26, 'Olivia Miller', '4371098766', 'olivia.miller@passen', '1998-06-06', 2, 6),
(27, 'William Taylor', '7890723457', 'william.taylor@passe', '1982-07-07', 3, 7),
(28, 'Sophia Anderson', '3579746812', 'sophia.anderson@pass', '2000-08-08', 1, 8),
(29, 'James Wilson', '6105273848', 'james.wilson@passeng', '1979-09-09', 2, 9),
(30, 'Ava White', '924683973', 'ava.white@passengers', '2002-10-10', 3, 10),
(31, 'Benjamin Hall', '7472583692', 'benjamin.hall@passen', '1986-11-11', 1, 11),
(32, 'Charlotte Clark', '8569014726', 'charlotte.clark@pass', '1999-12-12', 2, 12),
(33, 'Thomas Harris', '2585690148', 'thomas.harris@passen', '1983-01-13', 3, 13),
(34, 'Amelia Jones', '9014525837', 'amelia.jones@passeng', '2001-02-14', 1, 14),
(35, 'Ethan Carter', '4725536903', 'ethan.carter@passeng', '1978-03-15', 2, 15),
(36, 'Grace Miller', '2583590149', 'grace.miller@passeng', '2003-04-16', 3, 16),
(37, 'Noah Davis', '8369015727', 'noah.davis@passenger', '1985-05-17', 1, 17),
(38, 'Mia Rodriguez', '1475583694', 'mia.rodriguez@passen', '1997-06-18', 2, 18),
(39, 'Oliver Martinez', '5536901473', 'oliver.martinez@pass', '1981-07-19', 3, 19),
(40, 'Lily Hernandez', '3650147259', 'lily.hernandez@passe', '2000-08-20', 1, 20),
(41, 'fer fer', '7890123456', 'fer@gmail.com', '2024-11-08', 10, 1),
(44, 'shail p', '6789012345', 'shail@gmail.com', '2005-08-11', 2, 2),
(46, 'shivam shukla', '7890123458', 'ss@gmail.com', '2005-11-08', 12, 24),
(49, 'abc aa', '7572908024', 'abc@gmail.com', '2005-11-08', 15, 27);

-- --------------------------------------------------------

--
-- Table structure for table `route`
--

CREATE TABLE `route` (
  `RouteID` int(11) NOT NULL,
  `StartLocation` varchar(50) DEFAULT NULL,
  `EndLocation` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `route`
--

INSERT INTO `route` (`RouteID`, `StartLocation`, `EndLocation`) VALUES
(1, 'Mumbai', 'Delhi'),
(2, 'Bangalore', 'Chennai'),
(3, 'Kolkata', 'Hyderabad'),
(4, 'Pune', 'Goa'),
(5, 'Jaipur', 'Agra'),
(6, 'Ahmedabad', 'Surat'),
(7, 'Lucknow', 'Kanpur'),
(8, 'Bhopal', 'Indore'),
(9, 'Nagpur', 'Raipur'),
(10, 'Patna', 'Ranchi'),
(11, 'Chandigarh', 'Shimla'),
(12, 'Guwahati', 'Shillong'),
(13, 'Kochi', 'Thiruvananthapuram'),
(14, 'Bhubaneswar', 'Puri'),
(15, 'Srinagar', 'Leh'),
(16, 'Darjeeling', 'Gangtok'),
(17, 'Udaipur', 'Jodhpur'),
(18, 'Varanasi', 'Allahabad'),
(19, 'Mysore', 'Bangalore'),
(20, 'Amritsar', 'Chandigarh'),
(21, 'Mumbai', 'Pune'),
(22, 'Bangalore', 'Hyderabad'),
(23, 'Kolkata', 'Chennai'),
(24, 'Delhi', 'Jaipur'),
(25, 'Ahmedabad', 'Indore');

-- --------------------------------------------------------

--
-- Table structure for table `ticket`
--

CREATE TABLE `ticket` (
  `TicketID` int(11) NOT NULL,
  `TripID` int(11) DEFAULT NULL,
  `BookedBy` int(11) DEFAULT NULL,
  `BookedFor` int(11) DEFAULT NULL,
  `BookTime` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `TicketContent` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ticket`
--

INSERT INTO `ticket` (`TicketID`, `TripID`, `BookedBy`, `BookedFor`, `BookTime`, `TicketContent`) VALUES
(1, 2, 7, 7, '2024-08-20 11:53:08', ''),
(2, 2, 7, 27, '2024-08-20 11:53:12', ''),
(3, 2, 7, 7, '2024-08-20 11:53:27', ''),
(4, 2, 7, 7, '2024-08-21 02:56:36', ''),
(5, 2, 7, 7, '2024-08-21 02:56:36', ''),
(6, 2, 7, 27, '2024-08-21 02:56:48', ''),
(7, 2, 7, 7, '2024-08-21 03:18:37', ''),
(8, 2, 7, 7, '2024-08-21 03:18:37', ''),
(9, 2, 7, 7, '2024-08-21 03:18:40', ''),
(10, 2, 7, 27, '2024-08-21 03:18:41', ''),
(11, 2, 7, 27, '2024-08-21 03:18:47', ''),
(12, 6, 1, 21, '2024-08-21 04:33:34', ''),
(13, 6, 1, 1, '2024-08-21 04:33:36', ''),
(14, 2, 1, 1, '2024-08-22 14:01:30', ''),
(15, 2, 1, 21, '2024-08-22 14:01:36', ''),
(16, 2, 1, 41, '2024-08-22 14:01:39', ''),
(17, 8, 2, 22, '2024-08-23 06:15:18', '---------------------------- Ticket Details --------------------------\r\nTicket ID: 17\r\nTicket Booking Time: 2024-08-23 11:45:09.0\r\nTicket Price: 670.0\r\n------------------------------- trip Details --------------------------\r\nTrip Start Time: 2024-08-23 12:30:00.0\r\nTrip End Time: 2024-08-23 13:00:00.0\r\nStart Location: Delhi\r\nEnd Location: Jaipur\r\n---------------------------------- Passenger Details ---------------------------\r\n\r\n1. Passenger Name: Jane Smith\r\npassenger PhoneNumber: 9876548211\r\nPassenger Email: jane.smith@passenger\r\nPassenger DOB: 1992-02-02\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n\r\n2. Passenger Name: Ethan Brown\r\npassenger PhoneNumber: 1234567891\r\nPassenger Email: ethanb@example.com\r\nPassenger DOB: 1988-04-15\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n'),
(18, 8, 2, 2, '2024-08-23 06:15:18', ''),
(19, 8, 2, 22, '2024-08-23 06:20:53', '---------------------------- Ticket Details --------------------------\r\nTicket ID: 19\r\nTicket Booking Time: 2024-08-23 11:50:49.0\r\nTicket Price: 670.0\r\n------------------------------- trip Details --------------------------\r\nTrip Start Time: 2024-08-23 12:30:00.0\r\nTrip End Time: 2024-08-23 13:00:00.0\r\nStart Location: Delhi\r\nEnd Location: Jaipur\r\n---------------------------------- Passenger Details ---------------------------\r\n\r\n1. Passenger Name: Jane Smith\r\npassenger PhoneNumber: 9876548211\r\nPassenger Email: jane.smith@passenger\r\nPassenger DOB: 1992-02-02\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n\r\n2. Passenger Name: Ethan Brown\r\npassenger PhoneNumber: 1234567891\r\nPassenger Email: ethanb@example.com\r\nPassenger DOB: 1988-04-15\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n'),
(20, 8, 2, 2, '2024-08-23 06:20:53', ''),
(21, 8, 2, 2, '2024-08-23 06:23:42', '---------------------------- Ticket Details --------------------------\r\nTicket ID: 21\r\nTicket Booking Time: 2024-08-23 11:53:41.0\r\nTicket Price: 670.0\r\n------------------------------- trip Details --------------------------\r\nTrip Start Time: 2024-08-23 12:30:00.0\r\nTrip End Time: 2024-08-23 13:00:00.0\r\nStart Location: Delhi\r\nEnd Location: Jaipur\r\n---------------------------------- Passenger Details ---------------------------\r\n\r\n1. Passenger Name: Ethan Brown\r\npassenger PhoneNumber: 1234567891\r\nPassenger Email: ethanb@example.com\r\nPassenger DOB: 1988-04-15\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n\r\n2. Passenger Name: Jane Smith\r\npassenger PhoneNumber: 9876548211\r\nPassenger Email: jane.smith@passenger\r\nPassenger DOB: 1992-02-02\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n'),
(22, 8, 2, 22, '2024-08-23 06:23:42', ''),
(23, 8, 2, 2, '2024-08-23 06:26:33', '---------------------------- Ticket Details --------------------------\r\nTicket ID: 23\r\nTicket Booking Time: 2024-08-23 11:56:32.0\r\nTicket Price: 670.0\r\n------------------------------- trip Details --------------------------\r\nTrip Start Time: 2024-08-23 12:30:00.0\r\nTrip End Time: 2024-08-23 13:00:00.0\r\nStart Location: Delhi\r\nEnd Location: Jaipur\r\n---------------------------------- Passenger Details ---------------------------\r\n\r\n1. Passenger Name: Ethan Brown\r\npassenger PhoneNumber: 1234567891\r\nPassenger Email: ethanb@example.com\r\nPassenger DOB: 1988-04-15\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n\r\n2. Passenger Name: Jane Smith\r\npassenger PhoneNumber: 9876548211\r\nPassenger Email: jane.smith@passenger\r\nPassenger DOB: 1992-02-02\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n'),
(24, 8, 2, 22, '2024-08-23 06:26:33', '---------------------------- Ticket Details --------------------------\r\nTicket ID: 23\r\nTicket Booking Time: 2024-08-23 11:56:32.0\r\nTicket Price: 670.0\r\n------------------------------- trip Details --------------------------\r\nTrip Start Time: 2024-08-23 12:30:00.0\r\nTrip End Time: 2024-08-23 13:00:00.0\r\nStart Location: Delhi\r\nEnd Location: Jaipur\r\n---------------------------------- Passenger Details ---------------------------\r\n\r\n1. Passenger Name: Ethan Brown\r\npassenger PhoneNumber: 1234567891\r\nPassenger Email: ethanb@example.com\r\nPassenger DOB: 1988-04-15\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n\r\n2. Passenger Name: Jane Smith\r\npassenger PhoneNumber: 9876548211\r\nPassenger Email: jane.smith@passenger\r\nPassenger DOB: 1992-02-02\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n'),
(25, 10, 2, 2, '2024-08-23 08:29:32', '---------------------------- Ticket Details --------------------------\r\nTicket ID: 25\r\nTicket Booking Time: 2024-08-23 13:59:31.0\r\nTicket Price: 890.0\r\n------------------------------- trip Details --------------------------\r\nTrip Start Time: 2024-08-23 14:09:00.0\r\nTrip End Time: 2024-08-23 15:00:00.0\r\nStart Location: Delhi\r\nEnd Location: Jaipur\r\n---------------------------------- Passenger Details ---------------------------\r\n\r\n1. Passenger Name: Ethan Brown\r\npassenger PhoneNumber: 1234567891\r\nPassenger Email: ethanb@example.com\r\nPassenger DOB: 1988-04-15\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n\r\n2. Passenger Name: Jane Smith\r\npassenger PhoneNumber: 9876548211\r\nPassenger Email: jane.smith@passenger\r\nPassenger DOB: 1992-02-02\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n'),
(26, 10, 2, 22, '2024-08-23 08:29:32', '---------------------------- Ticket Details --------------------------\r\nTicket ID: 25\r\nTicket Booking Time: 2024-08-23 13:59:31.0\r\nTicket Price: 890.0\r\n------------------------------- trip Details --------------------------\r\nTrip Start Time: 2024-08-23 14:09:00.0\r\nTrip End Time: 2024-08-23 15:00:00.0\r\nStart Location: Delhi\r\nEnd Location: Jaipur\r\n---------------------------------- Passenger Details ---------------------------\r\n\r\n1. Passenger Name: Ethan Brown\r\npassenger PhoneNumber: 1234567891\r\nPassenger Email: ethanb@example.com\r\nPassenger DOB: 1988-04-15\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n\r\n2. Passenger Name: Jane Smith\r\npassenger PhoneNumber: 9876548211\r\nPassenger Email: jane.smith@passenger\r\nPassenger DOB: 1992-02-02\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n'),
(27, 2, 8, 28, '2024-08-23 12:17:33', '---------------------------- Ticket Details --------------------------\r\nTicket ID: 27\r\nTicket Booking Time: 2024-08-23 17:47:32.0\r\nTicket Price: 132.23\r\n------------------------------- trip Details --------------------------\r\nTrip Start Time: 2024-08-24 16:30:28.0\r\nTrip End Time: 2024-08-24 20:45:12.0\r\nStart Location: Mumbai\r\nEnd Location: Delhi\r\n---------------------------------- Passenger Details ---------------------------\r\n\r\n1. Passenger Name: Sophia Anderson\r\npassenger PhoneNumber: 3579746812\r\nPassenger Email: sophia.anderson@pass\r\nPassenger DOB: 2000-08-08\r\nDiscountPass ID: 1\r\nDiscountPass Name: No Pass\r\n\r\n2. Passenger Name: James Smith\r\npassenger PhoneNumber: 6789012346\r\nPassenger Email: jamess@example.com\r\nPassenger DOB: 1989-11-11\r\nDiscountPass ID: 12\r\nDiscountPass Name: Referral Pass\r\n'),
(28, 2, 8, 8, '2024-08-23 12:17:33', '---------------------------- Ticket Details --------------------------\r\nTicket ID: 27\r\nTicket Booking Time: 2024-08-23 17:47:32.0\r\nTicket Price: 132.23\r\n------------------------------- trip Details --------------------------\r\nTrip Start Time: 2024-08-24 16:30:28.0\r\nTrip End Time: 2024-08-24 20:45:12.0\r\nStart Location: Mumbai\r\nEnd Location: Delhi\r\n---------------------------------- Passenger Details ---------------------------\r\n\r\n1. Passenger Name: Sophia Anderson\r\npassenger PhoneNumber: 3579746812\r\nPassenger Email: sophia.anderson@pass\r\nPassenger DOB: 2000-08-08\r\nDiscountPass ID: 1\r\nDiscountPass Name: No Pass\r\n\r\n2. Passenger Name: James Smith\r\npassenger PhoneNumber: 6789012346\r\nPassenger Email: jamess@example.com\r\nPassenger DOB: 1989-11-11\r\nDiscountPass ID: 12\r\nDiscountPass Name: Referral Pass\r\n'),
(29, 2, 2, 2, '2024-08-24 04:32:56', '---------------------------- Ticket Details --------------------------\r\nTicket ID: 29\r\nTicket Booking Time: 2024-08-24 10:02:54.0\r\nTicket Price: 132.23\r\n------------------------------- trip Details --------------------------\r\nTrip Start Time: 2024-08-24 16:30:28.0\r\nTrip End Time: 2024-08-24 20:45:12.0\r\nStart Location: Mumbai\r\nEnd Location: Delhi\r\n---------------------------------- Passenger Details ---------------------------\r\n\r\n1. Passenger Name: Ethan Brown\r\npassenger PhoneNumber: 1234567891\r\nPassenger Email: ethanb@example.com\r\nPassenger DOB: 1988-04-15\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n\r\n2. Passenger Name: Jane Smith\r\npassenger PhoneNumber: 9876548211\r\nPassenger Email: jane.smith@passenger\r\nPassenger DOB: 1992-02-02\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n'),
(30, 2, 2, 22, '2024-08-24 04:32:56', '---------------------------- Ticket Details --------------------------\r\nTicket ID: 29\r\nTicket Booking Time: 2024-08-24 10:02:54.0\r\nTicket Price: 132.23\r\n------------------------------- trip Details --------------------------\r\nTrip Start Time: 2024-08-24 16:30:28.0\r\nTrip End Time: 2024-08-24 20:45:12.0\r\nStart Location: Mumbai\r\nEnd Location: Delhi\r\n---------------------------------- Passenger Details ---------------------------\r\n\r\n1. Passenger Name: Ethan Brown\r\npassenger PhoneNumber: 1234567891\r\nPassenger Email: ethanb@example.com\r\nPassenger DOB: 1988-04-15\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n\r\n2. Passenger Name: Jane Smith\r\npassenger PhoneNumber: 9876548211\r\nPassenger Email: jane.smith@passenger\r\nPassenger DOB: 1992-02-02\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n'),
(31, 2, 2, 2, '2024-08-24 05:00:40', '---------------------------- Ticket Details --------------------------\r\nTicket ID: 31\r\nTicket Booking Time: 2024-08-24 10:30:38.0\r\nTicket Price: 132.23\r\n------------------------------- trip Details --------------------------\r\nTrip Start Time: 2024-08-24 16:30:28.0\r\nTrip End Time: 2024-08-24 20:45:12.0\r\nStart Location: Mumbai\r\nEnd Location: Delhi\r\n---------------------------------- Passenger Details ---------------------------\r\n\r\n1. Passenger Name: Ethan Brown\r\npassenger PhoneNumber: 1234567891\r\nPassenger Email: ethanb@example.com\r\nPassenger DOB: 1988-04-15\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n\r\n2. Passenger Name: Jane Smith\r\npassenger PhoneNumber: 9876548211\r\nPassenger Email: jane.smith@passenger\r\nPassenger DOB: 1992-02-02\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n'),
(32, 2, 2, 22, '2024-08-24 05:00:40', '---------------------------- Ticket Details --------------------------\r\nTicket ID: 31\r\nTicket Booking Time: 2024-08-24 10:30:38.0\r\nTicket Price: 132.23\r\n------------------------------- trip Details --------------------------\r\nTrip Start Time: 2024-08-24 16:30:28.0\r\nTrip End Time: 2024-08-24 20:45:12.0\r\nStart Location: Mumbai\r\nEnd Location: Delhi\r\n---------------------------------- Passenger Details ---------------------------\r\n\r\n1. Passenger Name: Ethan Brown\r\npassenger PhoneNumber: 1234567891\r\nPassenger Email: ethanb@example.com\r\nPassenger DOB: 1988-04-15\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n\r\n2. Passenger Name: Jane Smith\r\npassenger PhoneNumber: 9876548211\r\nPassenger Email: jane.smith@passenger\r\nPassenger DOB: 1992-02-02\r\nDiscountPass ID: 2\r\nDiscountPass Name: Womens Pass\r\n');

-- --------------------------------------------------------

--
-- Table structure for table `trip`
--

CREATE TABLE `trip` (
  `TripID` int(11) NOT NULL,
  `BusID` int(11) DEFAULT NULL,
  `RouteID` int(11) DEFAULT NULL,
  `StartTime` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `EndTime` timestamp NOT NULL DEFAULT current_timestamp(),
  `Price` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `trip`
--

INSERT INTO `trip` (`TripID`, `BusID`, `RouteID`, `StartTime`, `EndTime`, `Price`) VALUES
(2, 1, 1, '2024-08-24 11:00:28', '2024-08-24 15:15:12', 132.23),
(6, 15, 9, '2024-08-24 11:03:46', '2024-08-25 11:04:00', 132.00),
(8, 23, 24, '2024-08-23 07:00:00', '2024-08-23 07:30:00', 670.00),
(10, 22, 15, '2024-08-24 04:57:13', '2024-08-23 09:30:00', 890.00);

-- --------------------------------------------------------

--
-- Structure for view `allbus`
--
DROP TABLE IF EXISTS `allbus`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `allbus`  AS SELECT `bus`.`BusID` AS `BusID`, `bus`.`NumberPlate` AS `NumberPlate`, `bus`.`NumberOfSeats` AS `NumberOfSeats` FROM `bus` ;

-- --------------------------------------------------------

--
-- Structure for view `allbusids`
--
DROP TABLE IF EXISTS `allbusids`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `allbusids`  AS SELECT `bus`.`BusID` AS `BusID` FROM `bus` ;

-- --------------------------------------------------------

--
-- Structure for view `alldiscountpass`
--
DROP TABLE IF EXISTS `alldiscountpass`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `alldiscountpass`  AS SELECT `discountpass`.`DiscountPassID` AS `DiscountPassID`, `discountpass`.`PassName` AS `PassName`, `discountpass`.`DiscountPercentage` AS `DiscountPercentage` FROM `discountpass` ;

-- --------------------------------------------------------

--
-- Structure for view `alldiscountpassids`
--
DROP TABLE IF EXISTS `alldiscountpassids`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `alldiscountpassids`  AS SELECT `discountpass`.`DiscountPassID` AS `DiscountPassID` FROM `discountpass` ;

-- --------------------------------------------------------

--
-- Structure for view `allroute`
--
DROP TABLE IF EXISTS `allroute`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `allroute`  AS SELECT `route`.`RouteID` AS `RouteID`, `route`.`StartLocation` AS `StartLocation`, `route`.`EndLocation` AS `EndLocation` FROM `route` ;

-- --------------------------------------------------------

--
-- Structure for view `allrouteids`
--
DROP TABLE IF EXISTS `allrouteids`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `allrouteids`  AS SELECT `route`.`RouteID` AS `RouteID` FROM `route` ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `bus`
--
ALTER TABLE `bus`
  ADD PRIMARY KEY (`BusID`),
  ADD UNIQUE KEY `NumberPlate` (`NumberPlate`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`CustomerID`),
  ADD UNIQUE KEY `CustomerNumber` (`CustomerNumber`),
  ADD UNIQUE KEY `CustomerEmail` (`CustomerEmail`),
  ADD UNIQUE KEY `CustomerNumber_2` (`CustomerNumber`),
  ADD KEY `DiscountPassID` (`DiscountPassID`);

--
-- Indexes for table `discountpass`
--
ALTER TABLE `discountpass`
  ADD PRIMARY KEY (`DiscountPassID`),
  ADD UNIQUE KEY `PassName` (`PassName`);

--
-- Indexes for table `passenger`
--
ALTER TABLE `passenger`
  ADD PRIMARY KEY (`PassengerID`),
  ADD UNIQUE KEY `PassengerNumber` (`PassengerNumber`),
  ADD UNIQUE KEY `PassengerEmail` (`PassengerEmail`),
  ADD KEY `DiscountPassID` (`DiscountPassID`),
  ADD KEY `AssociatedWith` (`AssociatedWith`);

--
-- Indexes for table `route`
--
ALTER TABLE `route`
  ADD PRIMARY KEY (`RouteID`);

--
-- Indexes for table `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`TicketID`),
  ADD KEY `TripID` (`TripID`),
  ADD KEY `BookedBy` (`BookedBy`),
  ADD KEY `BookedFor` (`BookedFor`);

--
-- Indexes for table `trip`
--
ALTER TABLE `trip`
  ADD PRIMARY KEY (`TripID`),
  ADD KEY `BusID` (`BusID`),
  ADD KEY `RouteID` (`RouteID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `bus`
--
ALTER TABLE `bus`
  MODIFY `BusID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `CustomerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `discountpass`
--
ALTER TABLE `discountpass`
  MODIFY `DiscountPassID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `passenger`
--
ALTER TABLE `passenger`
  MODIFY `PassengerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT for table `route`
--
ALTER TABLE `route`
  MODIFY `RouteID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `ticket`
--
ALTER TABLE `ticket`
  MODIFY `TicketID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `trip`
--
ALTER TABLE `trip`
  MODIFY `TripID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`DiscountPassID`) REFERENCES `discountpass` (`DiscountPassID`);

--
-- Constraints for table `passenger`
--
ALTER TABLE `passenger`
  ADD CONSTRAINT `passenger_ibfk_1` FOREIGN KEY (`DiscountPassID`) REFERENCES `discountpass` (`DiscountPassID`),
  ADD CONSTRAINT `passenger_ibfk_2` FOREIGN KEY (`AssociatedWith`) REFERENCES `customer` (`CustomerID`);

--
-- Constraints for table `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`TripID`) REFERENCES `trip` (`TripID`),
  ADD CONSTRAINT `ticket_ibfk_2` FOREIGN KEY (`BookedBy`) REFERENCES `customer` (`CustomerID`),
  ADD CONSTRAINT `ticket_ibfk_3` FOREIGN KEY (`BookedFor`) REFERENCES `passenger` (`PassengerID`);

--
-- Constraints for table `trip`
--
ALTER TABLE `trip`
  ADD CONSTRAINT `trip_ibfk_1` FOREIGN KEY (`BusID`) REFERENCES `bus` (`BusID`),
  ADD CONSTRAINT `trip_ibfk_2` FOREIGN KEY (`RouteID`) REFERENCES `route` (`RouteID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
