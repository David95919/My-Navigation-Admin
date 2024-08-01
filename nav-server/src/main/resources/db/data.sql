INSERT INTO t_user(id, username, password, create_time, update_time)
SELECT 1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1
                  FROM t_user
                  WHERE id = 1
                     OR username = 'admin');

# admin 123456