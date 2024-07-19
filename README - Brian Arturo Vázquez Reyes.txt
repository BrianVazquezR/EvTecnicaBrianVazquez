Prueba Técnica de Java

Sección 2: Nivel Medio
Objetivos
	1. Crear una API REST y un servicio SOAP utilizando Spring Boot.
	2. Implementar autenticación básica utilizando Spring Security.
	3. Interactuar con bases de datos PostgreSQL y DB2.
	4. Escribir y ejecutar procedimientos almacenados.



Es una aplicación basica con tema de Almacen llamado Kuroyami-PetStore.
Aprovecho este momento para disculparme por la sencilles de la aplicación pese al tiempo que me otorgaron para la prueba,
		no tuve la oportunidad de dedicarme como me hubiese gustado, pudiendo solo algúnas horas al día 
		debido a actividades finales de un proyecto, documentación de día y liberación a producción de noche.
		De tener más tiempo había hecho algo más grande,agregando carrito de compras, roles, gerencia, logins, horarios y muchas cosas más.
		De nuevo les ofresco una disculpa y Agradezco su comprención.
 


La aplicación cumple con todas las medidas solicitadas en las pruebas técnicas a excepción de las pruebas con DB2, 
para ser exactos SI incluye 
	-Los correspondientes properties de cada DB 
	-la comvivencia entre ambas bases de datos PostgreSQL y DB2 
	-la conectividad correspondiente de cada una de las bases de datos PostgreSQL y DB2 sin necesidad de cambiar perfiles
	-las pruebas exitosas (CRUD) de PostgreSQL por REST directo
	-Conectividad y Consultas de servicios SOAP 
	-La Consulta y obtención de información de PostgreSQL usando Procedimientos Almacenados + REST de prueba (exitosa)
	-La programación de la Consulta de DB2 usando Procedimientos Almacenados + REST de prueba
	-Seguridad de Roles Spring Security (accesos limitados por Rol)
	
Lo único que no incluye es
	-La garantía de que funcione  la consulta del Procedimiento Almacenado de DB2 debido a que no logré realizar pruebas de esto
		-Razón (Mi maquina no contaba con la configuración adecuada para DB2, por lo que al crear las bases de datos se requiere de 
					los permisos de usuario y contraseña del sistema operativo, se creó la DB, sus tablas y el SP pero no se pudo 
					realizar pruebas con los datos.)
					

=====Implementación de Spring Security:=====
	Usuarios	Contraseñas		Accesos
	admin -----> admin	  ----> Crontol sobre los Servicios SOAP y Todos los Servicios REST (incluyendo pruebas de Store Procedure)
	user  -----> password ----> Crontol sobre los Servicios SOAP
	
	Sin esos usuarios no se puede hacer uso de ningún servicio.
	-Desafortunadaente por falta de tiempo no puede agregar seguridad más alta como la configuración de Tokens 
	

=====Uso de Servicios REST y SOAP=====
Adjunto al código en la carpeta de resources colocaré las colecciones para que puedan hacer uso de ellas 
en las Herramientas de SoapUI y Postman (para los rest)
	-De todas formas coloco aquí las definiciones de cada uno por si quieren copiar y pegar

=====Servicios SOAP=====


URL:  http://localhost:8080/ws/departamentos.wsdl
Endpoint  http://localhost:8080/ws
Añadir Authenticación Básica (Usuarios)

request1: getAllDepartamentos  (Busca todos los Departametos)
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:dep="http://kuroyami.com/departamentos">
   <soapenv:Header/>
   <soapenv:Body>
      <dep:getAllDepartamentosRequest/>
   </soapenv:Body>
</soapenv:Envelope>

request2: getDepartamento  (Busca un Departameto por Id)
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:dep="http://kuroyami.com/departamentos">
   <soapenv:Header/>
   <soapenv:Body>
      <dep:getDepartamentoRequest>
         <dep:id>2</dep:id>
      </dep:getDepartamentoRequest>
   </soapenv:Body>
</soapenv:Envelope>



=====Servicios REST=====

Añadir Authenticación Básica (Usuario Admin)

Get_Obtener_Empleados:	http://localhost:8080/empleados
[
    {
        "id": 1,
        "nombre": "Jhon",
        "puesto": "Almacenador",
        "nombreDepartamento": "Almacenn",
        "departamentoId": 2
    },
    {
        "id": 4,
        "nombre": "Yuki",
        "puesto": "Gerente",
        "nombreDepartamento": "Ventass",
        "departamentoId": 1
    },
    {
        "id": 12,
        "nombre": "Hikari",
        "puesto": "Mascotas",
        "nombreDepartamento": "Almacenn",
        "departamentoId": 2
    },
    {
        "id": 13,
        "nombre": "Hikari",
        "puesto": "Mascotas",
        "nombreDepartamento": "Almacenn",
        "departamentoId": 2
    },
    {
        "id": 14,
        "nombre": "Hikaari",
        "puesto": "Mascotas",
        "nombreDepartamento": "Almacenn",
        "departamentoId": 2
    },
    {
        "id": 2,
        "nombre": "JosS Pérez",
        "puesto": "Organizador",
        "nombreDepartamento": "Estantess",
        "departamentoId": 3
    }
]
Get_Obtener_Empleados_By_Id: http://localhost:8080/empleados/2
	Response: 200 OK
	{
		"id": 2,
		"nombre": "JosS Pérez",
		"puesto": "Organizador",
		"nombreDepartamento": "Estantess",
		"departamentoId": 3
	}

