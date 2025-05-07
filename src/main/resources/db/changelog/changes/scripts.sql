--liquibase formatted sql

--changeSet idfcsgfy:1
ALTER TABLE authorities ADD CONSTRAINT fk_authority_security FOREIGN KEY (username) REFERENCES security(username) ON DELETE CASCADE;
ALTER TABLE users ADD CONSTRAINT fk_user_security FOREIGN KEY (username) REFERENCES security(username) ON DELETE CASCADE;