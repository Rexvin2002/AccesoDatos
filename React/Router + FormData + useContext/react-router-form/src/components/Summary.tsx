import { useContext } from "react";
import { FormContext } from "../App";

export const Summary = () => {
  const { formData } = useContext(FormContext);

  if (!formData) {
    return (
      <div className="form-container text-center fade-in">
        <h2 className="mb-3">No hay datos enviados</h2>
        <p>Por favor, complete el formulario de registro primero.</p>
      </div>
    );
  }

  const formEntries = formData.split(" | ").map((entry) => {
    const [key, value] = entry.split(": ");
    return { key, value };
  });

  return (
    <div className="fade-in">
      <div className="summary-container">
        <h2 className="text-center mb-4">Resumen de Registro</h2>
        {formEntries.map(({ key, value }) => (
          <div key={key} className="summary-item">
            <div className="summary-label">{key}</div>
            <div className="summary-value">{value}</div>
          </div>
        ))}
      </div>
    </div>
  );
};
