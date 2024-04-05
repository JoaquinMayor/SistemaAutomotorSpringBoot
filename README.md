# Sistema Automotor Spring Boot

>El siguiente código trabajado en Spring Boot es un sistemá de ApiRest, el mismo hace consultas a la base de datos de MySQL enlazada con sus tablas correspondientes, destinada a la gestión de usuarios para luego poder  hacer gestión  de sus vehiculos y sacar turnos para hacer determinados trámites.

### Tablas MySQL
Primary Key = PK
Foreign Key = FK
> **Users:**
>- id = varchar(100) (PK)
>- name = varchar(50)
>- lastname = varchar(50)
>- dni = varchar(50)
>- age = int
>- enabled = tinyint(1)
>- email = varchar(50)
>- password = varchar(50)

> **Vehicles:**
>- id = varchar(100) (PK)
>- type = varchar(50)
>- patent = varchar(50)
>- kilometres = int
>- model = varchar(50)
>- enabled = tinyint(1)
>- car_owner_id = varchar(50) (FK)

> **Shifts:**
>- id = varchar(100) (PK)
>- sku = varchar(50)
>- amount = float
>- date = date
>- enabled = tinyint
>- patent = varchar(50)
>- vehicle_id = varchar(50) (FK)

> **Roles**
>- id = int(PK)
>- name = varchar(50)

>**Rolesxusers**
>- id = varchar(100) (PK)
>- id_rol = int (FK)
>- id_user = varchar(50) (FK)

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
