import './App.css';
import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import AppRoutes from './appRoutes';
import { ThemeProvider } from '../contexts/themeContext';

function App() {

  return (
    <ThemeProvider>
      <Router>
        <AppRoutes />
      </Router>
    </ThemeProvider>
  );
}
export default App;
