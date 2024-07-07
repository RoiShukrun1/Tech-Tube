import React, { createContext, useState, useEffect } from 'react';
import axios from 'axios';

export const LoginContext = createContext();

export const LoginProvider = ({ children }) => {
  const [login, setLogin] = useState(null);

  const loggedIn = (userData) => {
    setLogin(userData);
  };

  const loggedOut = async () => {
    try {
      await axios.post('http://localhost/api/token/logout', {}, { withCredentials: true });
      setLogin(null);
    } catch (error) {
      console.error('Error logging out:', error);
    }
  };

  useEffect(() => {
    const checkAuth = async () => {
      try {
        const response = await axios.get('http://localhost/api/token/checkAuth', { withCredentials: true });
        if (response.data.isAuthenticated) {
          loggedIn(response.data.user);
        } else {
          loggedOut();
        }
      } catch (error) {
        loggedOut();
      }
    };

    checkAuth();
  }, []);

  return (
    <LoginContext.Provider value={{ login, loggedIn, loggedOut }}>
      {children}
    </LoginContext.Provider>
  );
};
