import React from 'react';
import './header.css';
import SearchBar from './search-bar/searchBar';
import HeaderButtons from './header-buttons/headerButtons';

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
