# Juego de Tres en Raya (Tic-Tac-Toe)

Un juego clásico de Tres en Raya implementado con React.

## Características

- Juego tradicional de 3x3
- Sistema de turnos (Jugador 1: X, Jugador 2: O)
- Detección automática de victorias en todas las combinaciones posibles
- Reconocimiento de empates cuando el tablero está lleno
- Indicadores visuales del jugador actual y estado del juego
- Función para reiniciar la partida

## Tecnologías Utilizadas

- React (Componentes funcionales con Hooks)
- CSS para los estilos
- JavaScript para la lógica del juego

## Cómo Jugar

1. Los jugadores alternan turnos haciendo clic en casillas vacías
2. El Jugador 1 es X y comienza primero
3. El Jugador 2 es O y juega segundo
4. El primer jugador en alinear 3 marcas (horizontal, vertical o diagonal) gana
5. Si todas las casillas se llenan sin ganador, el juego termina en empate

## Componentes del Juego

- **Casilla (Square)**: Componente individual clickeable
- **Tablero (Board)**: Gestor principal del estado del juego
- **Barra de estado**: Muestra el jugador actual y resultados

## Instalación

1. Clona el repositorio
2. Instala las dependencias:
   ```bash
   npm install
   ```
3. Inicia la aplicación:
   ```bash
   npm start
   ```

## Lógica del Juego

- `calculateWinner`: Verifica todas las combinaciones ganadoras posibles
- `handleClick`: Maneja los movimientos de los jugadores y actualiza el tablero
- `resetGame`: Reinicia el juego al estado inicial

## Diseño

- Interfaz limpia y minimalista con retroalimentación visual clara
- X en rojo y O en azul para mejor visibilidad
- Diseño responsive que funciona en diferentes tamaños de pantalla

## Mejoras Futuras

- Contador de puntos entre múltiples partidas
- Personalización de nombres de jugadores
- Historial de partidas y función para deshacer movimientos
- Niveles de dificultad para jugar contra la máquina

## Licencia

Licencia MIT - Libre para usar y modificar