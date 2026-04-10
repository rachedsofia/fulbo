-- =============================================
-- FULBO - Datos de prueba para perfil test (H2)
-- =============================================

-- Torneos
INSERT INTO tournaments (id, name, season, type, start_date, end_date) VALUES
(1, 'Liga Profesional 2026', '2026', 'LEAGUE', '2026-02-01', '2026-12-15'),
(2, 'Copa Argentina 2026', '2026', 'CUP', '2026-03-01', '2026-11-30'),
(3, 'Copa Libertadores 2026', '2026', 'INTERNATIONAL', '2026-02-15', '2026-11-25');

-- Clubes argentinos (28 clubes reales)
INSERT INTO clubs (id, name, short_name, logo_url, stadium_name, city, founded_year) VALUES
(1, 'River Plate', 'RIV', 'https://upload.wikimedia.org/wikipedia/commons/a/ac/Escudo_del_C_A_River_Plate.svg', 'Estadio Monumental', 'Buenos Aires', 1901),
(2, 'Boca Juniors', 'BOC', 'https://upload.wikimedia.org/wikipedia/commons/4/41/Escudo_del_Club_Atl%C3%A9tico_Boca_Juniors.svg', 'La Bombonera', 'Buenos Aires', 1905),
(3, 'Racing Club', 'RAC', 'https://upload.wikimedia.org/wikipedia/commons/5/56/Escudo_de_Racing_Club_%282014%29.svg', 'Estadio Presidente Perón', 'Avellaneda', 1903),
(4, 'Independiente', 'IND', 'https://upload.wikimedia.org/wikipedia/commons/7/74/Escudo_del_Club_Atl%C3%A9tico_Independiente.svg', 'Estadio Libertadores de América', 'Avellaneda', 1905),
(5, 'San Lorenzo', 'SLO', 'https://upload.wikimedia.org/wikipedia/commons/1/12/SanLorenzo.svg', 'Nuevo Gasómetro', 'Buenos Aires', 1908),
(6, 'Huracán', 'HUR', 'https://upload.wikimedia.org/wikipedia/commons/8/87/Escudo_del_Club_Atl%C3%A9tico_Hurac%C3%A1n.svg', 'Estadio Tomás Adolfo Ducó', 'Buenos Aires', 1908),
(7, 'Vélez Sarsfield', 'VEL', 'https://upload.wikimedia.org/wikipedia/commons/2/21/Escudo_Velez_Sarsfield.svg', 'Estadio José Amalfitani', 'Buenos Aires', 1910),
(8, 'Argentinos Juniors', 'ARJ', 'https://upload.wikimedia.org/wikipedia/commons/0/09/Escudo_de_la_Asociaci%C3%B3n_Atl%C3%A9tica_Argentinos_Juniors.svg', 'Estadio Diego Maradona', 'Buenos Aires', 1904),
(9, 'Lanús', 'LAN', 'https://upload.wikimedia.org/wikipedia/commons/4/46/Escudo_del_Club_Atl%C3%A9tico_Lan%C3%BAs.svg', 'Estadio Ciudad de Lanús', 'Lanús', 1915),
(10, 'Defensa y Justicia', 'DYJ', 'https://upload.wikimedia.org/wikipedia/commons/c/c0/Defensa_y_Justicia.svg', 'Estadio Norberto Tomaghello', 'Florencio Varela', 1935),
(11, 'Estudiantes LP', 'EDL', 'https://upload.wikimedia.org/wikipedia/commons/b/be/Escudo_de_Estudiantes_de_La_Plata.svg', 'Estadio Jorge Luis Hirschi', 'La Plata', 1905),
(12, 'Gimnasia LP', 'GLP', 'https://upload.wikimedia.org/wikipedia/commons/8/85/Escudo_del_Club_de_Gimnasia_y_Esgrima_La_Plata.svg', 'Estadio Juan Carmelo Zerillo', 'La Plata', 1887),
(13, 'Rosario Central', 'RCE', 'https://upload.wikimedia.org/wikipedia/commons/f/f4/Escudo_Rosario_Central.svg', 'Estadio Gigante de Arroyito', 'Rosario', 1889),
(14, 'Newell''s Old Boys', 'NOB', 'https://upload.wikimedia.org/wikipedia/commons/4/44/Escudo_del_Club_Atl%C3%A9tico_Newell%27s_Old_Boys_de_Rosario.svg', 'Estadio Marcelo Bielsa', 'Rosario', 1903),
(15, 'Talleres', 'TAL', 'https://upload.wikimedia.org/wikipedia/commons/8/8d/Escudo_del_Talleres_de_C%C3%B3rdoba.svg', 'Estadio Mario Alberto Kempes', 'Córdoba', 1913),
(16, 'Belgrano', 'BEL', 'https://upload.wikimedia.org/wikipedia/commons/9/99/Escudo_del_Club_Atl%C3%A9tico_Belgrano.svg', 'Estadio Julio César Villagra', 'Córdoba', 1905),
(17, 'Godoy Cruz', 'GCR', 'https://upload.wikimedia.org/wikipedia/commons/2/2e/Escudo_Godoy_Cruz.svg', 'Estadio Malvinas Argentinas', 'Mendoza', 1921),
(18, 'Unión', 'UNI', 'https://upload.wikimedia.org/wikipedia/commons/f/ff/Escudo_del_Club_Atl%C3%A9tico_Uni%C3%B3n_%28Santa_Fe%29.svg', 'Estadio 15 de Abril', 'Santa Fe', 1907),
(19, 'Colón', 'COL', 'https://upload.wikimedia.org/wikipedia/commons/2/2d/Escudo_del_Club_Atl%C3%A9tico_Col%C3%B3n.svg', 'Estadio Brigadier López', 'Santa Fe', 1905),
(20, 'Banfield', 'BAN', 'https://upload.wikimedia.org/wikipedia/commons/5/56/Escudo_del_Club_Atl%C3%A9tico_Banfield.svg', 'Estadio Florencio Sola', 'Banfield', 1896),
(21, 'Platense', 'PLA', 'https://upload.wikimedia.org/wikipedia/commons/4/49/Escudo_del_Club_Atl%C3%A9tico_Platense.svg', 'Estadio Ciudad de Vicente López', 'Vicente López', 1905),
(22, 'Tigre', 'TIG', 'https://upload.wikimedia.org/wikipedia/commons/1/13/Escudo_del_Club_Atl%C3%A9tico_Tigre_-_2019.svg', 'Estadio José Dellagiovanna', 'Victoria', 1902),
(23, 'Barracas Central', 'BAR', 'https://upload.wikimedia.org/wikipedia/commons/2/21/Escudo_de_Barracas_Central.svg', 'Estadio Claudio Chiqui Tapia', 'Buenos Aires', 1904),
(24, 'Instituto', 'INS', 'https://upload.wikimedia.org/wikipedia/commons/5/54/Escudo_del_Instituto_Atl%C3%A9tico_Central_C%C3%B3rdoba.svg', 'Estadio Juan Domingo Perón', 'Córdoba', 1918),
(25, 'Central Córdoba SdE', 'CCS', 'https://upload.wikimedia.org/wikipedia/commons/6/60/Central_Cordoba_SdE.svg', 'Estadio Alfredo Terrera', 'Santiago del Estero', 1919),
(26, 'Sarmiento', 'SAR', 'https://upload.wikimedia.org/wikipedia/commons/7/7c/Escudo_de_Sarmiento_de_Jun%C3%ADn.svg', 'Estadio Eva Perón', 'Junín', 1911),
(27, 'Atlético Tucumán', 'ATU', 'https://upload.wikimedia.org/wikipedia/commons/5/50/Escudo_del_Club_Atl%C3%A9tico_Tucum%C3%A1n.svg', 'Estadio Monumental José Fierro', 'San Miguel de Tucumán', 1902),
(28, 'Deportivo Riestra', 'RIE', 'https://upload.wikimedia.org/wikipedia/commons/8/89/Deportivo_Riestra.svg', 'Estadio Guillermo Laza', 'Buenos Aires', 1931);

