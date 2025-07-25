export const About = () => {
  return (
    <div className="fade-in">
      <div className="form-container">
        <h1 className="text-center mb-4">Sobre Nosotros</h1>
        <p className="mb-4">
          Esta aplicación demuestra el uso de características modernas de React
          incluyendo:
        </p>
        <div className="summary-item">
          <ul className="mt-2">
            <li className="mb-2">React Router para navegación</li>
            <li className="mb-2">Manejo de formularios con FormData API</li>
            <li className="mb-2">Context API para gestión de estado</li>
            <li className="mb-2">Diseño responsivo con CSS moderno</li>
          </ul>
        </div>
      </div>
    </div>
  );
};
