@echo off

rem Añade y realiza el commit del repositorio donde se encuetre y realiza un push al repositorio

git add .
git commit -a -F %1
git tag %2
git push