# Users schema

# --- !Ups
CREATE TYPE employee_type as ENUM ('employee', 'supervisor', 'hr');
CREATE CAST (CHARACTER VARYING as employee_type) WITH INOUT AS IMPLICIT;
ALTER TABLE users ADD COLUMN type employee_type;

# --- !Downs
ALTER TABLE users DROP COLUMN type;
DROP CAST (CHARACTER VARYING as employee_type);
DROP TYPE employee_type;