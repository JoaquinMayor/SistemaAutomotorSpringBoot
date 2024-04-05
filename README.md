# Sistema Automotor Spring Boot

>El siguiente código trabajado en Spring Boot es un sistemá de ApiRest, el mismo hace consultas a la base de datos de MySQL enlazada con sus tablas correspondientes, destinada a la gestión de usuarios para luego poder  hacer gestión  de sus vehiculos y sacar turnos para hacer determinados trámites.

*En caso de querer probar la aplicación recordar configurar adecuadamente la base de datos para así funcione correctamente y conmectarla y asociarla en el archivo `aplication.properties`*

### Tablas MySQL
Primary Key = PK
Foreign Key = FK
> **Users:**
- id = varchar(100) (PK)
- name = varchar(50)
- lastname = varchar(50)
- dni = varchar(50)
- age = int
- enabled = tinyint(1)
- email = varchar(50)
- password = varchar(50)

> **Vehicles:**
- id = varchar(100) (PK)
- type = varchar(50)
- patent = varchar(50)
- kilometres = int
- model = varchar(50)
- enabled = tinyint(1)
- car_owner_id = varchar(50) (FK)

> **Shifts:**
- id = varchar(100) (PK)
- sku = varchar(50)
- amount = float
- date = date
- enabled = tinyint
- patent = varchar(50)
- vehicle_id = varchar(50) (FK)

> **Roles**
- id = int(PK)
- name = varchar(50)

>**Rolesxusers**
- id = varchar(100) (PK)
- id_rol = int (FK)
- id_user = varchar(50) (FK)

### Entities

###### User, Role, Vehicle, Shift.

### Repositories

> #### IUserRepository
- allUsers() : Trae a todos los usuarios.
- findByEmail(String email) : Busca a un usuario por su email.
- findByDni(String dni) : Busca a un usuario por su DNI.
- findByLabstName(String lastname) : Busca a todos los usuarios con cierto apellido.
- existByDni(String dni): Determina si existe un usuario por su DNI.

> #### IVehicleRepository
- findAll() : Trae a todos los vehiculos registrados.
- findByPatent(String patent): Trae a un vehiculo buscado por su patente.
- existByPatent(String patent): Determina si existe un vehiculo registrado con esa patente.

> #### IShiftRepository
- findAll() : Trae todos los turnos creados.
- findByDate(Date date) :  Trae todos los turnos pertenecientes a una fecha determinada.
- findByPatent(String patent): Trae un turno asociado a al vehiculo solicitado.
- existBySku(String sku): Determina si un turno existe por su sku.

> #### IRoleRepository
- findByName(String name): Trae un tipo de rol según el nombre del mismo.

### Services

> #### JpaUserDetailsService
*Servicio destinado a la gestion del login de usuario y control de la contrasela y el email.*

> #### UserService
*Servicio destinado para manejar todos los datos de los usuarios dentro de los controladores.*

> #### VehicleService
*Servicio destinado para manejar todos los datos de los vehiculos dentro de los controladores.*

> #### ShiftService
*Servicio destinado para manejar todos los datos de los turnos dentro de los controladores.*

### Controladores

> #### UserController

> #### VehicleController

> #### ShiftController

Dentro de los respecticos controladores se encuentran comentadas las funcionalidades de los distintos métodos pertenecientes a los mismos.

### Security / filter folders

En estas carpetas encontraremos todos los archivos relacionados con la creación, pruebas y gestión de token de seguridad, para sí controlar el acceso y permisos de los usuarios a los diferentes request creados en los controladores.
En el archivo `SpringSecurityConfig.java` podremos ver que tipo de usuario tiene acceso a cada uno de los request.

### Validations folder

En esta carpeta podremos encontrar los disitntos validadores personalizados de la aplicación para cada uno de los `@Entity` de la aplicación.
Todos ellos están asociados a validar si ya se encuentra en la base de datos.

<p align="center">
  <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSRhFjHfTvMOaBAUyqdr8MPVy7Pu5fHXm3TOlKtdHdkiQ&s" alt="Descripción de la imagen" width="300">
</p>
