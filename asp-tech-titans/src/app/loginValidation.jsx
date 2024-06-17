import React, { useEffect, useContext } from 'react';
import { LoginContext } from '../contexts/loginContext'; // Import the LoginContext
import { useNavigate } from 'react-router-dom';

const LoginValidation = ({ children }) => {
  const { login } = useContext(LoginContext);
  const navigate = useNavigate();

  useEffect(() => {
    if (!login) {
      alert("You have to be logged in to access this page. Redirecting to the login page");
      navigate('/login');
    }
  }, [login, navigate]);
  return login ? <>{children}</> : null;
};

export default LoginValidation;
