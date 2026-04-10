# Fulbo - Plataforma de Fútbol Argentino

Plataforma digital full-stack sobre fútbol argentino que combina red social, fantasy sports, predicciones y estadísticas deportivas.

## Stack Técnico

### Backend
- **Java 17** + **Spring Boot 3.2.5**
- **Arquitectura**: Clean / Hexagonal
- **API REST** con documentación OpenAPI (Swagger)
- **Seguridad**: JWT + OAuth2
- **Base de datos**: MySQL 8.0
- **ORM**: JPA / Hibernate
- **Cache**: Redis
- **Mensajería**: RabbitMQ
- **Mapeo**: MapStruct 1.5.5

### Frontend
- **Angular 18** con standalone components
- **Arquitectura modular** + lazy loading
- **Estado**: Signals (Angular 18)
- **Responsive** (mobile-first)
- **Integración**: HTTP Client con interceptors JWT

### Infraestructura
- **Docker** + **Docker Compose**
- **GitHub Actions** CI/CD
- **Kubernetes ready**

## Estructura del Proyecto

```
fulbo/
├── backend/                    # Spring Boot API
│   └── src/main/java/com/fulbo/
│       ├── domain/             # Modelos y puertos (hexagonal)
│       │   ├── model/          # 13 domain models
│       │   └── port/           # in (use cases) + out (repositories)
│       ├── application/        # DTOs, mappers, servicios
│       │   ├── dto/            # Request/Response DTOs
│       │   ├── mapper/         # Entity mappers
│       │   └── service/        # Business logic
│       └── infrastructure/     # Adaptadores, config, seguridad
│           ├── adapter/in/rest/    # 6 REST controllers
│           ├── adapter/out/persistence/  # JPA entities, repos, adapters
│           ├── config/         # Security, CORS, OpenAPI
│           └── security/       # JWT provider, filter, UserDetails
├── frontend/                   # Angular 18 SPA
│   └── src/app/
│       ├── core/               # Services, interceptors, guards
│       ├── auth/               # Login, Register
│       ├── feed/               # Feed, PostCard, CreatePost
│       ├── fantasy/            # Team, League, Ranking
│       ├── stats/              # Clubs, Players, Match detail
│       ├── shared/             # Header, Footer
│       └── models/             # TypeScript interfaces
├── docker-compose.yml          # MySQL + Redis + RabbitMQ + Backend + Frontend
├── .github/workflows/ci.yml   # CI/CD pipeline
└── docs/                       # Documentación
```

## Inicio Rápido

### Con Docker Compose
```bash
docker-compose up -d
```
- Frontend: http://localhost
- Backend API: http://localhost:8080/api
- Swagger UI: http://localhost:8080/api/swagger-ui.html
- RabbitMQ Admin: http://localhost:15672

### Desarrollo Local

**Backend:**
```bash
cd backend
mvn spring-boot:run
```

**Frontend:**
```bash
cd frontend
npm install
ng serve
```
- Frontend dev: http://localhost:4200
- Backend: http://localhost:8080/api

## Modelo de Datos

### Entidades principales
| Entidad | Descripción |
|---------|-------------|
| User | Usuarios con roles, reputación, seguidores |
| Post | Publicaciones: texto, imagen, video, encuestas |
| Comment | Comentarios en publicaciones (anidados) |
| Reaction | Reacciones (like, love, etc.) |
| Club | Clubes de fútbol argentino |
| Player | Jugadores con posición, nacionalidad, valor |
| Match | Partidos con marcadores y estados |
| Tournament | Torneos (Liga Profesional, Copa, etc.) |
| PlayerStats | Estadísticas por partido por jugador |
| FantasyTeam | Equipos fantasy con presupuesto |
| FantasyTeamPlayer | Jugadores en equipos fantasy |
| FantasyLeague | Ligas fantasy públicas/privadas |
| Prediction | Predicciones de resultados |

## Endpoints REST

### Autenticación (`/api/auth`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/auth/register` | Registro de usuario |
| POST | `/auth/login` | Login (retorna JWT) |
| POST | `/auth/refresh` | Refresh token |
| GET | `/auth/me` | Usuario actual |

