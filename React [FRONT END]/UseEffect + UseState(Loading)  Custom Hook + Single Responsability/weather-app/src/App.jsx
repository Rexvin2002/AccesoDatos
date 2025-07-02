import React, { useState, useEffect } from "react";
import {
  Container,
  Typography,
  Box,
  TextField,
  Button,
  CircularProgress,
} from "@mui/material";

const API_WEATHER = `https://api.weatherapi.com/v1/current.json?key=${import.meta.env.VITE_API_KEY}&q=`;

// Componente para obtener los datos del clima
const useWeather = (city) => {
  const [weather, setWeather] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!city) return;
    setLoading(true);
    setError(null);

    const fetchWeather = async () => {
      try {
        const response = await fetch(API_WEATHER + city);
        const data = await response.json();
        
        if (data.error) throw new Error(data.error.message);

        setWeather({
          city: data.location.name,
          country: data.location.country,
          temp: data.current.temp_c,
          icon: data.current.condition.icon,
          conditionText: data.current.condition.text,
        });
      } catch (ex) {
        setError(ex.message);
      } finally {
        setLoading(false);
      }
    };

    fetchWeather();
  }, [city]);

  return { weather, loading, error };
};

const App = () => {
  const [city, setCity] = useState("");
  const [query, setQuery] = useState("");
  const { weather, loading, error } = useWeather(query);

  const handleSubmit = (e) => {
    e.preventDefault();
    setQuery(city);
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
        onSubmit={handleSubmit}
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
      {error && (
        <Typography color="error" align="center" sx={{ mt: 2 }}>
          {error}
        </Typography>
      )}
      {weather && (
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