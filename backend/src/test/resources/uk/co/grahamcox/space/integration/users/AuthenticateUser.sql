INSERT INTO users(user_id,
                  version,
                  created,
                  updated,
                  email,
                  display_name,
                  password)
VALUES (
  '00000000-0000-0000-0000-000000000001'::uuid,
  '11111111-1111-1111-1111-111111111111'::uuid,
  '2018-03-28T08:29:00Z',
  '2018-03-28T09:29:00Z',
  'test@user.example.com',
  'Test User',
  '$2a$10$fn8D3nI6AZmj4rlyCtAqTODRFG/NE4uaPfMpba.7cDIy0BNVCrMsG'); -- "pa55word"