-- Jugadores de River Plate (Club 1)
INSERT INTO players (id, club_id, first_name, last_name, position, nationality, shirt_number, market_value) VALUES
(1, 1, 'Franco', 'Armani', 'GK', 'Argentina', 1, 3000000.0),
(2, 1, 'Paulo', 'Díaz', 'DEF', 'Chile', 3, 8000000.0),
(3, 1, 'Germán', 'Pezzella', 'DEF', 'Argentina', 6, 5000000.0),
(4, 1, 'Milton', 'Casco', 'DEF', 'Argentina', 20, 2000000.0),
(5, 1, 'Enzo', 'Díaz', 'DEF', 'Argentina', 23, 3500000.0),
(6, 1, 'Rodrigo', 'Aliendro', 'MID', 'Argentina', 14, 3000000.0),
(7, 1, 'Nicolás', 'De La Cruz', 'MID', 'Uruguay', 10, 12000000.0),
(8, 1, 'Ignacio', 'Fernández', 'MID', 'Argentina', 26, 4000000.0),
(9, 1, 'Pablo', 'Solari', 'FWD', 'Argentina', 29, 10000000.0),
(10, 1, 'Miguel', 'Borja', 'FWD', 'Colombia', 9, 5000000.0),
(11, 1, 'Facundo', 'Colidio', 'FWD', 'Argentina', 18, 4000000.0);

