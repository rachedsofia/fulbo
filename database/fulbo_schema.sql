-- ============================================================
-- FULBO - Script de Base de Datos MySQL
-- Plataforma de Fútbol Argentino
-- ============================================================

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS fulbo
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE fulbo;

-- ============================================================
-- 1. USUARIOS
-- ============================================================
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    display_name VARCHAR(100),
    avatar_url VARCHAR(500),
    bio VARCHAR(500),
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    reputation INT NOT NULL DEFAULT 0,
    followers_count INT NOT NULL DEFAULT 0,
    following_count INT NOT NULL DEFAULT 0,
    favorite_club_id BIGINT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT uk_users_username UNIQUE (username),
    CONSTRAINT uk_users_email UNIQUE (email)
) ENGINE=InnoDB;

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_role ON users(role);

-- ============================================================
-- 2. SEGUIDORES
-- ============================================================
CREATE TABLE followers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    follower_id BIGINT NOT NULL,
    following_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_followers_follower FOREIGN KEY (follower_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_followers_following FOREIGN KEY (following_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uk_followers UNIQUE (follower_id, following_id)
) ENGINE=InnoDB;

-- ============================================================
-- 3. TORNEOS
-- ============================================================
CREATE TABLE tournaments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    short_name VARCHAR(50),
    season VARCHAR(20),
    country VARCHAR(50) DEFAULT 'Argentina',
    logo_url VARCHAR(500),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    start_date DATE,
    end_date DATE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ============================================================
-- 4. CLUBES
-- ============================================================
CREATE TABLE clubs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    short_name VARCHAR(10),
    logo_url VARCHAR(500),
    stadium_name VARCHAR(200),
    city VARCHAR(100),
    founded_year INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE INDEX idx_clubs_name ON clubs(name);

-- ============================================================
-- 5. JUGADORES
-- ============================================================
CREATE TABLE players (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    club_id BIGINT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    position VARCHAR(30),
    nationality VARCHAR(50),
    shirt_number INT,
    photo_url VARCHAR(500),
    market_value BIGINT DEFAULT 0,
    date_of_birth DATE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_players_club FOREIGN KEY (club_id) REFERENCES clubs(id) ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE INDEX idx_players_club ON players(club_id);
CREATE INDEX idx_players_position ON players(position);
CREATE INDEX idx_players_name ON players(last_name, first_name);

-- ============================================================
-- 6. PARTIDOS
-- ============================================================
CREATE TABLE matches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    home_club_id BIGINT NOT NULL,
    away_club_id BIGINT NOT NULL,
    tournament_id BIGINT,
    match_date DATETIME,
    home_score INT,
    away_score INT,
    status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED',
    stadium VARCHAR(200),
    matchday INT,
    referee VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_matches_home FOREIGN KEY (home_club_id) REFERENCES clubs(id),
    CONSTRAINT fk_matches_away FOREIGN KEY (away_club_id) REFERENCES clubs(id),
    CONSTRAINT fk_matches_tournament FOREIGN KEY (tournament_id) REFERENCES tournaments(id)
) ENGINE=InnoDB;

CREATE INDEX idx_matches_tournament ON matches(tournament_id);
CREATE INDEX idx_matches_date ON matches(match_date);
CREATE INDEX idx_matches_status ON matches(status);
CREATE INDEX idx_matches_home_club ON matches(home_club_id);
CREATE INDEX idx_matches_away_club ON matches(away_club_id);

-- ============================================================
-- 7. ESTADÍSTICAS DE JUGADORES POR PARTIDO
-- ============================================================
CREATE TABLE player_stats (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    player_id BIGINT NOT NULL,
    match_id BIGINT NOT NULL,
    goals INT DEFAULT 0,
    assists INT DEFAULT 0,
    yellow_cards INT DEFAULT 0,
    red_cards INT DEFAULT 0,
    minutes_played INT DEFAULT 0,
    saves INT DEFAULT 0,
    passes INT DEFAULT 0,
    shots_on_target INT DEFAULT 0,
    tackles INT DEFAULT 0,
    rating DECIMAL(3,1),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_stats_player FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE,
    CONSTRAINT fk_stats_match FOREIGN KEY (match_id) REFERENCES matches(id) ON DELETE CASCADE,
    CONSTRAINT uk_player_match_stats UNIQUE (player_id, match_id)
) ENGINE=InnoDB;

CREATE INDEX idx_stats_player ON player_stats(player_id);
CREATE INDEX idx_stats_match ON player_stats(match_id);

-- ============================================================
-- 8. PUBLICACIONES (POSTS)
-- ============================================================
CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    type VARCHAR(20) NOT NULL DEFAULT 'TEXT',
    media_url VARCHAR(500),
    club_id BIGINT,
    tournament_id BIGINT,
    likes_count INT NOT NULL DEFAULT 0,
    comments_count INT NOT NULL DEFAULT 0,
    shares_count INT NOT NULL DEFAULT 0,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_posts_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_posts_club FOREIGN KEY (club_id) REFERENCES clubs(id) ON DELETE SET NULL,
    CONSTRAINT fk_posts_tournament FOREIGN KEY (tournament_id) REFERENCES tournaments(id) ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE INDEX idx_posts_user ON posts(user_id);
CREATE INDEX idx_posts_club ON posts(club_id);
CREATE INDEX idx_posts_created ON posts(created_at DESC);
CREATE INDEX idx_posts_active_created ON posts(active, created_at DESC);

-- ============================================================
-- 9. COMENTARIOS
-- ============================================================
CREATE TABLE comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    parent_comment_id BIGINT,
    likes_count INT NOT NULL DEFAULT 0,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_comments_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    CONSTRAINT fk_comments_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_comments_parent FOREIGN KEY (parent_comment_id) REFERENCES comments(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE INDEX idx_comments_post ON comments(post_id);
CREATE INDEX idx_comments_user ON comments(user_id);

-- ============================================================
-- 10. REACCIONES
-- ============================================================
CREATE TABLE reactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL DEFAULT 'LIKE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_reactions_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_reactions_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    CONSTRAINT uk_reactions_user_post UNIQUE (user_id, post_id)
) ENGINE=InnoDB;

CREATE INDEX idx_reactions_post ON reactions(post_id);

-- ============================================================
-- 11. LIGAS FANTASY
-- ============================================================
CREATE TABLE fantasy_leagues (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    owner_id BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL DEFAULT 'PUBLIC',
    code VARCHAR(20),
    max_teams INT NOT NULL DEFAULT 20,
    tournament_id BIGINT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_fleague_owner FOREIGN KEY (owner_id) REFERENCES users(id),
    CONSTRAINT fk_fleague_tournament FOREIGN KEY (tournament_id) REFERENCES tournaments(id),
    CONSTRAINT uk_fleague_code UNIQUE (code)
) ENGINE=InnoDB;

CREATE INDEX idx_fleague_type ON fantasy_leagues(type);
CREATE INDEX idx_fleague_owner ON fantasy_leagues(owner_id);

-- ============================================================
-- 12. EQUIPOS FANTASY
-- ============================================================
CREATE TABLE fantasy_teams (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(200) NOT NULL,
    league_id BIGINT,
    total_points INT NOT NULL DEFAULT 0,
    budget BIGINT NOT NULL DEFAULT 100000000,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_fteam_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_fteam_league FOREIGN KEY (league_id) REFERENCES fantasy_leagues(id) ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE INDEX idx_fteam_user ON fantasy_teams(user_id);
CREATE INDEX idx_fteam_league ON fantasy_teams(league_id);

-- ============================================================
-- 13. JUGADORES EN EQUIPOS FANTASY
-- ============================================================
CREATE TABLE fantasy_team_players (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fantasy_team_id BIGINT NOT NULL,
    player_id BIGINT NOT NULL,
    club_id BIGINT,
    is_captain BOOLEAN NOT NULL DEFAULT FALSE,
    position VARCHAR(30),

    CONSTRAINT fk_ftp_team FOREIGN KEY (fantasy_team_id) REFERENCES fantasy_teams(id) ON DELETE CASCADE,
    CONSTRAINT fk_ftp_player FOREIGN KEY (player_id) REFERENCES players(id),
    CONSTRAINT fk_ftp_club FOREIGN KEY (club_id) REFERENCES clubs(id),
    CONSTRAINT uk_ftp_team_player UNIQUE (fantasy_team_id, player_id)
) ENGINE=InnoDB;

CREATE INDEX idx_ftp_team ON fantasy_team_players(fantasy_team_id);

-- ============================================================
-- 14. PREDICCIONES
-- ============================================================
CREATE TABLE predictions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    match_id BIGINT NOT NULL,
    predicted_home_score INT NOT NULL,
    predicted_away_score INT NOT NULL,
    points INT NOT NULL DEFAULT 0,
    resolved BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_predictions_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_predictions_match FOREIGN KEY (match_id) REFERENCES matches(id) ON DELETE CASCADE,
    CONSTRAINT uk_predictions_user_match UNIQUE (user_id, match_id)
) ENGINE=InnoDB;

CREATE INDEX idx_predictions_user ON predictions(user_id);
CREATE INDEX idx_predictions_match ON predictions(match_id);
CREATE INDEX idx_predictions_resolved ON predictions(resolved);

-- ============================================================
-- 15. REFRESH TOKENS
-- ============================================================
CREATE TABLE refresh_tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    token VARCHAR(500) NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_rtoken_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uk_rtoken UNIQUE (token)
) ENGINE=InnoDB;

