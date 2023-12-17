<a name="top"></a>
# Auth App BCI

## Índice de contenidos
* [Instrucciones](#item1)
* [Pre-Requisitos](#item2)

<a name="item2"></a>
### Pre-Requisitos

* Tener docker instalado
* Tener GIT instalado
* Tener puerto 8090 disponible o el que tenga disponible solo debe modificar este comando
> docker run -p ${PUERTO_DESEADO_AQUI}:8090 bci


<a name="item1"></a>
### Instrucciones

* Descargar repositorio, abriendo un terminal y pegando lo siguiente:
> git clone https://github.com/celeryx/test_bci.git
* Entrar a la carpeta "test_bci"
* Abrir un terminal
* Ejecutar comando:
>  docker build -t bci .
* Cuando el comando termine de ejecutarse (puede tardarse bastante la primera vez que se ejecuta)
* Cuando el comando haya finalizado ejecutar el siguiente comando:
> docker run -p 8090:8090 bci
* Consumir desde Postman los siguientes endpoints:
> http://localhost:8090/sign-up  
> http://localhost:8090/login
* CURL para endpoint sign-up
> curl --location --request POST 'http://localhost:8080/sign-up' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Julio Gonzalez",
"email": "julio@t1es2tssw.cl",
"password": "a2asfGfdfdf4",
"phones": [
{
"number": 87650009,
"cityCode": 7,
"countryCode": "25"
}
]
}'
* Body request para endpoint de sign-up
> {
"name": "Julio Gonzalez",
"email": "julio@t1es2tssw.cl",
"password": "a2asfGfdfdf4",
"phones": [
{
"number": 87650009,
"cityCode": 7,
"countryCode": "25"
}
]
}
* CURL para endpoint de login
> curl --location --request POST 'http://localhost:8080/login' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWxpbzFAdDFlczJ0c3N3LmNsIiwiaWF0IjoxNzAyODM1NTA1LCJleHAiOjE3MDI4MzU4MDV9.AIDZ1GrPrnXP9YkAMuUDVgoUnd1G2j3q5QSHsa_KJF1tlpYNtYU9Y1pTS6Mom-7gAPwLBhI7cCbkbDzK9NZlcQ' \
--header 'Content-Type: application/json' \
--data-raw '{
"email": "julio@t1es2tssw.cl",
"password": "a2asfGfdfdf4"
}'
* Body Request para endpoint de login
> {
"email": "julio@t1es2tssw.cl",
"password": "a2asfGfdfdf4"
}
* En caso de fallar revise si cumple con los pre-requisitos

[Subir](#top)
