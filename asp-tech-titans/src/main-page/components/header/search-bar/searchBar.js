import React, { useState, useContext } from 'react';
import './searchBar.css';
import searchIcon from '../../../../db/icons/search-icon.svg';
import searchIconDm from '../../../../db/icons/search-icon-dm.svg';
import { ThemeContext } from '../../../../contexts/themeContext';

function SearchBar({ onSearch }) {
  const [query, setQuery] = useState('');
  const { darkMode } = useContext(ThemeContext);

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
    <div className={`search-bar ${darkMode ? 'dark' : ''}`}>
      <input
        className='search-bar-input'
        type="text"
        placeholder="Search"
        value={query}
        onChange={handleInputChange}
        onKeyPress={handleKeyPress}
      />
      <button className="search-bar-button" type="button" onClick={handleSearch}>
        <img src={darkMode ? searchIconDm : searchIcon} alt="Search" />
      </button>
    </div>
  );
}

export default SearchBar;
