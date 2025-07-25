import { Link } from "react-router-dom";

export const NavigationBar = () => {
  return (
    <nav className="nav-container">
      <div className="nav-links">
        <Link to="/" className="nav-link">
          Home
        </Link>
        <Link to="/register" className="nav-link">
          Register
        </Link>
        <Link to="/summary" className="nav-link">
          Summary
        </Link>
        <Link to="/about" className="nav-link">
          About
        </Link>
      </div>
    </nav>
  );
};
