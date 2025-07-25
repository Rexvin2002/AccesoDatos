import React, { createContext, useContext, useState } from "react";

// Creación del Contexto del Juego
const ContextoJuego = createContext();

// Componente Proveedor del Juego
const ProveedorJuego = ({ children }) => {
  // Estado para las casillas del tablero (null = vacío)
  const [casillas, setCasillas] = useState(Array(9).fill(null));
  // Estado para determinar el turno (true = X, false = O)
  const [esTurnoDeX, setEsTurnoDeX] = useState(true);

  // Función para calcular si hay un ganador
  const calcularGanador = (casillas) => {
    // Combinaciones ganadoras posibles (horizontal, vertical y diagonal)
    const lineas = [
      [0, 1, 2], // Primera fila
      [3, 4, 5], // Segunda fila
      [6, 7, 8], // Tercera fila
      [0, 3, 6], // Primera columna
      [1, 4, 7], // Segunda columna
      [2, 5, 8], // Tercera columna
      [0, 4, 8], // Diagonal principal
      [2, 4, 6], // Diagonal inversa
    ];
    // Verifica cada combinación ganadora
    for (const [a, b, c] of lineas) {
      if (
        casillas[a] &&
        casillas[a] === casillas[b] &&
        casillas[a] === casillas[c]
      ) {
        return casillas[a];
      }
    }
    return null;
  };

  const ganador = calcularGanador(casillas);
  // Verifica si hay empate (todas las casillas llenas y sin ganador)
  const hayEmpate = !ganador && casillas.every((casilla) => casilla !== null);

  // Maneja el click en una casilla
  const manejarClick = (i) => {
    if (casillas[i] || ganador) return;
    const siguientesCasillas = casillas.slice();
    siguientesCasillas[i] = esTurnoDeX ? "X" : "O";
    setCasillas(siguientesCasillas);
    setEsTurnoDeX(!esTurnoDeX);
  };

  // Reinicia el juego
  const reiniciarJuego = () => {
    setCasillas(Array(9).fill(null));
    setEsTurnoDeX(true);
  };

  // Valores que estarán disponibles en el contexto
  const valor = {
    casillas,
    esTurnoDeX,
    ganador,
    hayEmpate,
    manejarClick,
    reiniciarJuego,
  };

  return (
    <ContextoJuego.Provider value={valor}>{children}</ContextoJuego.Provider>
  );
};

// Componente Casilla
const Casilla = ({ value, onClick }) => (
  <button
    className={`square ${value ? value.toLowerCase() : ""}`}
    onClick={onClick}
  >
    {value}
  </button>
);

// Componente Barra de Estado
const BarraEstado = () => {
  const { esTurnoDeX, ganador, hayEmpate, reiniciarJuego } =
    useContext(ContextoJuego);

  return (
    <div className="status-bar">
      <button className="status">
        SIGUIENTE: JUGADOR {esTurnoDeX ? "1" : "2"}
      </button>
      {(ganador || hayEmpate) && (
        <button className="winner-btn" onClick={reiniciarJuego}>
          {ganador ? `JUGADOR ${ganador === "X" ? "1" : "2"} GANA` : "¡EMPATE!"}
        </button>
      )}
      <button className="new-game-btn" onClick={reiniciarJuego}>
        NUEVO JUEGO
      </button>
    </div>
  );
};

// Componente Tablero
const Tablero = () => {
  const { casillas, manejarClick } = useContext(ContextoJuego);

  return (
    <div className="board">
      {casillas.map((casilla, i) => (
        <Casilla key={i} value={casilla} onClick={() => manejarClick(i)} />
      ))}
    </div>
  );
};

// Componente Principal del Juego
const Juego = () => {
  return (
    <ProveedorJuego>
      <div className="board-container">
        <BarraEstado />
        <Tablero />
      </div>
    </ProveedorJuego>
  );
};

export default Juego;
