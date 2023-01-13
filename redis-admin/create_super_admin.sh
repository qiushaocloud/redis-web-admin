script="
from users.models import DctUser

username = '$RM_AUTH_USERNAME';
password = '$RM_AUTH_PASSWORD';
email = '$RM_AHTH_EMAIL';

if DctUser.objects.filter(username=username).count()==0:
    DctUser.objects.create_superuser(username, email, password);
    print('Superuser created.');
else:
    print('Superuser creation skipped.');
"
printf "$script" | python manage.py shell
