# README - Proyecto React con Vite

## Descripción
Este es un proyecto básico de React creado con Vite como herramienta de construcción. Contiene la configuración inicial necesaria para empezar a desarrollar una aplicación web moderna con React.

## Requisitos previos
- Node.js (versión 14.18+, 16+ recomendada)
- npm (viene con Node.js)

## Instalación

1. Crear el proyecto:
```bash
npm create vite@latest
```
   - Seleccionar:
     - Project name: (tu-nombre-proyecto)
     - Framework: React
     - Variant: JavaScript

2. Navegar al directorio del proyecto:
```bash
cd tu-nombre-proyecto
```

3. Instalar dependencias:
```bash
npm install
```

## Estructura del proyecto
```
tu-nombre-proyecto/
├── node_modules/       # Dependencias instaladas
├── public/             # Archivos públicos
│   └── vite.svg        # Logo de Vite
├── src/                # Código fuente
│   ├── assets/         # Assets como imágenes
│   │   └── react.svg   # Logo de React
│   ├── App.css         # Estilos principales
│   ├── App.jsx         # Componente principal
│   ├── index.css       # Estilos globales
│   └── main.jsx        # Punto de entrada
├── .eslintrc.cjs       # Configuración ESLint
├── .gitignore          # Archivos ignorados por git
├── index.html          # HTML principal
├── package.json        # Configuración del proyecto y dependencias
├── package-lock.json   # Versiones exactas de dependencias
└── vite.config.js      # Configuración de Vite
```

## Comandos disponibles

### Ejecutar servidor de desarrollo
```bash
npm run dev
```
Abre [http://localhost:5173](http://localhost:5173) en tu navegador.

### Compilar para producción
```bash
npm run build
```
Genera los archivos optimizados en la carpeta `dist/`.

### Servir versión de producción localmente
```bash
npm run preview
```

## Personalización
Puedes empezar a editar el componente principal modificando `src/App.jsx`. La página se actualizará automáticamente cuando guardes los cambios.

## Licencia
Este proyecto está bajo la licencia MIT.