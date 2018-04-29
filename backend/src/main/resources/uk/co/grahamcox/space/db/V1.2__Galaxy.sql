CREATE TABLE Galaxies(
  galaxy_id UUID PRIMARY KEY,
  version UUID NOT NULL,
  created TIMESTAMP WITH TIME ZONE NOT NULL,
  updated TIMESTAMP WITH TIME ZONE NOT NULL,
  name VARCHAR(100) NOT NULL UNIQUE,
  width INT NOT NULL,
  height INT NOT NULL,

  CONSTRAINT ck_galaxy_width CHECK (width > 0),
  CONSTRAINT ck_galaxy_height CHECK (height > 0)
);
