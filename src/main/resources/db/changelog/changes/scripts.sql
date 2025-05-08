--liquibase formatted sql

--changeSet idfcsgfy:1
ALTER TABLE authorities ADD CONSTRAINT fk_authority_security FOREIGN KEY (username) REFERENCES security(username) ON DELETE CASCADE;
ALTER TABLE users ADD CONSTRAINT fk_user_security FOREIGN KEY (username) REFERENCES security(username) ON DELETE CASCADE;
--changeSer idfcsgfy:2
ALTER TABLE comments ADD CONSTRAINT fk_comment_user FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE comments ADD CONSTRAINT fk_comment_ad FOREIGN KEY (ad_id) REFERENCES ads(id) ON DELETE CASCADE;