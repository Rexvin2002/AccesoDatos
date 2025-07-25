import React, { useState } from "react";
import {
  Container,
  Typography,
  Box,
  TextField,
  Button,
  CircularProgress,
} from "@mui/material";

const App = () => {
  const API_WEATHER = `https://api.weatherapi.com/v1/current.json?key=${
    import.meta.env.VITE_API_KEY
  }&q=`;

  // Estados principales
  const [city, setCity] = useState(""); // Almacena el valor del campo input
  const [loading, setLoading] = useState(false); // Indica si la app está cargando datos
  const [error, setError] = useState({ error: false, message: "" }); // Maneja errores
  const [weather, setWeather] = useState({
    city: "",
    country: "",
    temp: "",
    condition: "",
    icon: "",
    conditionText: "",
  });

  // Manejador de envío del formulario
  const onSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError({ error: false, message: "" }); // Limpiar el error antes de cada intento

    try {
      if (!city.trim()) throw { message: "El campo ciudad es obligatorio" };

      const response = await fetch(API_WEATHER + city);
      const data = await response.json();

      // Si hay un error en los datos (por ejemplo, ciudad no encontrada)
      if (data.error) throw { message: data.error.message };

      setWeather({
        city: data.location.name,
        country: data.location.country,
        temp: data.current.temp_c,
        condition: data.current.condition.code,
        icon: data.current.condition.icon,
        conditionText: data.current.condition.text,
      });
    } catch (ex) {
      setError({ error: true, message: ex.message });
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container maxWidth="xs" sx={{ mt: 2 }}>
      <Typography variant="h3" component="h1" align="center" gutterBottom>
        Weather App
      </Typography>
      <Box
        sx={{ display: "grid", gap: 2 }}
        component="form"
        autoComplete="off"
        onSubmit={onSubmit}
      >
        <TextField
          id="city"
          label="Ciudad"
          variant="outlined"
          required
          fullWidth
          size="small"
          value={city}
          onChange={(e) => setCity(e.target.value)}
        />
        <Button
          type="submit"
          variant="contained"
          disabled={loading}
          endIcon={loading && <CircularProgress size={20} />}
        >
          {loading ? "Cargando..." : "Buscar"}
        </Button>
      </Box>
      {error.error && (
        <Typography color="error" align="center" sx={{ mt: 2 }}>
          {error.message}
        </Typography>
      )}
      {weather.city && (
        <Box sx={{ mt: 2, display: "grid", gap: 2, textAlign: "center" }}>
          <Typography variant="h4" component="h2">
            {weather.city}, {weather.country}
          </Typography>
          <Box
            component="img"
            alt={weather.conditionText}
            src={weather.icon}
            sx={{ margin: "0 auto" }}
          />
          <Typography variant="h2" component="h2">
            {weather.temp}ºC
          </Typography>
          <Typography variant="h5" component="h5">
            {weather.conditionText}
          </Typography>
        </Box>
      )}
    </Container>
  );
};

export default App;
