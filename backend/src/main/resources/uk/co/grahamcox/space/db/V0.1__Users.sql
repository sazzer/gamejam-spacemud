CREATE TABLE Users(
  user_id UUID PRIMARY KEY,
  version UUID NOT NULL,
  created TIMESTAMP WITH TIME ZONE NOT NULL,
  updated TIMESTAMP WITH TIME ZONE NOT NULL,
  email VARCHAR(260) NOT NULL UNIQUE,
  display_name VARCHAR(100) NOT NULL,
  password VARCHAR(60) NOT NULL
);
