CREATE TABLE Sectors(
  sector_id UUID PRIMARY KEY,
  version UUID NOT NULL,
  created TIMESTAMP WITH TIME ZONE NOT NULL,
  updated TIMESTAMP WITH TIME ZONE NOT NULL,
  galaxy_id UUID NOT NULL,
  x INT NOT NULL,
  y INT NOT NULL,
  stars INT NOT NULL,

  CONSTRAINT fk_sector_galaxy FOREIGN KEY (galaxy_id) REFERENCES Galaxies (galaxy_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT ck_sector_x CHECK (x > 0),
  CONSTRAINT ck_sector_y CHECK (y > 0),
  CONSTRAINT ck_sector_stars CHECK (stars > 0)
);

