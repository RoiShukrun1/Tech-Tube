import React, { createContext, useState, useEffect } from 'react';

// Create a context for theme management
export const ThemeContext = createContext();

/**
 * ThemeProvider component to manage dark mode state and provide it to child components.
 * It sets a 'data-theme' attribute on the document's root element to toggle between dark and light themes.
 *
 * @param {Object} props - The properties passed to the component.
 * @param {React.ReactNode} props.children - The child components that need access to the theme context.
 */
export const ThemeProvider = ({ children }) => {
  // State to keep track of whether dark mode is enabled
  const [darkMode, setDarkMode] = useState(false);

  /**
   * useEffect hook to apply the dark mode class to the document's root element whenever the darkMode state changes.
   * Also saves the dark mode state to localStorage.
   */
  useEffect(() => {
    const savedDarkMode = localStorage.getItem('darkMode') === 'true';
    setDarkMode(savedDarkMode);
  }, []);

  useEffect(() => {
    if (darkMode) {
      // Apply dark theme
      document.documentElement.setAttribute('data-theme', 'dark');
    } else {
      // Apply light theme
      document.documentElement.setAttribute('data-theme', 'light');
    }
    localStorage.setItem('darkMode', darkMode);
  }, [darkMode]);

  /**
   * Function to toggle dark mode on and off.
   */
  const toggleDarkMode = () => {
    setDarkMode((prevMode) => !prevMode);
  };

  return (
    <ThemeContext.Provider value={{ darkMode, toggleDarkMode }}>
      {children}
    </ThemeContext.Provider>
  );
};