CREATE INDEX idx_rtoken_user ON refresh_tokens(user_id);

-- ============================================================
-- 16. NOTIFICACIONES
-- ============================================================
CREATE TABLE notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    type VARCHAR(50) NOT NULL,
    title VARCHAR(200) NOT NULL,
    message TEXT,
    reference_id BIGINT,
    reference_type VARCHAR(50),
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_notif_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE INDEX idx_notif_user ON notifications(user_id);
CREATE INDEX idx_notif_read ON notifications(user_id, is_read);

-- ============================================================
-- DATOS INICIALES - Clubes del fútbol argentino
-- ============================================================
INSERT INTO clubs (name, short_name, stadium_name, city, founded_year) VALUES
('River Plate', 'RIV', 'Estadio Monumental', 'Buenos Aires', 1901),
('Boca Juniors', 'BOC', 'La Bombonera', 'Buenos Aires', 1905),
('Racing Club', 'RAC', 'Estadio Presidente Perón', 'Avellaneda', 1903),
('Independiente', 'IND', 'Estadio Libertadores de América', 'Avellaneda', 1905),
('San Lorenzo', 'SLO', 'Estadio Pedro Bidegain', 'Buenos Aires', 1908),
('Huracán', 'HUR', 'Estadio Tomás Adolfo Ducó', 'Buenos Aires', 1908),
('Vélez Sarsfield', 'VEL', 'Estadio José Amalfitani', 'Buenos Aires', 1910),
('Argentinos Juniors', 'ARG', 'Estadio Diego Armando Maradona', 'Buenos Aires', 1904),
('Lanús', 'LAN', 'Estadio Ciudad de Lanús', 'Lanús', 1915),
('Banfield', 'BAN', 'Estadio Florencio Sola', 'Banfield', 1896),
('Estudiantes', 'EST', 'Estadio Jorge Luis Hirschi', 'La Plata', 1905),
('Gimnasia y Esgrima LP', 'GEL', 'Estadio Juan Carmelo Zerillo', 'La Plata', 1887),
('Newells Old Boys', 'NOB', 'Estadio Marcelo Bielsa', 'Rosario', 1903),
('Rosario Central', 'RCE', 'Estadio Gigante de Arroyito', 'Rosario', 1889),
('Talleres', 'TAL', 'Estadio Mario Alberto Kempes', 'Córdoba', 1913),
('Belgrano', 'BEL', 'Estadio Julio César Villagra', 'Córdoba', 1905),
('Instituto', 'INS', 'Estadio Juan Domingo Perón', 'Córdoba', 1918),
('Colón', 'COL', 'Estadio Brigadier General Estanislao López', 'Santa Fe', 1905),
('Unión', 'UNI', 'Estadio 15 de Abril', 'Santa Fe', 1907),
('Godoy Cruz', 'GCR', 'Estadio Malvinas Argentinas', 'Mendoza', 1921),
('Defensa y Justicia', 'DYJ', 'Estadio Norberto Tomaghello', 'Florencio Varela', 1935),
('Arsenal de Sarandí', 'ARS', 'Estadio Julio Humberto Grondona', 'Sarandí', 1957),
('Platense', 'PLA', 'Estadio Ciudad de Vicente López', 'Vicente López', 1905),
('Tigre', 'TIG', 'Estadio José Dellagiovanna', 'Victoria', 1902),
('Central Córdoba', 'CCO', 'Estadio Alfredo Terrera', 'Santiago del Estero', 1919),
('Barracas Central', 'BAR', 'Estadio Claudio Chiqui Tapia', 'Buenos Aires', 1904),
('Sarmiento', 'SAR', 'Estadio Eva Perón', 'Junín', 1911),
('Atlético Tucumán', 'ATU', 'Estadio Monumental José Fierro', 'Tucumán', 1902);

