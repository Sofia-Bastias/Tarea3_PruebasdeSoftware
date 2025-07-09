
# Sistema de Tarjeta de Fidelidad Gamificada

Programa de línea de comandos en Java para gestión de clientes y puntos de fidelidad en una cadena de tiendas.

## Requisitos
- Java 17+
- Maven 3.8+
- JUnit 5 (incluido en dependencias Maven)

## Cómo ejecutar
```bash
#Recordar ingresar comandos desde 
.\tarea-maven-app\
# Compilar y ejecutar
mvn clean compile exec:java "-Dexec.mainClass=com.tarea3.Main"

# Ejecutar pruebas unitarias
mvn test

# Generar reporte de cobertura (JaCoCo)
mvn clean test jacoco:report
```

## Estructura de la tarea
```text
tarea-maven-app/
├── src/
│   ├── main/java/com/tarea3/
│   │   ├── Cliente.java         # Entidad Cliente
│   │   ├── ClienteService.java  # Lógica de negocio
│   │   ├── Compra.java          # Entidad Compra
│   │   ├── CompraService.java   # Reglas de puntos
│   │   ├── Nivel.java           # Enum de niveles
│   │   └── Main.java            # Interfaz de consola
│   └── test/java/com/tarea3/    # Pruebas unitarias
├── target/site/jacoco/          # Reporte de cobertura
└── pom.xml                      # Configuración Maven
```

## Cobertura de pruebas
Usé **JaCoCo** para medir la cobertura porque:
- Se integra fácilmente con Maven
- Genera reportes visuales claros
- Es el estándar en proyectos Java
- Cumple con los requisitos del curso


**Cobertura actual:**

| **Clase**         | **Instrucciones** | **Branches** |  
|-------------------|------------------|-------------|
| `Compra`          | 93%              | 65%         |  
| `Cliente`         | 88%              | 72%         |  
| `ClienteService`  | 98%              | 87%         |  
| `CompraService`   | 93%              | 83%         | 
| `Nivel`           | 100%             | -           |  


## Funcionalidades implementadas
### Gestión de Clientes
✔ CRUD completo  
✔ Validación de correo electrónico  
✔ Sistema de niveles automático  

### Registro de Compras
✔ Cálculo de puntos con multiplicadores por nivel  
✔ Bonus por 3 compras en un día  
✔ Histórico de compras  

### Menú de consola
```text
1. Gestión de Clientes
2. Registrar Compra
3. Mostrar Puntos/Nivel
4. Salir
```

## 📝 Licencia
MIT License - Ver archivo [LICENSE](LICENSE)

## 🔍 Diseño del sistema
![Diagrama UML simplificado](https://i.imgur.com/fake-uml.png)  
*(Diagrama de clases básico que muestra relaciones entre Cliente, Compra y Nivel)*

## Ejemplo de salida de tests (último resultado al probar tarea)
```text
[INFO] Results:
[INFO]
[INFO] Tests run: 44, Failures: 0, Errors: 0, Skipped: 0
```
