# Juego de Tres en Raya con React Context

Un juego clásico de Tres en Raya implementado con React y el patrón Context API para manejo de estado global.

## Características Principales

- **Arquitectura basada en Context API**: Estado global accesible en todos los componentes
- **Lógica completa del juego**: Turnos alternados, detección de ganador y empates
- **Componentes reutilizables**: Diseño modular y desacoplado
- **Interfaz intuitiva**: Visualización clara del estado del juego

## Tecnologías Utilizadas

- React con Hooks (useState, useContext)
- Context API para gestión de estado global
- CSS para estilizado básico
- JavaScript ES6+ para la lógica del juego

## Instalación

1. Clonar el repositorio
2. Instalar dependencias:
   ```bash
   npm install
   ```
3. Iniciar la aplicación:
   ```bash
   npm start
   ```

## Estructura del Código

### Componentes Principales

1. **ProveedorJuego**:
   - Maneja el estado global del juego
   - Contiene la lógica principal (turnos, ganador, empates)
   - Provee el contexto a todos los componentes hijos

2. **Tablero**:
   - Renderiza la cuadrícula 3x3
   - Mapea las casillas usando el estado del contexto

3. **Casilla**:
   - Componente presentacional para cada celda
   - Muestra X/O y maneja clicks

4. **BarraEstado**:
   - Muestra información del juego (turno actual, ganador)
   - Controles para reiniciar el juego

### Hooks Personalizados

- **ContextoJuego**: Proporciona acceso al estado y funciones del juego

## Reglas del Juego

1. Alternancia de turnos entre Jugador 1 (X) y Jugador 2 (O)
2. Gana el primero en alinear 3 símbolos iguales
3. Las líneas pueden ser horizontales, verticales o diagonales
4. Si se llena el tablero sin ganador, es empate

## Personalización

Puedes modificar fácilmente:
- Estilos CSS en los componentes
- Textos y mensajes en la barra de estado
- Comportamiento del juego editando el ProveedorJuego

## Mejoras Futuras

- [ ] Historial de movimientos
- [ ] Modo para jugar contra la computadora
- [ ] Temporizador por turno
- [ ] Puntuación acumulativa
- [ ] Animaciones y efectos visuales

## Licencia

MIT License - Libre para uso y modificación