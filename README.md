**Sistema de Gestión de Biblioteca Escolar**
Un sistema de escritorio desarrollado en Java Swing con arquitectura MVC, persistencia en MySQL mediante JDBC, implementación del patrón DAO, conexión Singleton y programación concurrente con SwingWorker y bloques synchronized.
Características PrincipalesAutenticación y Control de Accesos: Inicio de sesión seguro con diferenciación de roles (BIBLIOTECARIO y ESTUDIANTE).Gestión de
Catálogo (CRUD): Administración de libros, categorías y estudiantes con persistencia directa en MySQL.
Procesamiento Asíncrono de Préstamos: Uso de SwingWorker para realizar transacciones pesadas en segundo plano sin congelar la interfaz gráfica.
Control de Stock Sincronizado: Métodos con modificador synchronized para prevenir condiciones de carrera (race conditions) durante la actualización de inventario.
Reportes y Consultas: Visualización gráfica de préstamos activos, historial e inventario.
Arquitectura del Proyecto (MVC + DAO)El código está estructurado en capas dentro del directorio src/ para mantener una clara separación de responsabilidades:src/



├── app/
│   └── Main.java                    # Punto de entrada de la aplicación
├── config/
│   └── DatabaseConnection.java      # Patrón Singleton para la conexión JDBC
├── controller/
│   ├── PrestamoController.java      # Lógica de negocio para préstamos
│   └── PrestamoWorker.java          # Concurrencia con SwingWorker
├── dao/
│   ├── EstudianteDAO.java           # Operaciones CRUD en tabla estudiantes
│   ├── LibroDAO.java                # Operaciones CRUD y control de stock sincronizado
│   ├── PrestamoDAO.java             # Persistencia de transacciones de préstamos
│   └── UsuarioDAO.java              # Validación y consulta de credenciales
├── model/
│   ├── Categoria.java               # Modelo Categoria
│   ├── Estudiante.java              # Modelo Estudiante
│   ├── Libro.java                   # Modelo Libro
│   ├── Prestamo.java                # Modelo Prestamo
│   └── Usuario.java                 # Modelo Usuario
└── view/
    ├── LibrosPanel.java             # Panel de gestión de inventario
    ├── LoginFrame.java              # Ventana de autenticación
    ├── MainFrame.java               # Ventana principal con pestañas
    ├── PrestamosPanel.java          # Panel interactivo de préstamos
    └── ReportesPanel.java           # Panel de reportes
Abre src/config/DatabaseConnection.java y ajusta la contraseña de tu servidor MySQL local:private static final String URL = "jdbc:mysql://localhost:3306/biblioteca_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = "TU_CONTRASEÑA_MYSQL";
Vincular el Driver JDBC en IntelliJ IDEA:Ve a Project Structure (Ctrl + Alt + Shift + S) ➔ Modules ➔ pestaña Dependencies.Añade el archivo lib/mysql-connector-j-26.7.0.jar y marca la casilla con ámbito Compile o Runtime.Ejecutar la Aplicación:Abre src/app/Main.java y presiona Run (Shift + F10).
