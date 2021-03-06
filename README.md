d# Tenpo Challenge API


* [Overview](#overview)
* [Deployment Overview](#deployment-overview)
* [Docker](#docker)

## Overview
Nombre      |  URL                        | Requiere autenticación
----------|-----------------------------|-------------
Sign up usuarios   |  GET /user/signup | No
Login usuarios | POST /user/login | No
Sumar dos números | GET /add?a={addendA}&a={addendB} | Sí
Historial de llamados a los endpoints | GET /history | Sí
Logout usuarios | GET /logout | Sí

Una vez registrado el usuario (singup) se podrá autenticar a la API a través del Login, el cual si fue exitoso devolverá un token.

Para ejecutar los endpoints segurizados se deberá agregar el header de autorización "Bearer token" con el token obtenido en el login.

Postman collection de la API en [Postman collection](src/test/resources/templates/challenge.postman_collection.json)

## Deployment:

La aplicación está desarrollada utilizando Java, Spring, Postgres y Redis
- Postgres es usado para mantener la información de los usuarios registrados y el historial de operaciones.
- Redis mantiene de forma temporal los tokens jwt que fueron blacklisteados, esta estrategia permite invalidar los tokens que aún no expiraron pero que el usuario decidió desloguearse.

## Docker

La app está desarrollada para correr sobre un contenedor Docker. 
El docker-compose.yml en la raíz del proyecto esta preparado para orquestar un docker network con la imagen 
descargada desde el docker-hub.
Para ejecutar la aplicación se debe descargar la  docker-compose image

```
docker pull gusrei/tenpo:v0
```
y luego en un directorio que contenga el docker-compose.yml ejecutar

 ```
  docker-compose up 
 ```

