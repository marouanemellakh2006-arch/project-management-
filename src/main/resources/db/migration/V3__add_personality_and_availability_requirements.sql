-- Flyway Migration V3: Add personality and availability requirements to projects
-- These fields are consumed by the AI Recommendation microservice.

ALTER TABLE projects
    ADD COLUMN IF NOT EXISTS personality_extraversion      INT  NOT NULL DEFAULT 50,
    ADD COLUMN IF NOT EXISTS personality_agreeableness     INT  NOT NULL DEFAULT 50,
    ADD COLUMN IF NOT EXISTS personality_conscientiousness INT  NOT NULL DEFAULT 50,
    ADD COLUMN IF NOT EXISTS personality_neuroticism       INT  NOT NULL DEFAULT 50,
    ADD COLUMN IF NOT EXISTS personality_openness          INT  NOT NULL DEFAULT 50,
    ADD COLUMN IF NOT EXISTS required_from                 DATE NOT NULL DEFAULT CURRENT_DATE,
    ADD COLUMN IF NOT EXISTS required_until                DATE NOT NULL DEFAULT (CURRENT_DATE + INTERVAL '90 days');

-- Personality scores must stay within the 0-100 range
ALTER TABLE projects
    ADD CONSTRAINT chk_personality_extraversion_range      CHECK (personality_extraversion      BETWEEN 0 AND 100),
    ADD CONSTRAINT chk_personality_agreeableness_range     CHECK (personality_agreeableness     BETWEEN 0 AND 100),
    ADD CONSTRAINT chk_personality_conscientiousness_range CHECK (personality_conscientiousness BETWEEN 0 AND 100),
    ADD CONSTRAINT chk_personality_neuroticism_range       CHECK (personality_neuroticism       BETWEEN 0 AND 100),
    ADD CONSTRAINT chk_personality_openness_range          CHECK (personality_openness          BETWEEN 0 AND 100);

-- Availability window must be coherent
ALTER TABLE projects
    ADD CONSTRAINT chk_availability_date_range CHECK (required_until >= required_from);
