import React from 'react';
import './header.css';
import SearchBar from './search-bar/searchBar';
import HeaderButtons from './header-buttons/headerButtons';

/**
 * Header Component
 * 
 * This component renders the main header of the page, which includes a search bar and a set of header buttons.
 * 
 * Props:
 * - onSearch (function): A callback function to handle the search action. This function is passed down to the SearchBar component.
 */
function Header({ onSearch }) {
  return (
    <header className="main-page-header">
      <SearchBar onSearch={onSearch} />
      <div className='header-buttons-place'>
        <HeaderButtons />
      </div>
    </header>
  );
}

export default Header;
