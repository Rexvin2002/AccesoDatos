import React from "react";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import MiCard from "./miCard";
import "../App.css"; // Importa el archivo CSS

const MiContent: React.FC = () => {
  return (
    <Container className="mi-content">
      <Grid container spacing={2}>
        <Grid item xs={12} sm={6} md={4}>
          <Typography variant="body1" className="mi-content-text">
            Content 1: Sample text from the web
          </Typography>
        </Grid>
        <Grid item xs={12} sm={6} md={4}>
          <Typography variant="body1" className="mi-content-text">
            Content 2: Another sample text
          </Typography>
        </Grid>
        <Grid item xs={12} sm={6} md={4}>
          <MiCard />
        </Grid>
      </Grid>
    </Container>
  );
};

export default MiContent;
