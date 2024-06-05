import React from 'react';
import './header.css';
import SearchBar from './search-bar/searchBar';
import HeaderButtons from './header-buttons/headerButtons';

function Header() {
  return (
    <header className="main-page-header">
      <div className="flex-grow-1 mx-3">
        <SearchBar />
      </div>
      <HeaderButtons />
    </header>
  );
}

export default Header;

