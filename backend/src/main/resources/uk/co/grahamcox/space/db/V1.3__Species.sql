CREATE TABLE Species(
  species_id UUID PRIMARY KEY,
  version UUID NOT NULL,
  created TIMESTAMP WITH TIME ZONE NOT NULL,
  updated TIMESTAMP WITH TIME ZONE NOT NULL,
  galaxy_id UUID NOT NULL,
  name VARCHAR(100) NOT NULL,
  traits JSONB NOT NULL,

  CONSTRAINT fk_species_galaxy FOREIGN KEY (galaxy_id) REFERENCES Galaxies (galaxy_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT uk_species_galaxy UNIQUE (galaxy_id, name)
);

