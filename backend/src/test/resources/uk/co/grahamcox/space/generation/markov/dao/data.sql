INSERT INTO markovchains(
  markov_chain_id,
  version,
  created,
  updated,
  name,
  type,
  prefix,
  corpus
) VALUES (
  '00000000-0000-0000-0000-000000000001'::uuid,
  '11111111-1111-1111-1111-111111111111'::uuid,
  '2018-03-28T08:29:00Z',
  '2018-03-28T09:29:00Z',
  'Example Markov Chain',
  'species',
  2,
  '["ab", "bc", "cd"]'::jsonb
);

INSERT INTO markovchains(
  markov_chain_id,
  version,
  created,
  updated,
  name,
  type,
  prefix,
  corpus
) VALUES (
  '00000000-0000-0000-0000-000000000002'::uuid,
  '11111111-1111-1111-1111-111111111111'::uuid,
  '2018-03-28T08:29:00Z',
  '2018-03-28T09:29:00Z',
  'Second Markov Chain',
  'planets',
  2,
  '["ab", "bc", "cd"]'::jsonb
);
