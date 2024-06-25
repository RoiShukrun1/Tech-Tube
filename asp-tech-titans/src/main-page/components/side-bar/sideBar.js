import React, { useContext, useState } from 'react';
import './sideBar.css';
import homeIcon from '../../../db/icons/home-icon.svg';
import forYouIcon from '../../../db/icons/for-you-icon.svg';
import Subscriptions from '../../../db/icons/subscription-icon.svg';
import ScrollingMenuButton from './scrolling-menu-button/scrollingMenuButton';
import ScrollingMenu from './scrolling-menu/scrollingMenu';
import music from '../../../db/icons/headphone-icon.svg';
import trending from '../../../db/icons/fast-speed-icon.svg';
import gaming from '../../../db/icons/gamepad-icon.svg';
import news from '../../../db/icons/newspaper-icon.svg';
import podcast from '../../../db/icons/podcast-icon.svg';
import settings from '../../../db/icons/setting-line-icon.svg';
import report from '../../../db/icons/comment-blog-icon.svg';
import help from '../../../db/icons/question-mark-circle-outline-icon.svg';
import feedback from '../../../db/icons/pencil-icon.svg';
import techTitansLogo from '../../../db/techTitansLogo.png';
import techTitansLogoDM from '../../../db/techTitansLogoDM.png';
import { Link, useNavigate } from 'react-router-dom';
import { ThemeContext } from '../../../contexts/themeContext';

/**
 * Sidebar Component
 * 
 * This component renders the sidebar menu with various navigation options. It includes a button to toggle a scrolling menu.
 * 
 * The sidebar adapts its appearance based on the current theme (dark or light mode).
 */
function Sidebar() {
  const [isMenuOpen, setIsMenuOpen] = useState(false); // State to manage the menu's open/closed status
  const { darkMode } = useContext(ThemeContext); // Retrieve the darkMode value from ThemeContext

  /**
   * Toggle the menu's open/closed status
   */
  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  return (
    <div>
      <ScrollingMenuButton isOpen={isMenuOpen} toggleMenu={toggleMenu} />
      <ScrollingMenu isOpen={isMenuOpen} toggleMenu={toggleMenu} />
      <div className={`sidebar ${darkMode ? 'dark-mode' : ''}`} id="sidebar-wrapper">
        <div className="logo-container-sidebar">
          <img src={darkMode ? techTitansLogoDM : techTitansLogo} alt="Logo" className='logo-sidebar-img'/>
        </div>
        <div className="scrollable-menu">
          <ul className="list-group list-group-flush">
            <Link to="/mainPage"><li className="list-group-item"><img src={homeIcon} alt="Home" /> Home</li></Link>
            <li className="list-group-item"><img src={Subscriptions} alt="Subscriptions" /> Subscriptions</li>
            <li className="list-group-item"><img src={forYouIcon} alt="For You" /> For you</li>
            <div className="divider"></div>
            <div className="categories-label">Categories</div>
            <li className="list-group-item no-icon">Sitcoms</li>
            <li className="list-group-item no-icon">Music</li>
            <li className="list-group-item no-icon">Restaurants</li>
            <li className="list-group-item no-icon">Gaming</li>
            <li className="list-group-item no-icon">Mixes</li>
            <li className="list-group-item no-icon">Computers</li>
            <li className="list-group-item no-icon">Satire</li>
            <li className="list-group-item no-icon">News</li>
            <li className="list-group-item no-icon">Tech House</li>
            <li className="list-group-item no-icon">Playlists</li>
            <li className="list-group-item no-icon">History</li>
            <div className="divider"></div>
            <div className="categories-label">Explore</div>
            <li className="list-group-item"><img src={music} alt="Home" />Music</li>
            <li className="list-group-item"><img src={trending} alt="Home" />Trending</li>
            <li className="list-group-item"><img src={gaming} alt="Home" />Gaming</li>
            <li className="list-group-item"><img src={news} alt="Home" />News</li>
            <li className="list-group-item"><img src={podcast} alt="Home" />Podcast</li>
            <div className="divider"></div>
            <li className="list-group-item"><img src={settings} alt="Home" />Settings</li>
            <li className="list-group-item"><img src={report} alt="Home" />Report history</li>
            <li className="list-group-item"><img src={help} alt="Home" />Help</li>
            <li className="list-group-item"><img src={feedback} alt="Home" />Send feedback</li>        
             </ul>
        </div>
      </div>
    </div>
  );
}

export default Sidebar;
