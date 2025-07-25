import { useContext, FormEvent } from "react";
import { FormContext } from "../App";
import { useNavigate } from "react-router-dom";

export const Register = () => {
  const { setFormData } = useContext(FormContext);
  const navigate = useNavigate();

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    const data = {
      name: formData.get("name"),
      email: formData.get("email"),
      phone: formData.get("phone"),
      address: formData.get("address"),
    };

    const summary = Object.entries(data)
      .map(([key, value]) => `${key}: ${value}`)
      .join(" | ");

    setFormData(summary);
    navigate("/summary");
  };

  return (
    <div className="fade-in">
      <div className="form-container">
        <h2 className="text-center mb-4">Formulario de Registro</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label">Nombre</label>
            <input type="text" name="name" className="form-input" required />
          </div>
          <div className="form-group">
            <label className="form-label">Email</label>
            <input type="email" name="email" className="form-input" required />
          </div>
          <div className="form-group">
            <label className="form-label">Teléfono</label>
            <input type="tel" name="phone" className="form-input" required />
          </div>
          <div className="form-group">
            <label className="form-label">Dirección</label>
            <textarea name="address" className="form-input" required />
          </div>
          <button type="submit" className="button">
            Enviar
          </button>
        </form>
      </div>
    </div>
  );
};
