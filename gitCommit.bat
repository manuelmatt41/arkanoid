@echo off

#Añade y realiza el commit del repositorio seleccionada

git add .
git commit -a -m "%1"
git tag %2
git push