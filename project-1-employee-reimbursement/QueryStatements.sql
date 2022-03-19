select u.user_id, u.username, ur.user_role
from users u
inner join user_roles ur 
on ur.user_role_id = u.user_role_id 