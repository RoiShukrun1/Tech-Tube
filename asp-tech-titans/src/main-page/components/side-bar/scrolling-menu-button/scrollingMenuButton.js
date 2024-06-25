import React from 'react';
import './scrollingMenuButton.css';

/**
 * ScrollingMenuButton Component
 * 
 * This component renders a button to toggle the scrolling menu.
 * 
 * Props:
 * - isOpen (boolean): Indicates whether the menu is open or closed.
 * - toggleMenu (function): A function to toggle the menu open or closed.
 */
const ScrollingMenuButton = ({ isOpen, toggleMenu }) => {
  return (
    <button className="menu-button" onClick={toggleMenu}>
      {isOpen ? '☰' : '☰'}
    </button>
  );
};

export default ScrollingMenuButton;