-- ============================================================
-- DATOS INICIALES - Torneos
-- ============================================================
INSERT INTO tournaments (name, short_name, season, country) VALUES
('Liga Profesional de Fútbol', 'LPF', '2026', 'Argentina'),
('Copa Argentina', 'COPA', '2026', 'Argentina'),
('Copa de la Liga Profesional', 'CLP', '2026', 'Argentina'),
('Copa Libertadores', 'LIB', '2026', 'Sudamérica'),
('Copa Sudamericana', 'SUD', '2026', 'Sudamérica');

-- ============================================================
-- DATOS INICIALES - Liga Fantasy pública
-- ============================================================
-- Nota: Se necesita un usuario admin para crear la liga
-- INSERT INTO users (username, email, password, display_name, role) VALUES
-- ('admin', 'admin@fulbo.com', '$2a$10$...', 'Administrador', 'ADMIN');

-- ============================================================
-- VISTAS ÚTILES
-- ============================================================

-- Vista: Tabla de goleadores
CREATE OR REPLACE VIEW v_top_scorers AS
SELECT
    p.id AS player_id,
    p.first_name,
    p.last_name,
    c.name AS club_name,
    c.short_name AS club_short,
    SUM(ps.goals) AS total_goals,
    SUM(ps.assists) AS total_assists,
    COUNT(ps.id) AS matches_played,
    ROUND(AVG(ps.rating), 1) AS avg_rating
