# Aplicación del Clima (Weather App)

Una aplicación meteorológica que muestra las condiciones climáticas actuales de cualquier ciudad del mundo.

## Características Principales

- Búsqueda por ciudad para obtener datos meteorológicos actuales
- Visualización de temperatura en grados Celsius
- Muestra el icono y descripción de las condiciones climáticas
- Incluye información de ubicación (ciudad y país)
- Diseño responsive con Material-UI
- Manejo de estados de carga y errores

## Tecnologías Utilizadas

- React con Hooks (useState, useEffect)
- Material-UI para componentes y estilos
- API de WeatherAPI.com para datos meteorológicos
- Fetch API para peticiones HTTP

## Instalación

1. Clonar el repositorio
2. Crear archivo `.env` con tu API key:
   ```
   VITE_API_KEY=tu_api_key_aqui
   ```
3. Instalar dependencias:
   ```bash
   npm install
   ```
4. Iniciar la aplicación:
   ```bash
   npm run dev
   ```

## Cómo Usar

1. Ingresa el nombre de una ciudad en el campo de búsqueda
2. Presiona el botón "Buscar"
3. La aplicación mostrará:
   - Nombre de la ciudad y país
   - Temperatura actual
   - Icono representando las condiciones climáticas
   - Descripción textual del clima

## Componentes Principales

- **useWeather**: Custom Hook que maneja la lógica de obtención de datos
- **App**: Componente principal que contiene la interfaz de usuario
- **TextField**: Campo de entrada para la ciudad
- **Button**: Botón para realizar la búsqueda

## API Utilizada

La aplicación utiliza la API de [WeatherAPI.com](https://www.weatherapi.com/) para obtener datos meteorológicos en tiempo real.

## Variables de Entorno

El proyecto requiere una variable de entorno `VITE_API_KEY` con tu clave de API de WeatherAPI.

## Manejo de Errores

La aplicación detecta y muestra:
- Errores de conexión
- Ciudades no encontradas
- Problemas con la API

## Estructura del Proyecto

- Lógica de fetching separada en custom hook
- Diseño modular con componentes de Material-UI
- Estilos inline con el sistema sx de Material-UI

## Mejoras Futuras

- Pronóstico extendido (5 días)
- Búsqueda por ubicación geográfica
- Cambio entre unidades Celsius/Fahrenheit
- Historial de búsquedas recientes
- Modo oscuro/nocturno

## Licencia

MIT License - Libre para uso y modificación