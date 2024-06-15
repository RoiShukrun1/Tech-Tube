import React, { createContext, useState } from 'react';

export const LoginContext = createContext();

export const LoginProvider = ({ children }) => {
  const [login, setLogin] = useState(null);

  const loggedIn = (userData) => {
    setLogin(userData);
  };

  const loggedOut = () => {
    setLogin(null);
  };

  return (
    <LoginContext.Provider value={{ login, loggedIn, loggedOut }}>
      {children}
    </LoginContext.Provider>
  );
};
