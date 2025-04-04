# Wallet Personal con Autenticación JWT

## Descripción

**Wallet Personal** es un proyecto de microservicios que permite gestionar una billetera personal con múltiples funcionalidades, como el manejo de usuarios, cuentas bancarias y transacciones financieras. El sistema está compuesto por varios microservicios, cada uno enfocado en una tarea específica, incluyendo la autenticación de usuarios, la gestión de cuentas y la realización de transacciones. La autenticación se maneja utilizando JSON Web Tokens (JWT) para garantizar seguridad en la comunicación entre los servicios.

## Funcionalidades

- **Gestión de usuarios**: Registro, autenticación y gestión de perfiles de usuario mediante JWT.
- **Gestión de cuentas**: Creación de cuentas bancarias asociadas a usuarios, con posibilidad de ver el saldo y realizar operaciones.
- **Gestión de tarjetas**: Administración de tarjetas bancarias asociadas a cuentas.
- **Transacciones**: Realización de transacciones entre cuentas, incluyendo ingresos y egresos.
- 

## Arquitectura

El sistema está compuesto por los siguientes microservicios:

1. **API-Usuario**: Gestiona el registro y autenticación de usuarios usando JWT.
2. **API-Cuenta**: Gestiona la creación y visualización de cuentas bancarias.
3. **API-Card**: Permite la gestión de tarjetas bancarias.
4. **API-Transacción**: Permite realizar transacciones entre cuentas.
5. **Base de datos MySQL**: Para almacenamiento de usuarios, cuentas y transacciones.
6.**Uso del JWT**: Para interactuar con otros microservicios como **API-Cuenta** o **API-Transacción**, el usuario debe incluir el JWT en el encabezado de las solicitudes HTTP.


## Requisitos

- Docker (para ejecutar los contenedores de los microservicios y las bases de datos).
- Java 11 o superior (para compilar y ejecutar los microservicios de Spring Boot).
- Maven (para construir los proyectos de microservicios).
- Keycloak (para la autenticación basada en JWT).

## Configuración del Proyecto

### Paso 1: Clonar el repositorio

git clone https://github.com/tu-usuario/wallet-personal.git
cd wallet-personal

### Configurar Docker y Base de Datos
El proyecto utiliza Docker Compose para levantar los contenedores de los microservicios y la base de datos.

Configura las variables de entorno en el archivo docker-compose.yml según tu entorno.

Inicia los contenedores:

bash
Copiar
Editar
docker-compose up --build
Esto levantará los contenedores de MySQL, los microservicios de API-Usuario, API-Cuenta y API-Transacción, así como Keycloak para la gestión de autenticación.

### Iniciar los microservicios
mvn clean install
mvn spring-boot:run

### Acceder a los Microservicios
API-Usuario: http://localhost:8087

API-Cuenta: http://localhost:8089

API-Card	http://localhost:8090

API-Transacción: http://localhost:8091

### JWT Autenticación
Flujo de Autenticación
Registro de Usuario: Enviar una solicitud POST a /login con los datos del usuario (nombre, apellido, email, y contraseña). Si el registro es exitoso, se devuelve un JWT.

Inicio de Sesión: Enviar una solicitud POST a /login con las credenciales del usuario (email y contraseña). Si las credenciales son correctas, el servidor responde con un JWT.

Cierre de Sesión: Enviar una solicitud POST a /logout con token y cliente id.

Uso del JWT: El JWT se debe enviar en el encabezado Authorization de todas las solicitudes posteriores que requieran autenticación. El formato debe ser:

Authorization: Bearer <jwt_token>

### Endpoints de Autenticación
P🔐 Autenticación con Keycloak via API Gateway

Flujo:

Keycloak valida usuario/contraseña y emite JWT.

El cliente incluye el JWT en el header de cada solicitud.

API Gateway valida el token y enruta la petición.

Obtener Token con cURL:

### Estructura del JWT

El JWT contiene la siguiente información:

sub (subject): ID del usuario.

roles: Roles del usuario (por ejemplo, "ROLE_USER").

exp (expiration): Tiempo de expiración del token.

curl -X POST http://localhost:9100/realms/mi-realm/protocol/openid-connect/token \
-H "Content-Type: application/x-www-form-urlencoded" \
-d "username=usuario" \
-d "password=clave123" \
-d "grant_type=password" \
-d "client_id=wallet-client"

{
"access_token": "<jwt_token>",
...
}

### Microservicios

## API-Usuario

GET /usuarios/{id}: Obtiene los detalles de un usuario (requiere JWT).

## API-Cuenta
POST /api/cuenta/cuentas

GET /api/cuenta/cuentas/{id}

GET /api/cuenta/cuentas/usuario/{usuarioId}

## API-Transacción
POST /api-transaccion/transacciones

GET /api-transaccion/transacciones/{id}

## API-Card

POST /cards

GET /cards

GET /cards/{id}

PUT /cards/{id}

DELETE /cards/{id}

### Configuración

## API-Usuario
El microservicio API-Usuario está encargado de la autenticación mediante JWT. La configuración de JWT en Spring Boot es realizada utilizando filtros de seguridad personalizados. En application.properties, se configura la clave secreta para firmar los JWT:

jwt.secret=mi_clave_secreta
jwt.expiration=3600000  # 1 hora
jwt.token-prefix=Bearer 
jwt.header=Authorization

## API-Cuenta y API-Transacción
Ambos microservicios validan el JWT en cada solicitud para asegurar que el usuario esté autenticado. Se usa un filtro de seguridad que intercepta las solicitudes entrantes y verifica la validez del token JWT en el encabezado Authorization.

spring.datasource.url=jdbc:mysql://localhost:3307/api_db
spring.datasource.username=root
spring.datasource.password=root

# Configuración de seguridad JWT
jwt.secret=mi_clave_secreta
jwt.token-prefix=Bearer
jwt.header=Authorization

## Ejemplos de Peticiones con cURL

# Crear cuenta bancaria
curl -X POST http://localhost:8090/api/cuenta/cuentas \
-H "Authorization: Bearer <access_token>" \
-H "Content-Type: application/json" \
-d '{
"nombre": "Cuenta Corriente",
"saldoInicial": 500.0,
"usuarioId": 1
}'

## Crear tarjeta
curl -X POST http://localhost:8090/cards \
-H "Authorization: Bearer <access_token>" \
-H "Content-Type: application/json" \
-d '{
"numero": "9999888877776666",
"tipo": "Mastercard",
"cuenta": {
"id": 1
}
}'

## Crear transacción

curl -X POST http://localhost:8090/api-transaccion/transacciones \
-H "Authorization: Bearer <access_token>" \
-H "Content-Type: application/json" \
-d '{
"cuentaOrigenId": 1,
"cuentaDestinoId": 2,
"monto": 150.00,
"descripcion": "Pago de servicios"
}'

### Contribución
Si deseas contribuir a este proyecto, sigue estos pasos:

Haz un fork del repositorio.

Crea una nueva rama para tu cambio (git checkout -b feature/nueva-funcionalidad).

Haz commit de tus cambios (git commit -m 'Agrega nueva funcionalidad').

Push a la rama (git push origin feature/nueva-funcionalidad).

### Licencia
Este proyecto está bajo la licencia MIT. Consulta el archivo LICENSE para más detalles.


Este archivo `README.md` describe cómo configurar, ejecutar y probar un sistema de microservicios que utiliza JWT para la autenticación, pero sin depender de Keycloak. Cada microservicio realiza la validación de JWT para asegurar que las solicitudes provengan de usuarios autenticados.