### Publicaciones (`/api/posts`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/posts` | Crear publicación |
| GET | `/posts/feed` | Feed paginado |
| GET | `/posts/{id}` | Detalle de post |
| GET | `/posts/user/{userId}` | Posts de usuario |
| GET | `/posts/club/{clubId}` | Posts de club |
| PUT | `/posts/{id}` | Actualizar post |
| DELETE | `/posts/{id}` | Eliminar post (soft) |
| POST | `/posts/{id}/like` | Like |
| DELETE | `/posts/{id}/like` | Unlike |

### Fantasy (`/api/fantasy`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/fantasy/teams` | Crear equipo |
| GET | `/fantasy/teams/me` | Mi equipo |
| POST | `/fantasy/teams/{id}/players` | Agregar jugador |
| DELETE | `/fantasy/teams/{id}/players/{pid}` | Quitar jugador |
| POST | `/fantasy/leagues` | Crear liga |
| GET | `/fantasy/leagues/public` | Ligas públicas |
| POST | `/fantasy/leagues/join` | Unirse a liga (código) |
| GET | `/fantasy/leagues/{id}/ranking` | Ranking de liga |

### Estadísticas (`/api/stats`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/stats/clubs` | Todos los clubes |
| GET | `/stats/clubs/{id}` | Detalle de club |
| GET | `/stats/clubs/{id}/players` | Plantel |
| GET | `/stats/players/{id}` | Detalle jugador |
| GET | `/stats/tournaments/{id}/matches` | Partidos |
| GET | `/stats/matches/{id}` | Detalle partido |
| GET | `/stats/tournaments/{id}/top-scorers` | Goleadores |

### Predicciones (`/api/predictions`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/predictions` | Crear predicción |
| GET | `/predictions/me` | Mis predicciones |
| GET | `/predictions/match/{id}` | Predicciones de partido |

## Roadmap

### Fase 1 - MVP (Actual)
- [x] Autenticación JWT (registro/login)
- [x] CRUD de publicaciones con feed social
- [x] Sistema de likes y reacciones
- [x] Fantasy: creación de equipos y ligas
- [x] Estadísticas: clubes, jugadores, partidos
- [x] Predicciones de resultados
- [x] Frontend Angular responsive
- [x] Docker + CI/CD

### Fase 2 - Engagement
- [ ] Comentarios anidados con WebSocket en tiempo real
- [ ] Sistema de seguidores y notificaciones
- [ ] Perfiles de usuario enriquecidos
- [ ] Grupos y comunidades por club
- [ ] Sistema de reputación/ranking
- [ ] Encuestas interactivas

### Fase 3 - Data & Gaming
- [ ] Integración con APIs externas (estadísticas en vivo)
- [ ] Cálculo automático de puntos fantasy por jornada
- [ ] Comparador de jugadores avanzado
- [ ] Predicciones mejoradas (goleadores, campeones)
- [ ] Recompensas simbólicas y logros
- [ ] Push notifications

### Fase 4 - Escalado
- [ ] Migración a microservicios (Kubernetes)
- [ ] Event sourcing con Kafka
- [ ] Cache distribuido (Redis Cluster)
- [ ] CDN para contenido multimedia
- [ ] Rate limiting y throttling
- [ ] OAuth2 social login (Google, Apple)

### Fase 5 - Monetización
- [ ] Plan premium con funcionalidades extra
- [ ] Perfiles verificados para clubes/periodistas
- [ ] Integración con streaming en vivo
- [ ] Publicidad contextual
- [ ] Marketplace de predicciones
- [ ] API pública para terceros

## Seguridad
- JWT con firma HMAC-SHA256
- Refresh tokens con expiración 7 días
- BCrypt para hashing de contraseñas
- CORS configurado para orígenes permitidos
- Endpoints públicos: auth, feed (GET), stats (GET), ligas públicas
- Endpoints protegidos: crear/editar contenido, fantasy, predicciones

## Licencia
MIT
