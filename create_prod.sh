#!/bin/bash
rm -r /mnt/Other/Programing/Projects/dentist_app/prod/Spring_API
rm -r /mnt/Other/Programing/Projects/dentist_app/prod/Angular_UI
rsync -av --progress /mnt/Other/Programing/Projects/dentist_app/Angular_UI /mnt/Other/Programing/Projects/dentist_app/prod/ --exclude .git --exclude node_modules
rsync -av --progress /mnt/Other/Programing/Projects/dentist_app/Spring_API /mnt/Other/Programing/Projects/dentist_app/prod/ --exclude .git


# rm -r /prod/Spring_API/.git
# rm -r /prod/Angular_UI/.git
