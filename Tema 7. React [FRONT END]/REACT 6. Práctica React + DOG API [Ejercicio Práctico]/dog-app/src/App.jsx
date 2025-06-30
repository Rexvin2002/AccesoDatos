import React, { useState, useEffect } from "react";
import {
  Container,
  Typography,
  Box,
  Button,
  CircularProgress,
  Card,
  CardContent,
  CardMedia,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";

const App = () => {
  const API_DOG_LIST = "https://dog.ceo/api/breeds/list/all";
  const API_DOG_IMAGE = "https://dog.ceo/api/breed/";

  // Estados principales
  const [breeds, setBreeds] = useState([]); // Lista de razas disponibles
  const [selectedBreed, setSelectedBreed] = useState(""); // Raza seleccionada
  const [loading, setLoading] = useState(false); // Indica si la app está cargando datos
  const [error, setError] = useState({ error: false, message: "" }); // Maneja errores
  const [dogImage, setDogImage] = useState(""); // Almacena la imagen del perro

  // Cargar la lista de razas disponibles al iniciar
  useEffect(() => {
    const fetchBreeds = async () => {
      try {
        const response = await fetch(API_DOG_LIST);
        const data = await response.json();
        if (data.status === "success") {
          setBreeds(Object.keys(data.message)); // Guardar las razas en el estado
        } else {
          throw { message: "Error al obtener las razas." };
        }
      } catch (ex) {
        setError({ error: true, message: ex.message });
      }
    };
    fetchBreeds();
  }, []);

  // Manejador de envío del formulario
  const onSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError({ error: false, message: "" }); // Limpiar el error antes de cada intento

    try {
      if (!selectedBreed.trim()) throw { message: "Debe seleccionar una raza" };

      const response = await fetch(`${API_DOG_IMAGE}${selectedBreed}/images/random`);
      const data = await response.json();

      // Si la raza no existe o hay un error
      if (data.status !== "success") throw { message: "No se encontró imagen" };

      setDogImage(data.message);
    } catch (ex) {
      setError({ error: true, message: ex.message });
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container maxWidth="xs" sx={{ mt: 2 }}>
      <Typography variant="h3" component="h1" align="center" gutterBottom>
        Dog Image App
      </Typography>
      <Box
        sx={{ display: "grid", gap: 2 }}
        component="form"
        autoComplete="off"
        onSubmit={onSubmit}
      >
        {/* ComboBox para seleccionar raza */}
        <FormControl fullWidth size="small" required>
          <InputLabel>Raza de Perro</InputLabel>
          <Select
            value={selectedBreed}
            label="Raza de Perro"
            onChange={(e) => setSelectedBreed(e.target.value)}
          >
            {breeds.map((breed) => (
              <MenuItem key={breed} value={breed}>
                {breed}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <Button
          type="submit"
          variant="contained"
          disabled={loading}
          endIcon={loading && <CircularProgress size={20} />}
        >
          {loading ? "Cargando..." : "Buscar Imagen"}
        </Button>
      </Box>
      {error.error && (
        <Typography color="error" align="center" sx={{ mt: 2 }}>
          {error.message}
        </Typography>
      )}
      {dogImage && (
        <Box sx={{ mt: 2, display: "grid", gap: 2, textAlign: "center" }}>
          <Card sx={{ maxWidth: 345, margin: "auto" }}>
            <CardMedia
              component="img"
              height="250"
              image={dogImage}
              alt={`Imagen de un perro de raza ${selectedBreed}`}
            />
            <CardContent>
              <Typography variant="h5" component="div">
                Raza: {selectedBreed}
              </Typography>
            </CardContent>
          </Card>
        </Box>
      )}
    </Container>
  );
};

export default App;
