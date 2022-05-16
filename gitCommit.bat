@echo off

rem Añade y realiza el commit del repositorio seleccionada

git add .
git commit -a -F %1
git tag %2
git push