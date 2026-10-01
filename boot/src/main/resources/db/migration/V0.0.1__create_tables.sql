CREATE TABLE coaches (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE participants (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE sessions (
    id BIGSERIAL PRIMARY KEY,
    coach_id BIGINT NOT NULL,
    location VARCHAR(255) NOT NULL,
    time_zone VARCHAR(100) NOT NULL,
    start_time TIMESTAMPTZ NOT NULL,
    end_time TIMESTAMPTZ NOT NULL,
    capacity INTEGER NOT NULL CHECK (capacity > 0),
    CONSTRAINT fk_sessions_coaches FOREIGN KEY (coach_id) REFERENCES coaches (id) ON DELETE RESTRICT
);

CREATE TABLE session_participants (
    session_id BIGINT NOT NULL,
    participant_id BIGINT NOT NULL,
    registered_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_session_participants PRIMARY KEY (session_id, participant_id),
    CONSTRAINT fk_sp_sessions FOREIGN KEY (session_id) REFERENCES sessions (id) ON DELETE RESTRICT,
    CONSTRAINT fk_sp_participants FOREIGN KEY (participant_id) REFERENCES participants (id) ON DELETE RESTRICT
);

CREATE INDEX idx_sessions_coach_id ON sessions (coach_id);
CREATE INDEX idx_session_participants_participant_id ON session_participants (participant_id);
