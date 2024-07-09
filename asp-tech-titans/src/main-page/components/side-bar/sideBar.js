import React, { useContext, useState } from 'react';
import './sideBar.css';
import homeIcon from '../../../db/icons/home-icon.svg';
import forYouIcon from '../../../db/icons/for-you-icon.svg';
import yourChannel from '../../../db/icons/user-channel.svg';
import deleteIcon from '../../../db/icons/delete-button-icon.svg';
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
import { ThemeContext } from '../../../contexts/themeContext';
import { CurrentPublisherContext } from '../../../publisher-chanel-page/currentPublisherContext';
import axios from 'axios';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

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
  const { setPublisher } = useContext(CurrentPublisherContext);
  const navigate = useNavigate();
  const [loggedInuser, setLoggedInUser] = useState(null); // State to manage the logged-in user

  useEffect(() => {
    const checkAuth = async () => {
      try {
        const response = await axios.get('http://localhost/api/token/checkAuth', { withCredentials: true });
        if (response.data.isAuthenticated) {
          setLoggedInUser(response.data.user);
        }
      } catch (error) {
        alert("Error checking authentication.");
      }
    };
    
    checkAuth();
  }, []); 

  /**
   * Toggle the menu's open/closed status
   */
  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

   /**
   * Handle your channel click event
   * Redirects to the publisher channel page.
   */
   const handleYourChannelClick = () => {
    if(!loggedInuser){
      setPublisher(null);
      localStorage.setItem('publisher', null);
      alert('You must login to enter your channel!');
      return;
    } else {
    localStorage.setItem('publisher', loggedInuser.username);
    setPublisher(loggedInuser.username);
    navigate('/publisherChannel', { state: { refresh: Date.now() } });
  }
  };

  const handleDeleteUserClick = async (e) => {
    if (!loggedInuser) {
      alert('You must login to delete your account!');
      return;
    }
    try {
      const response = await axios.delete(`http://localhost:80/api/users/${loggedInuser.username}`);
      if (response.status === 200) {
        alert('User deleted successfully');
        setLoggedInUser(null);
        navigate('/mainPage', { state: { refresh: Date.now() } });
      }
    } catch (error) {
      alert('Error deleting user');
    }
  };

  const handleHomeClick = () => {
    navigate('/mainPage', { state: { refresh: true } });
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
            <li className="list-group-item" onClick={() =>handleHomeClick()}><img src={homeIcon} alt="Home" /> Home</li>
            <li className="list-group-item" onClick={() =>handleYourChannelClick()}><img src={yourChannel} alt="Your Channel" /> Your Channel</li>
            <li className="list-group-item" ><img src={forYouIcon} alt="For You" /> For You</li>
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
            <div className="categories-label">More</div>
            <li className="list-group-item"><img src={settings} alt="Home" />Settings</li>
            <li className="list-group-item"><img src={report} alt="Home" />Report history</li>
            <li className="list-group-item"><img src={help} alt="Home" />Help</li>
            <li className="list-group-item"><img src={feedback} alt="Home" />Feedback</li>
            <li className="list-group-item" onClick={() =>handleDeleteUserClick()} ><img src={deleteIcon} alt="Home" />Delete user </li>        
             </ul>
        </div>
      </div>
    </div>
  );
}

export default Sidebar;
