-- Flyway Migration V2: Seed baseline production data for TeamMatch AI

INSERT INTO projects (id, title, description, required_experience, required_team_members, team_lead_requirement, status, created_at, updated_at)
VALUES 
('545aedf8-23cd-4143-81e7-f7450b9f2347', 'Plateforme TeamMatch AI Core Engine', 'Moteur principal d''appariement basé sur l''IA pour connecter automatiquement les développeurs et les projets selon leurs compétences et disponibilités.', 'Senior (5+ ans)', 4, true, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('3ac0d00e-6fd3-4a12-8500-1b62987b25b9', 'Algorithme d''Appariement Intelligent', 'Module IA d''analyse vectorielle et d''évaluation des compétences pour optimiser le score de compatibilité entre les candidats et les équipes.', 'Lead / Expert', 3, true, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('33ca3183-0b5a-4bfd-993c-e88ec31710c7', 'Dashboard Web Frontend React', 'Interface utilisateur réactive et ultra-moderne permettant la gestion visuelle des projets, le filtrage dynamique et la consultation des métriques d''équipe.', 'Mid-level (2-4 ans)', 2, false, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('493a86bf-9954-43b4-bee6-d96d53118951', 'Service de Notification & Collaboration Temps Réel', 'Infrastructure de communication bidirectionnelle en temps réel pour alerter les membres lors d''un nouveau match de projet ou d''une invitation.', 'Mid-level (3+ ans)', 3, false, 'DRAFT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('1c719a73-e414-4819-b3ed-2e407f1c916c', 'Application Mobile TeamMatch Companion', 'Application mobile iOS et Android permettant d''accepter les invitations de projet, de suivre l''avancement et de discuter avec le chef de projet.', 'Senior (4+ ans)', 5, true, 'COMPLETED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO project_required_skills (project_id, skill) VALUES
('545aedf8-23cd-4143-81e7-f7450b9f2347', 'Java 21'),
('545aedf8-23cd-4143-81e7-f7450b9f2347', 'Spring Boot 3'),
('545aedf8-23cd-4143-81e7-f7450b9f2347', 'PostgreSQL'),
('545aedf8-23cd-4143-81e7-f7450b9f2347', 'Docker'),
('545aedf8-23cd-4143-81e7-f7450b9f2347', 'REST API'),
('3ac0d00e-6fd3-4a12-8500-1b62987b25b9', 'Python'),
('3ac0d00e-6fd3-4a12-8500-1b62987b25b9', 'TensorFlow'),
('3ac0d00e-6fd3-4a12-8500-1b62987b25b9', 'FastAPI'),
('3ac0d00e-6fd3-4a12-8500-1b62987b25b9', 'NLP'),
('33ca3183-0b5a-4bfd-993c-e88ec31710c7', 'React 18'),
('33ca3183-0b5a-4bfd-993c-e88ec31710c7', 'TypeScript'),
('33ca3183-0b5a-4bfd-993c-e88ec31710c7', 'Vite'),
('493a86bf-9954-43b4-bee6-d96d53118951', 'Node.js'),
('493a86bf-9954-43b4-bee6-d96d53118951', 'WebSocket'),
('493a86bf-9954-43b4-bee6-d96d53118951', 'Redis'),
('1c719a73-e414-4819-b3ed-2e407f1c916c', 'Flutter'),
('1c719a73-e414-4819-b3ed-2e407f1c916c', 'Dart'),
('1c719a73-e414-4819-b3ed-2e407f1c916c', 'Firebase');
