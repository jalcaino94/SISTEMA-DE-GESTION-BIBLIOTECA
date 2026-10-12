Sistema de Gestion de Biblioteca EscolarUn sistema de escritorio desarrollado en Java Swing con arquitectura MVC, persistencia en MySQL mediante JDBC, implementacion del patron DAO, conexion Singleton y programacion concurrente con SwingWorker y bloques synchronized.Proyecto desarrollado como Evaluacion Final Transversal (EFT) para la asignatura de Programacion Orientada a Objetos II.Caracteristicas PrincipalesAutenticacion y Control de Accesos: Inicio de sesion seguro con diferenciacion de roles (BIBLIOTECARIO y ESTUDIANTE).Gestion de Catalogo (CRUD): Administracion de libros, categorias y estudiantes con persistencia directa en MySQL.Procesamiento Asincrono de Prestamos: Uso de SwingWorker para realizar transacciones pesadas en segundo plano sin congelar la interfaz grafica (EDT).Control de Stock Sincronizado: Metodos con modificador synchronized para prevenir condiciones de carrera (race conditions) durante la actualizacion de inventario.Reportes y Consultas: Visualizacion grafica de prestamos activos, historial e inventario.Arquitectura del Proyecto (MVC + DAO)El codigo esta estructurado en capas dentro del directorio src/ para mantener una clara separacion de responsabilidades:src/



├── app/
│   └── Main.java                    # Punto de entrada de la aplicacion
├── config/
│   └── DatabaseConnection.java      # Patron Singleton para la conexion JDBC
├── controller/
│   ├── PrestamoController.java      # Logica de negocio para prestamos
│   └── PrestamoWorker.java          # Concurrencia con SwingWorker
├── dao/
│   ├── EstudianteDAO.java           # Operaciones CRUD en tabla estudiantes
│   ├── LibroDAO.java                # Operaciones CRUD y control de stock sincronizado
│   ├── PrestamoDAO.java             # Persistencia de transacciones de prestamos
│   └── UsuarioDAO.java              # Validacion y consulta de credenciales
├── model/
│   ├── Categoria.java               # Modelo Categoria
│   ├── Estudiante.java              # Modelo Estudiante
│   ├── Libro.java                   # Modelo Libro
│   ├── Prestamo.java                # Modelo Prestamo
│   └── Usuario.java                 # Modelo Usuario
└── view/
    ├── LibrosPanel.java             # Panel de gestion de inventario
    ├── LoginFrame.java              # Ventana de autenticacion
    ├── MainFrame.java               # Ventana principal con pestanas
    ├── PrestamosPanel.java          # Panel interactivo de prestamos
    └── ReportesPanel.java           # Panel de reportes

Tecnologias y Patrones UtilizadosLenguaje: Java (JDK 17+)GUI Framework: Java Swing / AWTBase de Datos: MySQL 8.0+Conector JDBC: mysql-connector-j-26.7.0.jarPatrones de DisenoMVC (Modelo-Vista-Controlador): Desacoplamiento entre la interfaz grafica, logica y datos.DAO (Data Access Object): Encapsulamiento de sentencias SQL (PreparedStatement) para prevenir inyecciones SQL.Singleton: Instancia unica y reutilizable de la conexion a la base de datos en DatabaseConnection.java.Programacion ConcurrenteSwingWorker: Hilo secundario para consultas de base de datos sin bloquear el Event Dispatch Thread (EDT).synchronized: Garantia de atomicidad e integridad transaccional en el control de inventario.Base de Datos MySQL (biblioteca_db)El proyecto incluye dos scripts de base de datos ubicados en la carpeta lib/:Script_crea_tablas_biblioteca.sql: Define la base de datos biblioteca_db y las 5 tablas relacionales (usuarios, estudiantes, categorias, libros, prestamos).Script_poblado_tablas_biblioteca.sql: Inserta datos iniciales de prueba (categorias, libros, estudiantes y usuarios).Credenciales de Prueba InicialesRolCorreoContrasenaBibliotecarioadmin@biblioteca.cl1234Estudiantejuan.perez@biblioteca.cl1234Configuracion e InstalacionClonar el repositorio:git clone https://github.com/tu-usuario/Biblioteca_Escolar.git


Cargar la Base de Datos:Abre MySQL Workbench y ejecuta en orden los siguientes scripts:lib/Script_crea_tablas_biblioteca.sqllib/Script_poblado_tablas_biblioteca.sqlConfigurar Credenciales en Java:Abre src/config/DatabaseConnection.java y ajusta la contrasena de tu servidor MySQL local:private static final String URL = "jdbc:mysql://localhost:3306/biblioteca_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = "TU_CONTRASENA_MYSQL";


Vincular el Driver JDBC en IntelliJ IDEA:Ve a Project Structure (Ctrl + Alt + Shift + S) -> Modules -> pestaña Dependencies.Anade el archivo lib/mysql-connector-j-26.7.0.jar y marca la casilla con ambito Compile o Runtime.Ejecutar la Aplicacion:Abre src/app/Main.java y presiona Run (Shift + F10).acion:Abre src/app/Main.java y presiona Run (Shift + F10).
