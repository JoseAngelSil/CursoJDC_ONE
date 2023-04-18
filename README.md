# CursoJDC_ONE
Curso de JDBC en Alura usando Mysql y Maven

Bienvenidos a este repositorio.
Esta usando JDK 19 con maven 3.8.7 para el curso de JDBC usando como driver principal para la conexion 
de base de datos mysql usando XAMPP como motor principal.
Ademas se usa el driver de c3p0 para mantener un pool de conexiones maxima de 20 como minimo para que 
no haya persistencia o problemas a colas de solicitudes de conexion.

Usa el modelo MVC, por lo que cada uno tiene con un objetivo diferente para el aplizativo. por lo que se agrega los paquetes 
de DAO y factory, para tener mas control del proyecto y del desarrollo.

Se anexa un archivo SQL para que se pueda replicar de la misma forma la base de datos de mysql o mariaDB.

