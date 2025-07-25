import React from "react";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import "../App.css"; // Importa el archivo CSS

const MiBar: React.FC = () => {
  return (
    <AppBar position="static" className="mi-bar">
      <Toolbar className="mi-toolbar">
        <IconButton
          edge="start"
          color="inherit"
          aria-label="menu"
          className="mi-icon-button"
        >
          <MenuIcon />
        </IconButton>
        <Typography variant="h6" className="mi-typography">
          My MUI App
        </Typography>
        <Button className="mi-button">Login</Button>
      </Toolbar>
    </AppBar>
  );
};

export default MiBar;
