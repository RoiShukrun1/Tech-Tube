import React, { useState, useContext } from 'react';
import './searchBar.css';
import searchIcon from '../../../../db/icons/search-icon.svg';
import searchIconDm from '../../../../db/icons/search-icon-dm.svg';
import { ThemeContext } from '../../../../contexts/themeContext';

/**
 * SearchBar Component
 * 
 * This component renders a search bar with an input field and a search button.
 * It uses ThemeContext to adjust its styles based on the current theme (dark or light mode).
 * The search query is managed using the useState hook, and an onSearch function is called 
 * when the user submits the search.
 * 
 * Props:
 * - onSearch (function): A callback function to handle the search action.
 */
function SearchBar({ onSearch }) {
  const [query, setQuery] = useState(''); // State to manage the search query
  const { darkMode } = useContext(ThemeContext); // Retrieve darkMode value from ThemeContext

  /**
   * Handles input change event
   * Updates the query state with the input value.
   */
  const handleInputChange = (e) => {
    setQuery(e.target.value);
  };

  /**
   * Handles search button click event
   * Calls the onSearch function with the current query.
   */
  const handleSearch = () => {
    onSearch(query);
  };

  /**
   * Handles key press event in the input field
   * Initiates search when the Enter key is pressed.
   */
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
