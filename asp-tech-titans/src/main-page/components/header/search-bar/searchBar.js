// SearchBar.js

import React from 'react';
import './searchBar.css';
import searchIcon from './search-icon.svg'; // Adjust the path if necessary


const SearchBar = () => {
  return (
    <div className="search-bar">
      <input type="text" placeholder="Search" />
      <button type="submit">  <img src={searchIcon} alt="Search" /></button>
    </div>
  );
};

export default SearchBar;
