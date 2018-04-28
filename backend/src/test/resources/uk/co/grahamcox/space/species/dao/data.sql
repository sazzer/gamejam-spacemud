INSERT INTO galaxies(
  galaxy_id,
  version,
  created,
  updated,
  name,
  width,
  height
) VALUES (
  '00000000-0000-0000-0000-000000000001'::uuid,
  '11111111-1111-1111-1111-111111111111'::uuid,
  '2018-03-28T08:29:00Z',
  '2018-03-28T09:29:00Z',
  'Example Galaxy',
  100,
  200
);

INSERT INTO species(
    species_id,
    version,
    created,
    updated,
    galaxy_id,
    name,
    traits
) VALUES (
  '00000000-0000-0000-0000-000000000001'::uuid,
  '11111111-1111-1111-1111-111111111111'::uuid,
  '2018-03-28T08:29:00Z',
  '2018-03-28T09:29:00Z',
  '00000000-0000-0000-0000-000000000001'::uuid,
  'Example Species',
  '{"AGGRESSIVE": 2, "CREATIVE": -1}'::jsonb
);

INSERT INTO species(
    species_id,
    version,
    created,
    updated,
    galaxy_id,
    name,
    traits
) VALUES (
  '00000000-0000-0000-0000-000000000002'::uuid,
  '11111111-1111-1111-1111-111111111111'::uuid,
  '2018-03-28T08:29:00Z',
  '2018-03-28T09:29:00Z',
  '00000000-0000-0000-0000-000000000001'::uuid,
  'Second Species',
  '{}'::jsonb
);
