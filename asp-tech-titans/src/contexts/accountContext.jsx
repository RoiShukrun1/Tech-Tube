import React, { createContext, useState } from 'react';

export const AccountContext = createContext();

export const AccountProvider = ({ children }) => {
  const [accounts, setAccounts] = useState([]);

  const addAccount = (account) => {
    setAccounts((prevAccounts) => [...prevAccounts, account]);
  };

  return (
    <AccountContext.Provider value={{ accounts, addAccount, setAccounts }}>
      {children}
    </AccountContext.Provider>
  );
};
