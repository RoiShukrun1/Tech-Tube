import React, { createContext, useState } from 'react';

// Create a context
export const CurrentPublisherContext = createContext();

// Provider component
export const CurrentPublisherProvider = ({ children }) => {
  const [publisher, setPublisher] = useState(null);

  return (
    <CurrentPublisherContext.Provider value={{ publisher, setPublisher }}>
      {children}
    </CurrentPublisherContext.Provider>
  );
};