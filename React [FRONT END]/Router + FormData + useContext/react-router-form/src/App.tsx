import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { createContext, useState } from 'react';
import { NavigationBar } from './components/NavigationBar';
import { Home } from './components/Home';
import { Register } from './components/Register';
import { Summary } from './components/Summary';
import { About } from './components/About';
import './styles/index.css';

export const FormContext = createContext<{
  formData: string;
  setFormData: (data: string) => void;
}>({
  formData: '',
  setFormData: () => {},
});

const App = () => {
  const [formData, setFormData] = useState('');

  return (
    <FormContext.Provider value={{ formData, setFormData }}>
      <Router>
        <div className="app-container">
          <NavigationBar />
          <main className="main-container">
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/register" element={<Register />} />
              <Route path="/summary" element={<Summary />} />
              <Route path="/about" element={<About />} />
            </Routes>
          </main>
        </div>
      </Router>
    </FormContext.Provider>
  );
};

export default App;