-- Jugadores de Boca Juniors (Club 2)
INSERT INTO players (id, club_id, first_name, last_name, position, nationality, shirt_number, market_value) VALUES
(12, 2, 'Sergio', 'Romero', 'GK', 'Argentina', 1, 1500000.0),
(13, 2, 'Marcos', 'Rojo', 'DEF', 'Argentina', 6, 2000000.0),
(14, 2, 'Cristian', 'Lema', 'DEF', 'Argentina', 3, 2500000.0),
(15, 2, 'Frank', 'Fabra', 'DEF', 'Colombia', 18, 2000000.0),
(16, 2, 'Luis', 'Advíncula', 'DEF', 'Peru', 17, 3000000.0),
(17, 2, 'Cristian', 'Medina', 'MID', 'Argentina', 29, 8000000.0),
(18, 2, 'Kevin', 'Zenón', 'MID', 'Argentina', 10, 15000000.0),
(19, 2, 'Pol', 'Fernández', 'MID', 'Argentina', 8, 2000000.0),
(20, 2, 'Miguel', 'Merentiel', 'FWD', 'Uruguay', 16, 5000000.0),
(21, 2, 'Edinson', 'Cavani', 'FWD', 'Uruguay', 9, 3000000.0),
(22, 2, 'Luca', 'Langoni', 'FWD', 'Argentina', 27, 4000000.0);

-- Jugadores de Racing Club (Club 3)
INSERT INTO players (id, club_id, first_name, last_name, position, nationality, shirt_number, market_value) VALUES
(23, 3, 'Gabriel', 'Arias', 'GK', 'Chile', 1, 2500000.0),
(24, 3, 'Facundo', 'Mura', 'DEF', 'Argentina', 4, 3000000.0),
(25, 3, 'Marco', 'Di Cesare', 'DEF', 'Argentina', 6, 2000000.0),
(26, 3, 'Juan José', 'Cáceres', 'DEF', 'Paraguay', 3, 2500000.0),
(27, 3, 'Agustín', 'Almendra', 'MID', 'Argentina', 5, 6000000.0),
(28, 3, 'Juan Fernando', 'Quintero', 'MID', 'Colombia', 10, 4000000.0),
(29, 3, 'Roger', 'Martínez', 'FWD', 'Colombia', 9, 5000000.0),
(30, 3, 'Adrián', 'Martínez', 'FWD', 'Argentina', 19, 3000000.0);

-- Jugadores de Independiente (Club 4)
INSERT INTO players (id, club_id, first_name, last_name, position, nationality, shirt_number, market_value) VALUES
(31, 4, 'Rodrigo', 'Rey', 'GK', 'Argentina', 1, 1500000.0),
(32, 4, 'Sergio', 'Barreto', 'DEF', 'Argentina', 2, 2000000.0),
(33, 4, 'Iván', 'Marcone', 'MID', 'Argentina', 5, 1500000.0),
(34, 4, 'Alan', 'Velasco', 'MID', 'Argentina', 10, 8000000.0),
(35, 4, 'Leandro', 'Fernández', 'FWD', 'Argentina', 9, 4000000.0);

-- Jugadores de San Lorenzo (Club 5)
INSERT INTO players (id, club_id, first_name, last_name, position, nationality, shirt_number, market_value) VALUES
(36, 5, 'Gastón', 'Gómez', 'GK', 'Argentina', 1, 1000000.0),
(37, 5, 'Gonzalo', 'Luján', 'DEF', 'Argentina', 4, 2000000.0),
(38, 5, 'Agustín', 'Martegani', 'MID', 'Argentina', 10, 6000000.0),
(39, 5, 'Adam', 'Bareiro', 'FWD', 'Paraguay', 9, 4000000.0);

