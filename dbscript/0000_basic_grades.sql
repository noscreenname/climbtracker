--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.0
-- Dumped by pg_dump version 9.5.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = public, pg_catalog;

--
-- Data for Name: grade; Type: TABLE DATA; Schema: public; Owner: climbtracker
--

INSERT INTO grade VALUES (1002, '1', 1, 1001);
INSERT INTO grade VALUES (1003, '1+', 2, 1001);
INSERT INTO grade VALUES (1004, '2-', 3, 1001);
INSERT INTO grade VALUES (1005, '2', 4, 1001);
INSERT INTO grade VALUES (1006, '2+', 5, 1001);
INSERT INTO grade VALUES (1007, '3-', 6, 1001);
INSERT INTO grade VALUES (1008, '3', 7, 1001);
INSERT INTO grade VALUES (1009, '3+', 8, 1001);
INSERT INTO grade VALUES (1010, '4-', 9, 1001);
INSERT INTO grade VALUES (1011, '4', 10, 1001);
INSERT INTO grade VALUES (1012, '4+', 11, 1001);
INSERT INTO grade VALUES (1013, '5-', 12, 1001);
INSERT INTO grade VALUES (1014, '5', 13, 1001);
INSERT INTO grade VALUES (1015, '5+', 14, 1001);
INSERT INTO grade VALUES (1016, '6A', 15, 1001);
INSERT INTO grade VALUES (1017, '6A+', 16, 1001);
INSERT INTO grade VALUES (1018, '6B', 17, 1001);
INSERT INTO grade VALUES (1019, '6B+', 18, 1001);
INSERT INTO grade VALUES (1020, '6C', 19, 1001);
INSERT INTO grade VALUES (1021, '6C+', 20, 1001);
INSERT INTO grade VALUES (1022, '7A', 21, 1001);
INSERT INTO grade VALUES (1023, '7A+', 22, 1001);
INSERT INTO grade VALUES (1024, '7B', 23, 1001);
INSERT INTO grade VALUES (1025, '7B+', 24, 1001);
INSERT INTO grade VALUES (1026, '7C', 25, 1001);
INSERT INTO grade VALUES (1027, '7C+', 26, 1001);
INSERT INTO grade VALUES (1028, '8A', 27, 1001);
INSERT INTO grade VALUES (1029, '8A+', 28, 1001);
INSERT INTO grade VALUES (1030, '8B', 29, 1001);
INSERT INTO grade VALUES (1031, '8B+', 30, 1001);
INSERT INTO grade VALUES (1032, '8C', 31, 1001);
INSERT INTO grade VALUES (1033, '8C+', 32, 1001);
INSERT INTO grade VALUES (1035, 'Yellow', 1, 1034);
INSERT INTO grade VALUES (1036, 'Green', 2, 1034);
INSERT INTO grade VALUES (1037, 'Blue', 3, 1034);
INSERT INTO grade VALUES (1038, 'Red', 4, 1034);
INSERT INTO grade VALUES (1039, 'Black', 5, 1034);
INSERT INTO grade VALUES (1040, 'Purple', 6, 1034);
INSERT INTO grade VALUES (1041, '1', 1, 1041);
INSERT INTO grade VALUES (1042, '2', 2, 1041);
INSERT INTO grade VALUES (1043, '3a', 3, 1041);
INSERT INTO grade VALUES (1044, '3b', 4, 1041);
INSERT INTO grade VALUES (1045, '3c', 5, 1041);
INSERT INTO grade VALUES (1046, '4a', 6, 1041);
INSERT INTO grade VALUES (1047, '4b', 7, 1041);
INSERT INTO grade VALUES (1048, '4c', 8, 1041);
INSERT INTO grade VALUES (1049, '5a', 9, 1041);
INSERT INTO grade VALUES (1050, '5a+', 10, 1041);
INSERT INTO grade VALUES (1051, '5b', 11, 1041);
INSERT INTO grade VALUES (1052, '5b+', 12, 1041);
INSERT INTO grade VALUES (1053, '5c', 13, 1041);
INSERT INTO grade VALUES (1054, '5c+', 14, 1041);
INSERT INTO grade VALUES (1055, '6a', 15, 1041);
INSERT INTO grade VALUES (1056, '6a+', 16, 1041);
INSERT INTO grade VALUES (1057, '6b', 17, 1041);
INSERT INTO grade VALUES (1058, '6b+', 18, 1041);
INSERT INTO grade VALUES (1059, '6c', 19, 1041);
INSERT INTO grade VALUES (1060, '6c+', 20, 1041);
INSERT INTO grade VALUES (1061, '7a', 21, 1041);
INSERT INTO grade VALUES (1062, '7a+', 22, 1041);
INSERT INTO grade VALUES (1063, '7b', 23, 1041);
INSERT INTO grade VALUES (1064, '7b+', 24, 1041);
INSERT INTO grade VALUES (1065, '7c', 25, 1041);
INSERT INTO grade VALUES (1066, '7c+', 26, 1041);
INSERT INTO grade VALUES (1067, '8a', 27, 1041);
INSERT INTO grade VALUES (1068, '8a+', 28, 1041);
INSERT INTO grade VALUES (1069, '8b', 29, 1041);
INSERT INTO grade VALUES (1070, '8b+', 30, 1041);
INSERT INTO grade VALUES (1071, '8c', 31, 1041);
INSERT INTO grade VALUES (1072, '8c+', 32, 1041);
INSERT INTO grade VALUES (1073, '9a', 33, 1041);
INSERT INTO grade VALUES (1074, '9a+', 34, 1041);
INSERT INTO grade VALUES (1075, '9b', 35, 1041);
INSERT INTO grade VALUES (1076, '9b+', 36, 1041);
INSERT INTO grade VALUES (1077, '9c', 37, 1041);
INSERT INTO grade VALUES (1078, '9c+', 38, 1041);


--
-- Name: grade_id_seq; Type: SEQUENCE SET; Schema: public; Owner: climbtracker
--

SELECT pg_catalog.setval('grade_id_seq', 1, false);


--
-- Data for Name: grade_system; Type: TABLE DATA; Schema: public; Owner: climbtracker
--

INSERT INTO grade_system VALUES (1001, 'Fontainebleau');
INSERT INTO grade_system VALUES (1034, 'Arkose Color');
INSERT INTO grade_system VALUES (1041, 'French');


--
-- Name: grade_system_id_seq; Type: SEQUENCE SET; Schema: public; Owner: climbtracker
--

SELECT pg_catalog.setval('grade_system_id_seq', 1, false);


--
-- PostgreSQL database dump complete
--