Delete_Eliminar_Empleado_By_Id: http://localhost:8080/empleados/7
	Response: 200 OK

Post_Crear_Empleados:	 http://localhost:8080/empleados
	Body - Json
	{   
		"nombre": "Hikari",
		"puesto": "Mascotas",
		"departamentoId": 2
	}
	
	Response: 200 OK
	{
		"id": 14,
		"nombre": "Hikaari",
		"puesto": "Mascotas",
		"nombreDepartamento": "Almacenn",
		"departamentoId": 2
	}
	
	
Put_Actualizar_Empleado_By_Id: 	http://localhost:8080/empleados/2
	Body - Json
	{
		"nombre": "JosS Pérez",
		"puesto": "Organizador",
		"departamentoId": 2
	}
	
	Response 500 - Nota (responde con error pero si funciona, no tuve tiempo de pulirlo)
	
	
Get_Consulta_Empleados_By_Store-Procedure: http://localhost:8080/departamentos/1/empleados
	Response: 200 OK
	[
		{
			"id": 4,
			"nombre": "Yuki",
			"puesto": "Gerente"
		}
	]
	
Put_Actualizar_Departamento_By_Id_Store-Procedure:	http://localhost:8080/departamentos/2/nombre?nombre=Tecnico
	Este es el correspondiente a DB2 que no pude probar por problemas con mi equipo.
	
	



=====Bases de Datos=====
PostgreSQL
	Nombre DB: KuroyamiPets
	Tablas: departamentos y empleados 
	Columnas:
		departamentos
			id (BIGINT)Primary_Key, 
			nombre (VARCHAR), 
			
		empleados
			id (BIGINT)Primary_Key, 
			nombre (VARCHAR), 
			puesto (VARCHAR), 
			departamento_id(BIGINT) Foreing Key linkeada a Prymay key departamentos.id
			

	Store-Procedure
		CREATE OR REPLACE FUNCTION get_empleados_by_departamento(dept_id BIGINT)
		RETURNS TABLE(id BIGINT, nombre VARCHAR, puesto VARCHAR, departamento_id BIGINT) AS $$
		BEGIN
			RETURN QUERY
			SELECT e.id, e.nombre, e.puesto, e.departamento_id
			FROM empleados e
			WHERE e.departamento_id = dept_id;
		END;
		$$ LANGUAGE plpgsql;


DB2 - Linea de Comandos
	Nombre DB: KUROYAMI
	Tablas: departamentos y empleados 
		CREATE TABLE DEPARTAMENTOS (id INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), nombre VARCHAR(255) NOT NULL)
		CREATE TABLE EMPLEADOS ( id INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), nombre VARCHAR(255) NOT NULL, puesto VARCHAR(255), departamento_id INTEGER, FOREIGN KEY (departamento_id) REFERENCES DEPARTAMENTOS(id))
		INSERT INTO EMPLEADOS(nombre, puesto, departamento_id)	VALUES ('JhonB2' ,'Almacenador' ,2 ),	('JamesB2' ,'Organizador' ,3 ),('AnaB2' ,'Cajera' ,1 ),('YukiB2' ,'Gerente' ,1 );
		INSERT INTO DEPARTAMENTOS(nombre) VALUES ('VentasB2'),('AlmacenB2'), ('EstantesB2');
	
	Store-Procedure
		db2 -td@ -v "CREATE OR REPLACE PROCEDURE update_department_name(IN dept_id BIGINT, IN new_name VARCHAR(100))BEGIN UPDATE Departamentos SET nombre = new_name WHERE id = dept_id; END@"
	
	db2 "SELECT TABSCHEMA, TABNAME FROM SYSCAT.TABLES WHERE TYPE = 'T' ORDER BY TABSCHEMA, TABNAME"

	Logueo DB2
		db2 connect to KUROYAMI user BYAKU using <password>
	Esquemas
		db2 "SET SCHEMA DB2ADMIN"
	Consultas
		db2 "SELECT * FROM departamentos"
	Obtención del puerto
	db2 get dbm cfg | findstr /C:"SVCENAME"
		 Nombre de servicio TCP/IP                    (SVCENAME) = db2c_DB2
		 Nombre de servicio SSL                   (SSL_SVCENAME) =
	Busqueda del Puerto
		findstr db2c_DB2 services
			db2c_DB2        25000/tcp
	Puerto Activo? 
		netstat -an | findstr 25000