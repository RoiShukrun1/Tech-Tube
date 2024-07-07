import React, { useEffect, useContext } from 'react';
import { LoginContext } from '../contexts/loginContext';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const LoginValidation = ({ children }) => {
  const { login, loggedIn, loggedOut } = useContext(LoginContext);
  const navigate = useNavigate();

  useEffect(() => {
    const checkAuth = async () => {
      try {
        const response = await axios.get('http://localhost/api/token/checkAuth', { withCredentials: true });
        if (response.data.isAuthenticated) {
          loggedIn(response.data.user);
        } else {
          loggedOut();
          alert("You have to be logged in to access this page. Redirecting to the login page");
          navigate('/login');
        }
      } catch (error) {
        loggedOut();
        alert("You have to be logged in to access this page. Redirecting to the login page");
        navigate('/login');
      }
    };

    if (!login) {
      checkAuth();
    }
  }, [login, loggedIn, loggedOut, navigate]);

  return login ? <>{children}</> : null;
};

export default LoginValidation;
