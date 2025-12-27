#!/bin/bash

# ====================================================================
# CONFIGURACIÓN (AJUSTA ESTAS RUTAS SEGÚN TU ENTORNO)
# ====================================================================

# Directorio raíz del script. Asume que los proyectos están al mismo nivel.
RAIZ_PROYECTOS=$(pwd)

# --- ⚠️ CONFIGURACIÓN DE TOMCAT GENERALIZADA ⚠️ ---

# 1. Ajusta este nombre de directorio para que coincida con el nombre de la carpeta
#    de Tomcat dentro del HOME de CADA USUARIO, por ejemplo: apache-tomcat-11.0.4
TOMCAT_BASE_NAME="apache-tomcat-11.0.4" 

# 2. Ruta base de Tomcat (Asume que está en el HOME del usuario: /home/usuario/apache-tomcat-X.X.X)
TOMCAT_BASE_DIR="$HOME/$TOMCAT_BASE_NAME"

# 3. Ruta final a webapps
TOMCAT_WEBAPPS="$TOMCAT_BASE_DIR/webapps"

# Rutas a los scripts de control
TOMCAT_SHUTDOWN_SCRIPT="$TOMCAT_BASE_DIR/bin/shutdown.sh"
TOMCAT_STARTUP_SCRIPT="$TOMCAT_BASE_DIR/bin/startup.sh"

# Nombre del proyecto Servidor Central (el JAR)
PROYECTO_CENTRAL="ServidorCentral"

# Nombres de los proyectos Web (los WARs)
PROYECTO_WEB_NORMAL="webServer"
PROYECTO_WEB_MOBILE="webServerMobile"

# Nombre del archivo JAR final (ajustado al formato con dependencias)
JAR_FILE="ServidorCentral-Servidor-jar-with-dependencies.jar"
JAR_PATH="$PROYECTO_CENTRAL/target"
FULL_JAR_PATH="$RAIZ_PROYECTOS/$JAR_PATH/$JAR_FILE"

# ====================================================================
# FUNCIONES PRINCIPALES
# ====================================================================

# Función para compilar y empaquetar un proyecto Maven
compilar_maven() {
    local PROYECTO=$1
    echo "--- 🛠️ COMPILANDO $PROYECTO ---"
    
    # Comprobación de existencia del directorio
    if [ ! -d "$RAIZ_PROYECTOS/$PROYECTO" ]; then
        echo "Error: No se encontró la carpeta $PROYECTO en $RAIZ_PROYECTOS/$PROYECTO"
        exit 1
    fi
    
    cd "$RAIZ_PROYECTOS/$PROYECTO"
    
    # Limpiar y construir
    mvn clean install -DskipTests
    local RESULTADO=$?
    
    cd "$RAIZ_PROYECTOS"
    if [ $RESULTADO -eq 0 ]; then
        echo "--- ✅ $PROYECTO COMPILADO EXITOSAMENTE ---"
    else
        echo "--- ❌ ERROR DE COMPILACIÓN EN $PROYECTO ---"
        sleep 3
    fi
    return $RESULTADO
}

# Función para desplegar y levantar Tomcat
desplegar_y_levantar_web() {
    echo "--- 🌐 DESPLIEGUE Y ARRANQUE DE TOMCAT ---"

    # --- 🔒 VALIDACIÓN DE RUTA TOMCAT 🔒 ---
    if [ ! -d "$TOMCAT_WEBAPPS" ]; then
        echo "--- ❌ ERROR: DIRECTORIO TOMCAT NO ENCONTRADO ---"
        echo "Ruta esperada: $TOMCAT_WEBAPPS"
        echo "Asegúrate de que la carpeta '$TOMCAT_BASE_NAME' existe en tu $HOME."
        sleep 5
        return 1
    fi

    # Detener Tomcat (si está corriendo)
    echo "Deteniendo Tomcat con $TOMCAT_SHUTDOWN_SCRIPT..."
    if [ -f "$TOMCAT_SHUTDOWN_SCRIPT" ]; then
        "$TOMCAT_SHUTDOWN_SCRIPT" > /dev/null 2>&1
    else
        echo "⚠️ Advertencia: Script shutdown.sh no encontrado en $TOMCAT_BASE_DIR/bin. Saltando detención."
    fi
    sleep 3
    
    # --- 🔄 RUTAS DE WAR Y RENOMBRADO 🔄 ---
    local WAR_NORMAL_SRC_WITH_SUFFIX="$RAIZ_PROYECTOS/$PROYECTO_WEB_NORMAL/target/$PROYECTO_WEB_NORMAL-1.war"
    local WAR_MOBILE_SRC_WITH_SUFFIX="$RAIZ_PROYECTOS/$PROYECTO_WEB_MOBILE/target/$PROYECTO_WEB_MOBILE-1.war"
    
    local WAR_NORMAL_RENAMED="$RAIZ_PROYECTOS/$PROYECTO_WEB_NORMAL/target/$PROYECTO_WEB_NORMAL.war"
    local WAR_MOBILE_RENAMED="$RAIZ_PROYECTOS/$PROYECTO_WEB_MOBILE/target/$PROYECTO_WEB_MOBILE.war"

    if [ ! -f "$WAR_NORMAL_SRC_WITH_SUFFIX" ] || [ ! -f "$WAR_MOBILE_SRC_WITH_SUFFIX" ]; then
        echo "--- ❌ ERROR: ARCHIVOS WAR (con sufijo -1) NO ENCONTRADOS ---"
        echo "Compila los proyectos web primero (Opción 2)."
        sleep 3
        return 1
    fi
    
    # Renombrar los WARs
    echo "Renombrando WARs para eliminar el sufijo '-1'..."
    mv "$WAR_NORMAL_SRC_WITH_SUFFIX" "$WAR_NORMAL_RENAMED"
    mv "$WAR_MOBILE_SRC_WITH_SUFFIX" "$WAR_MOBILE_RENAMED"

    echo "Limpiando webapps..."
    # Limpiar despliegues anteriores
    rm -rf "$TOMCAT_WEBAPPS/$PROYECTO_WEB_NORMAL"
    rm -f "$TOMCAT_WEBAPPS/$PROYECTO_WEB_NORMAL.war"
    rm -rf "$TOMCAT_WEBAPPS/$PROYECTO_WEB_MOBILE"
    rm -f "$TOMCAT_WEBAPPS/$PROYECTO_WEB_MOBILE.war"

    # Copiar los nuevos WARs (ya renombrados)
    echo "Copiando WARs renombrados a $TOMCAT_WEBAPPS..."
    cp "$WAR_NORMAL_RENAMED" "$TOMCAT_WEBAPPS/"
    cp "$WAR_MOBILE_RENAMED" "$TOMCAT_WEBAPPS/"
    
    # Iniciar Tomcat
    echo "Iniciando Tomcat con $TOMCAT_STARTUP_SCRIPT..."
    if [ -f "$TOMCAT_STARTUP_SCRIPT" ]; then
        "$TOMCAT_STARTUP_SCRIPT"
    else
        echo "--- ❌ ERROR: Script startup.sh no encontrado en $TOMCAT_BASE_DIR/bin. Tomcat no se inició. ---"
        sleep 5
        return 1
    fi
    
    echo "Tomcat iniciado. Espera unos segundos para que se desplieguen los WARs."
    read -r -p "Presiona Enter para continuar..."
}

