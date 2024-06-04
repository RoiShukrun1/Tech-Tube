// SearchBar.js

import React, { useState } from 'react';
import './searchBar.css';
import searchIcon from '../../../../db/icons/search-icon.svg'; // Adjust the path if necessary

function SearchBar({ onSearch }) {
  const [query, setQuery] = useState('');

  const handleInputChange = (e) => {
    setQuery(e.target.value);
  };

  const handleSearch = () => {
    onSearch(query);
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleSearch();
    }
  };

  return (
    <div className="search-bar">
      <input
        type="text"
        placeholder="Search"
        value={query}
        onChange={handleInputChange}
        onKeyPress={handleKeyPress}
      />
      <button type="button" onClick={handleSearch}>
        <img src={searchIcon} alt="Search" />
      </button>
    </div>
  );
}

export default SearchBar;
