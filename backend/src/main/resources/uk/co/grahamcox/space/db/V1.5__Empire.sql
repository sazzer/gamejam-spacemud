CREATE TABLE SpeciesSectors(
  species_sector_id UUID PRIMARY KEY,
  version UUID NOT NULL,
  created TIMESTAMP WITH TIME ZONE NOT NULL,
  updated TIMESTAMP WITH TIME ZONE NOT NULL,
  species_id UUID NOT NULL,
  sector_id UUID NOT NULL,
  stars INT NOT NULL,

  CONSTRAINT fk_species_sector FOREIGN KEY (species_id) REFERENCES Species (species_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_sector_species FOREIGN KEY (sector_id) REFERENCES Sectors (sector_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT ck_sector_stars CHECK (stars >= 0),
  CONSTRAINT uk_sector_species UNIQUE (species_id, sector_id)
);