FROM players p
JOIN player_stats ps ON p.id = ps.player_id
JOIN clubs c ON p.club_id = c.id
GROUP BY p.id, p.first_name, p.last_name, c.name, c.short_name
ORDER BY total_goals DESC;

-- Vista: Tabla de posiciones (simplificada)
CREATE OR REPLACE VIEW v_standings AS
SELECT
    sub.club_id,
    sub.club_name,
    sub.short_name,
    sub.wins,
    sub.draws,
    sub.losses,
    sub.goals_for,
    sub.goals_against,
    sub.goals_for - sub.goals_against AS goal_difference,
    sub.points
FROM (
    SELECT
        c.id AS club_id,
        c.name AS club_name,
        c.short_name,
        COUNT(CASE WHEN (m.home_club_id = c.id AND m.home_score > m.away_score) OR
                        (m.away_club_id = c.id AND m.away_score > m.home_score) THEN 1 END) AS wins,
        COUNT(CASE WHEN m.home_score = m.away_score THEN 1 END) AS draws,
        COUNT(CASE WHEN (m.home_club_id = c.id AND m.home_score < m.away_score) OR
                        (m.away_club_id = c.id AND m.away_score < m.home_score) THEN 1 END) AS losses,
        COALESCE(SUM(CASE WHEN m.home_club_id = c.id THEN m.home_score
                          WHEN m.away_club_id = c.id THEN m.away_score END), 0) AS goals_for,
        COALESCE(SUM(CASE WHEN m.home_club_id = c.id THEN m.away_score
                          WHEN m.away_club_id = c.id THEN m.home_score END), 0) AS goals_against,
        COUNT(CASE WHEN (m.home_club_id = c.id AND m.home_score > m.away_score) OR
                        (m.away_club_id = c.id AND m.away_score > m.home_score) THEN 1 END) * 3 +
        COUNT(CASE WHEN m.home_score = m.away_score THEN 1 END) AS points
    FROM clubs c
    LEFT JOIN matches m ON (m.home_club_id = c.id OR m.away_club_id = c.id) AND m.status = 'FINISHED'
    GROUP BY c.id, c.name, c.short_name
) AS sub
ORDER BY sub.points DESC, sub.goals_for - sub.goals_against DESC;

-- Vista: Ranking de predicciones
CREATE OR REPLACE VIEW v_prediction_ranking AS
SELECT
    u.id AS user_id,
    u.username,
    u.display_name,
    u.avatar_url,
    SUM(pr.points) AS total_points,
    COUNT(pr.id) AS total_predictions,
    COUNT(CASE WHEN pr.resolved = TRUE THEN 1 END) AS resolved_predictions,
    COUNT(CASE WHEN pr.points = 5 THEN 1 END) AS exact_predictions
FROM users u
JOIN predictions pr ON u.id = pr.user_id
GROUP BY u.id, u.username, u.display_name, u.avatar_url
ORDER BY total_points DESC;

-- Vista: Ranking fantasy
CREATE OR REPLACE VIEW v_fantasy_ranking AS
SELECT
    ft.id AS team_id,
    ft.name AS team_name,
    u.username,
    u.display_name,
    fl.name AS league_name,
    ft.total_points,
    ft.budget,
    COUNT(ftp.id) AS players_count
FROM fantasy_teams ft
JOIN users u ON ft.user_id = u.id
LEFT JOIN fantasy_leagues fl ON ft.league_id = fl.id
LEFT JOIN fantasy_team_players ftp ON ft.id = ftp.fantasy_team_id
GROUP BY ft.id, ft.name, u.username, u.display_name, fl.name, ft.total_points, ft.budget
ORDER BY ft.total_points DESC;

-- ============================================================
-- FIN DEL SCRIPT
-- ============================================================