-- Jugadores de Talleres (Club 15)
INSERT INTO players (id, club_id, first_name, last_name, position, nationality, shirt_number, market_value) VALUES
(40, 15, 'Guido', 'Herrera', 'GK', 'Argentina', 1, 3000000.0),
(41, 15, 'Gastón', 'Benavídez', 'DEF', 'Argentina', 2, 2000000.0),
(42, 15, 'Rodrigo', 'Garro', 'MID', 'Argentina', 10, 7000000.0),
(43, 15, 'Michael', 'Santos', 'FWD', 'Uruguay', 9, 4000000.0);

-- Partidos Fecha 1 - Liga Profesional 2026
INSERT INTO matches (id, home_club_id, away_club_id, tournament_id, match_date, home_score, away_score, status, stadium, matchday) VALUES
(1, 1, 2, 1, '2026-02-08 21:00:00', 2, 1, 'FINISHED', 'Estadio Monumental', 1),
(2, 3, 4, 1, '2026-02-08 19:00:00', 1, 1, 'FINISHED', 'Estadio Presidente Perón', 1),
(3, 5, 6, 1, '2026-02-08 17:00:00', 3, 0, 'FINISHED', 'Nuevo Gasómetro', 1),
(4, 7, 8, 1, '2026-02-09 19:00:00', 2, 2, 'FINISHED', 'Estadio José Amalfitani', 1),
(5, 9, 10, 1, '2026-02-09 21:00:00', 1, 0, 'FINISHED', 'Estadio Ciudad de Lanús', 1),
(6, 11, 12, 1, '2026-02-10 17:00:00', 2, 0, 'FINISHED', 'Estadio Jorge Luis Hirschi', 1),
(7, 13, 14, 1, '2026-02-10 19:00:00', 0, 1, 'FINISHED', 'Estadio Gigante de Arroyito', 1),
(8, 15, 16, 1, '2026-02-10 21:00:00', 3, 1, 'FINISHED', 'Estadio Mario Alberto Kempes', 1);

-- Partidos Fecha 2 - Liga Profesional 2026
INSERT INTO matches (id, home_club_id, away_club_id, tournament_id, match_date, home_score, away_score, status, stadium, matchday) VALUES
(9, 2, 3, 1, '2026-02-15 21:00:00', 0, 2, 'FINISHED', 'La Bombonera', 2),
(10, 4, 1, 1, '2026-02-15 19:00:00', 1, 3, 'FINISHED', 'Estadio Libertadores de América', 2),
(11, 6, 7, 1, '2026-02-15 17:00:00', 1, 2, 'FINISHED', 'Estadio Tomás Adolfo Ducó', 2),
(12, 8, 5, 1, '2026-02-16 19:00:00', 0, 1, 'FINISHED', 'Estadio Diego Maradona', 2),
(13, 10, 9, 1, '2026-02-16 21:00:00', 2, 2, 'FINISHED', 'Estadio Norberto Tomaghello', 2),
(14, 14, 15, 1, '2026-02-16 19:00:00', 1, 1, 'FINISHED', 'Estadio Marcelo Bielsa', 2);

-- Partidos Fecha 3 - Liga Profesional 2026 (algunos en vivo/programados)
INSERT INTO matches (id, home_club_id, away_club_id, tournament_id, match_date, home_score, away_score, status, stadium, matchday) VALUES
(15, 1, 3, 1, '2026-04-10 21:00:00', 1, 0, 'LIVE', 'Estadio Monumental', 3),
(16, 2, 5, 1, '2026-04-10 19:00:00', 2, 2, 'HALFTIME', 'La Bombonera', 3),
(17, 15, 7, 1, '2026-04-11 21:00:00', NULL, NULL, 'SCHEDULED', 'Estadio Mario Alberto Kempes', 3),
(18, 4, 6, 1, '2026-04-11 19:00:00', NULL, NULL, 'SCHEDULED', 'Estadio Libertadores de América', 3),
(19, 9, 11, 1, '2026-04-12 17:00:00', NULL, NULL, 'SCHEDULED', 'Estadio Ciudad de Lanús', 3),
(20, 13, 16, 1, '2026-04-12 19:00:00', NULL, NULL, 'SCHEDULED', 'Estadio Gigante de Arroyito', 3);

-- Copa Argentina - Partidos
INSERT INTO matches (id, home_club_id, away_club_id, tournament_id, match_date, home_score, away_score, status, stadium, matchday) VALUES
(21, 1, 15, 2, '2026-03-20 21:00:00', 4, 1, 'FINISHED', 'Estadio Monumental', 1),
(22, 2, 9, 2, '2026-03-21 21:00:00', 2, 0, 'FINISHED', 'La Bombonera', 1),
(23, 3, 5, 2, '2026-03-22 19:00:00', 1, 0, 'FINISHED', 'Estadio Presidente Perón', 1);