# Función para levantar el Servidor Central JAR
levantar_servidor_central() {
    if [ ! -f "$FULL_JAR_PATH" ]; then
        echo "--- ⚠️ JAR no encontrado. Compila primero. ---"
        echo "Ruta esperada: $FULL_JAR_PATH"
    else
        echo "--- 🚀 LEVANTANDO SERVIDOR CENTRAL ---"
        
        # Navegar al directorio target
        echo "Cambiando directorio a $RAIZ_PROYECTOS/$JAR_PATH..."
        cd "$RAIZ_PROYECTOS/$JAR_PATH" || { echo "Error: No se encontró la carpeta $JAR_PATH."; return 1; }
        
        # Ejecutar el JAR desde dentro de target
        echo "Ejecutando: java -jar $JAR_FILE"
        java -jar "$JAR_FILE"
        
        # Volver al directorio raíz del script
        cd "$RAIZ_PROYECTOS"
    fi
    read -r -p "Presiona Enter para continuar..."
}

# Función para crear el JAR del Servidor Central
crear_jar_central() {
    compilar_maven "$PROYECTO_CENTRAL"
    if [ $? -eq 0 ]; then
        read -r -p "¿Deseas levantar el Servidor Central ahora? (s/n): " RESPUESTA
        if [[ "$RESPUESTA" =~ ^[Ss]$ ]]; then
            levantar_servidor_central
        fi
    fi
}

# Función para crear los WARs y desplegar
crear_wars_web() {
    compilar_maven "$PROYECTO_WEB_NORMAL" && \
    compilar_maven "$PROYECTO_WEB_MOBILE"
    
    if [ $? -eq 0 ]; then
        read -r -p "¿Deseas desplegar y levantar Tomcat ahora? (s/n): " RESPUESTA
        if [[ "$RESPUESTA" =~ ^[Ss]$ ]]; then
            desplegar_y_levantar_web
        fi
    fi
}

# Función del menú principal
mostrar_menu() {
    clear
    echo "========================================================"
    echo "       🚀 GESTOR DE PROYECTOS MAVEN 🌐"
    echo "========================================================"
    echo "RUTA BASE TOMCAT: $TOMCAT_BASE_DIR" # Muestra la ruta actual
    echo "========================================================"
    echo "1. Crear JAR de Servidor Central y Levantar (Opcional)"
    echo "2. Crear WARs de Proyectos Web y Desplegar en Tomcat"
    echo "3. Levantar solo Servidor Central (si el JAR ya existe)"
    echo "4. Desplegar y Levantar solo Tomcat (si los WARs ya existen)"
    echo "5. Salir"
    echo "========================================================"
    read -r -p "Selecciona una opción: " OPCION
}

# ====================================================================
# EJECUCIÓN DEL SCRIPT
# ====================================================================

# Bucle principal del menú
while true; do
    mostrar_menu
    case $OPCION in
        1) crear_jar_central ;;
        2) crear_wars_web ;;
        3) levantar_servidor_central ;;
        4) desplegar_y_levantar_web ;;
        5) echo "Saliendo... ¡Adiós!"; exit 0 ;;
        *) echo "Opción no válida. Intenta de nuevo."; sleep 2 ;;
    esac
done