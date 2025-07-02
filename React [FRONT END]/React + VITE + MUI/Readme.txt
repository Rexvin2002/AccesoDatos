# README - React + Vite + Material UI (MUI)

## Ejercicio Práctico: Implementación de componentes MUI

### Descripción
Este proyecto demuestra la integración de Material UI (MUI) con React y Vite, implementando componentes MUI organizados en una estructura modular.

### Requisitos
- Node.js (v14.18+)
- npm (v7+)

### Instalación
1. Crear proyecto Vite:
```bash
npm create vite@latest mui-react-app -- --template react
cd mui-react-app
```

2. Instalar dependencias:
```bash
npm install @mui/material @emotion/react @emotion/styled @mui/icons-material
```

3. Iniciar servidor de desarrollo:
```bash
npm run dev
```

### Estructura de componentes
```
src/
├── components/
│   ├── MiBar.jsx       # Componente AppBar con Toolbar
│   ├── MiContent.jsx   # Componente Container con Grid
│   └── MiCard.jsx      # Componente Card reusable
├── App.jsx             # Componente principal
└── main.jsx            # Punto de entrada
```

### Implementación de componentes MUI

#### 1. MiBar.jsx (AppBar component)
```jsx
import { AppBar, Toolbar, IconButton, Typography, Button, MenuItem } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';

export default function MiBar() {
  return (
    <AppBar position="static">
      <Toolbar>
        <IconButton edge="start" color="inherit" aria-label="menu">
          <MenuIcon />
          <MenuItem>Opción 1</MenuItem>
        </IconButton>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          Mi App MUI
        </Typography>
        <Button color="inherit">Login</Button>
      </Toolbar>
    </AppBar>
  );
}
```

#### 2. MiContent.jsx (Grid layout)
```jsx
import { Container, Grid } from '@mui/material';
import MiCard from './MiCard';

export default function MiContent() {
  const loremIpsum = "Lorem ipsum dolor sit amet..."; // Texto largo aquí

  return (
    <Container maxWidth="lg" sx={{ mt: 4 }}>
      <Grid container spacing={3}>
        <Grid item xs={12} md={6}>
          <Typography variant="body1">{loremIpsum}</Typography>
        </Grid>
        <Grid item xs={12} md={6}>
          <MiCard />
        </Grid>
      </Grid>
    </Container>
  );
}
```

#### 3. MiCard.jsx (Card component)
```jsx
import { Card, CardMedia, CardContent, CardActions, Typography, Button } from '@mui/material';

export default function MiCard() {
  return (
    <Card sx={{ maxWidth: 345 }}>
      <CardMedia
        component="img"
        height="140"
        image="/placeholder.jpg"
        alt="Imagen de ejemplo"
      />
      <CardContent>
        <Typography gutterBottom variant="h5" component="div">
          Tarjeta MUI
        </Typography>
        <Typography variant="body2" color="text.secondary">
          Este es un ejemplo de tarjeta usando Material UI con props personalizadas.
        </Typography>
      </CardContent>
      <CardActions>
        <Button size="small">Compartir</Button>
        <Button size="small" color="secondary">Aprender más</Button>
      </CardActions>
    </Card>
  );
}
```

#### App.jsx (Componente principal)
```jsx
import MiBar from './components/MiBar';
import MiContent from './components/MiContent';

function App() {
  return (
    <>
      <MiBar />
      <MiContent />
    </>
  );
}

export default App;
```

### Características implementadas
1. **Responsive Grid**: Uso de `Grid` con props `xs`, `md` para diseño adaptable
2. **Componentes MUI**: AppBar, Toolbar, IconButton, Typography, Button, Container, Grid, Card
3. **Props personalizadas**: Estilos sx, variantes, tamaños y colores
4. **Organización modular**: Componentes separados para cada sección

### Personalización
Para modificar el proyecto:
1. Cambia los textos en los componentes
2. Ajusta los props de Grid para diferentes layouts
3. Modifica los colores usando el sistema de temas de MUI
4. Añade más componentes de la documentación MUI

### Documentación MUI
Consulta todos los componentes disponibles en:  
[https://mui.com/material-ui/all-components/](https://mui.com/material-ui/all-components/)