# Users schema

# --- !Ups
CREATE TYPE status as ENUM ('approved', 'denied', 'pending');
CREATE CAST (CHARACTER VARYING as status) WITH INOUT AS IMPLICIT;

CREATE TABLE request (
  id SERIAL PRIMARY KEY,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  location TEXT NOT NULL,
  notes TEXT,
  supervisor_approved status NOT NULL DEFAULT 'pending'
);

insert into request (start_date, end_date, location, notes, supervisor_approved) values (to_date('2017-01-01', 'YYYY-MM-DD'), to_date('2017-01-02', 'YYYY-MM-DD'), 'Palm Beach', 'None', 'approved');
insert into request (start_date, end_date, location, notes, supervisor_approved) values (to_date('2017-05-23', 'YYYY-MM-DD'), to_date('2017-05-24', 'YYYY-MM-DD'), 'San Fran', 'None', 'denied');
insert into request (start_date, end_date, location, notes, supervisor_approved) values (to_date('2017-11-02', 'YYYY-MM-DD'), to_date('2017-11-05', 'YYYY-MM-DD'), 'Denver', 'None', 'pending');

# --- !Downs

DROP TYPE status CASCADE;
CREATE CAST (CHARACTER VARYING as status);
DROP TABLE Request;