-- Copa Libertadores - Partidos
INSERT INTO matches (id, home_club_id, away_club_id, tournament_id, match_date, home_score, away_score, status, stadium, matchday) VALUES
(24, 1, 2, 3, '2026-04-15 21:00:00', NULL, NULL, 'SCHEDULED', 'Estadio Monumental', 1),
(25, 3, 4, 3, '2026-04-16 21:00:00', NULL, NULL, 'SCHEDULED', 'Estadio Presidente Perón', 1);

-- Estadísticas de jugadores - Partido 1 (River 2 - Boca 1)
INSERT INTO player_stats (player_id, match_id, goals, assists, yellow_cards, red_cards, minutes_played, saves, passes, shots_on_target, tackles, rating) VALUES
(10, 1, 2, 0, 0, 0, 90, 0, 28, 4, 1, 9.2),
(7, 1, 0, 2, 0, 0, 90, 0, 62, 1, 3, 8.5),
(9, 1, 0, 0, 1, 0, 85, 0, 35, 3, 2, 7.4),
(1, 1, 0, 0, 0, 0, 90, 5, 22, 0, 0, 7.8),
(2, 1, 0, 0, 0, 0, 90, 0, 45, 0, 6, 7.2),
(3, 1, 0, 0, 1, 0, 90, 0, 52, 0, 4, 7.0),
(21, 1, 1, 0, 0, 0, 90, 0, 20, 3, 0, 7.5),
(18, 1, 0, 1, 1, 0, 90, 0, 55, 2, 2, 7.0),
(12, 1, 0, 0, 0, 0, 90, 3, 18, 0, 0, 6.8),
(13, 1, 0, 0, 1, 0, 90, 0, 38, 0, 3, 6.5);

-- Estadísticas de jugadores - Partido 2 (Racing 1 - Independiente 1)
INSERT INTO player_stats (player_id, match_id, goals, assists, yellow_cards, red_cards, minutes_played, saves, passes, shots_on_target, tackles, rating) VALUES
(28, 2, 1, 0, 0, 0, 90, 0, 58, 3, 1, 8.0),
(29, 2, 0, 1, 0, 0, 78, 0, 22, 2, 0, 7.3),
(23, 2, 0, 0, 0, 0, 90, 4, 25, 0, 0, 7.5),
(35, 2, 1, 0, 0, 0, 90, 0, 18, 4, 1, 7.8),
(34, 2, 0, 1, 1, 0, 90, 0, 48, 2, 2, 7.2),
(31, 2, 0, 0, 0, 0, 90, 3, 20, 0, 0, 7.0);

-- Estadísticas de jugadores - Partido 3 (San Lorenzo 3 - Huracán 0)
INSERT INTO player_stats (player_id, match_id, goals, assists, yellow_cards, red_cards, minutes_played, saves, passes, shots_on_target, tackles, rating) VALUES
(39, 3, 2, 0, 0, 0, 90, 0, 24, 5, 0, 9.0),
(38, 3, 1, 2, 0, 0, 90, 0, 65, 3, 1, 8.8),
(36, 3, 0, 0, 0, 0, 90, 2, 30, 0, 0, 7.5);

-- Estadísticas - Partido 8 (Talleres 3 - Belgrano 1)
INSERT INTO player_stats (player_id, match_id, goals, assists, yellow_cards, red_cards, minutes_played, saves, passes, shots_on_target, tackles, rating) VALUES
(42, 8, 2, 1, 0, 0, 90, 0, 55, 4, 1, 9.5),
(43, 8, 1, 0, 0, 0, 75, 0, 18, 3, 0, 7.8),
(40, 8, 0, 0, 0, 0, 90, 3, 28, 0, 0, 7.2);

-- Estadísticas - Partido 9 (Boca 0 - Racing 2)
INSERT INTO player_stats (player_id, match_id, goals, assists, yellow_cards, red_cards, minutes_played, saves, passes, shots_on_target, tackles, rating) VALUES
(29, 9, 1, 0, 0, 0, 90, 0, 20, 3, 1, 8.2),
(28, 9, 1, 1, 0, 0, 90, 0, 62, 2, 2, 8.5),
(23, 9, 0, 0, 0, 0, 90, 6, 22, 0, 0, 8.0),
(12, 9, 0, 0, 0, 0, 90, 2, 18, 0, 0, 6.0),
(21, 9, 0, 0, 1, 0, 85, 0, 15, 2, 0, 5.5);

