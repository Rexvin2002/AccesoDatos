import React, { useState } from "react";
import "./App.css";

const Square = ({ value, onClick }) => (
  <button
    className={`square ${value ? value.toLowerCase() : ""}`}
    onClick={onClick}
  >
    {value}
  </button>
);

const Board = () => {
  const [squares, setSquares] = useState(Array(9).fill(null));
  const [xIsNext, setXIsNext] = useState(true);

  const calculateWinner = (squares) => {
    const lines = [
      [0, 1, 2],
      [3, 4, 5],
      [6, 7, 8],
      [0, 3, 6],
      [1, 4, 7],
      [2, 5, 8],
      [0, 4, 8],
      [2, 4, 6],
    ];
    for (const [a, b, c] of lines) {
      if (
        squares[a] &&
        squares[a] === squares[b] &&
        squares[a] === squares[c]
      ) {
        return squares[a];
      }
    }
    return null;
  };

  const winner = calculateWinner(squares);
  const isDraw = !winner && squares.every((square) => square !== null);

  const handleClick = (i) => {
    if (squares[i] || winner) return;
    const nextSquares = squares.slice();
    nextSquares[i] = xIsNext ? "X" : "O";
    setSquares(nextSquares);
    setXIsNext(!xIsNext);
  };

  const resetGame = () => {
    setSquares(Array(9).fill(null));
    setXIsNext(true);
  };

  return (
    <div className="board-container">
      <div className="status-bar">
        <button className="status">NEXT: PLAYER {xIsNext ? "1" : "2"}</button>

        {(winner || isDraw) && (
          <button className="winner-btn" onClick={resetGame}>
            {winner ? `PLAYER ${winner === "X" ? "1" : "2"} WINS` : "DRAW!"}
          </button>
        )}
        <button className="new-game-btn" onClick={resetGame}>
          NEW GAME
        </button>
      </div>
      <div className="board">
        {squares.map((square, i) => (
          <Square key={i} value={square} onClick={() => handleClick(i)} />
        ))}
      </div>
    </div>
  );
};

export default Board;
