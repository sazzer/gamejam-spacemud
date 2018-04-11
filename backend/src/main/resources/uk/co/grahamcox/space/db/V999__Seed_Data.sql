INSERT INTO users(user_id,
                  version,
                  created,
                  updated,
                  email,
                  display_name,
                  password)
VALUES (
  '00000000-0000-0000-0000-000000000000'::uuid,
  'd482c44d-7c78-49ac-8a86-b7ab3b5de45b'::uuid,
  '2018-03-28T08:29:00Z',
  '2018-03-28T09:29:00Z',
  'test@user.example.com',
  'Test User',
  '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy');