-- Estadísticas - Partido 10 (Independiente 1 - River 3)
INSERT INTO player_stats (player_id, match_id, goals, assists, yellow_cards, red_cards, minutes_played, saves, passes, shots_on_target, tackles, rating) VALUES
(10, 10, 2, 0, 0, 0, 90, 0, 22, 5, 0, 9.4),
(9, 10, 1, 1, 0, 0, 88, 0, 38, 3, 1, 8.2),
(7, 10, 0, 2, 0, 0, 90, 0, 70, 1, 2, 8.7),
(1, 10, 0, 0, 0, 0, 90, 2, 25, 0, 0, 7.5),
(35, 10, 1, 0, 0, 0, 90, 0, 16, 3, 1, 7.0),
(31, 10, 0, 0, 0, 0, 90, 5, 18, 0, 0, 6.5);

-- Estadísticas - Partido 21 (River 4 - Talleres 1 - Copa Argentina)
INSERT INTO player_stats (player_id, match_id, goals, assists, yellow_cards, red_cards, minutes_played, saves, passes, shots_on_target, tackles, rating) VALUES
(10, 21, 2, 1, 0, 0, 90, 0, 30, 6, 0, 9.6),
(9, 21, 1, 0, 0, 0, 82, 0, 32, 4, 1, 8.0),
(7, 21, 1, 2, 0, 0, 90, 0, 68, 2, 3, 9.0),
(42, 21, 1, 0, 1, 0, 90, 0, 42, 2, 1, 7.0);

-- Usuario admin predeterminado (password: admin123)
INSERT INTO users (id, username, email, password, display_name, role, reputation, followers_count, following_count, active, created_at, updated_at) VALUES
(1, 'admin', 'admin@fulbo.com', '$2a$10$ctPwIKFbcQoEd8V0nZQS0ODDGy2vV8dkdz6NDu1QTID.R2tKiyp.6', 'Administrador Fulbo', 'ADMIN', 100, 0, 0, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Posts de ejemplo
INSERT INTO posts (id, user_id, content, type, club_id, tournament_id, likes_count, comments_count, shares_count, active, created_at, updated_at) VALUES
(1, 1, 'Arrancó la Liga Profesional 2026! River le ganó el Superclásico a Boca 2-1 con doblete de Borja. Tremendo partido en el Monumental.', 'TEXT', 1, 1, 156, 32, 18, true, '2026-02-08 23:30:00', '2026-02-08 23:30:00'),
(2, 1, 'San Lorenzo goleó 3-0 a Huracán en el clásico del barrio. Bareiro imparable con un doblete. Martegani puso el tercero con un golazo de tiro libre.', 'TEXT', 5, 1, 89, 15, 7, true, '2026-02-08 19:30:00', '2026-02-08 19:30:00'),
(3, 1, 'Talleres aplastó a Belgrano 3-1 en el clásico cordobés. Garro fue la figura con 2 goles y 1 asistencia. Rating 9.5!', 'TEXT', 15, 1, 120, 28, 12, true, '2026-02-10 23:00:00', '2026-02-10 23:00:00'),
(4, 1, 'FECHA 2: Racing sorprende en la Bombonera! Le ganó 2-0 a Boca con goles de Quintero y Roger Martínez. La Academia es líder con puntaje ideal.', 'TEXT', 3, 1, 203, 45, 25, true, '2026-02-15 23:00:00', '2026-02-15 23:00:00'),
(5, 1, 'River no para! Goleó 3-1 a Independiente de visitante. Borja ya lleva 4 goles en 2 fechas. Es el goleador del torneo.', 'TEXT', 1, 1, 178, 38, 20, true, '2026-02-15 21:30:00', '2026-02-15 21:30:00'),
(6, 1, 'COPA ARGENTINA: River avanza con goleada 4-1 sobre Talleres. Borja intratable: 2 goles y 1 asistencia. De La Cruz magia pura con 1 gol y 2 asistencias.', 'TEXT', 1, 2, 245, 52, 30, true, '2026-03-20 23:30:00', '2026-03-20 23:30:00'),
(7, 1, 'EN VIVO: River vs Racing por la Fecha 3. River gana 1-0 por ahora. Partidazo en el Monumental!', 'TEXT', 1, 1, 67, 89, 5, true, '2026-04-10 21:30:00', '2026-04-10 21:30:00');
