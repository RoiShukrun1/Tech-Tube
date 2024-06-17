import React from 'react';
import './scrollingMenuButton.css';

const ScrollingMenuButton = ({ isOpen, toggleMenu }) => {
  return (
    <button className="menu-button" onClick={toggleMenu}>
      {isOpen ? '☰' : '☰'}
    </button>
  );
};

export default ScrollingMenuButton;
