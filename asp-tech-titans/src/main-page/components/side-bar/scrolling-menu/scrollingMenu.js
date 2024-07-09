import React, { useContext, useState} from 'react';
import './scrollingMenu.css';
import homeIcon from '../../../../db/icons/home-icon.svg';
import forYouIcon from '../../../../db/icons/for-you-icon.svg';
import subscriptions from '../../../../db/icons/subscription-icon.svg';
import yourChannel from '../../../../db/icons/user-channel.svg';
import history from '../../../../db/icons/order-history-icon.svg';
import playlists from '../../../../db/icons/playlist-svgrepo-com.svg';
import watchLater from '../../../../db/icons/watch-later-icon.svg';
import likedVideos from '../../../../db/icons/like-icon.svg';
import settings from '../../../../db/icons/setting-line-icon.svg';
import report from '../../../../db/icons/comment-blog-icon.svg';
import help from '../../../../db/icons/question-mark-circle-outline-icon.svg';
import feedback from '../../../../db/icons/pencil-icon.svg';
import techTitansLogo from '../../../../db/techTitansLogo.png';
import techTitansLogoDM from '../../../../db/techTitansLogoDM.png';
import { CurrentPublisherContext } from '../../../../publisher-chanel-page/currentPublisherContext';
import { ThemeContext } from '../../../../contexts/themeContext';
import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';
import axios from 'axios';


/**
 * ScrollingMenu Component
 * 
 * This component renders a scrolling sidebar menu with various navigation options.
 * It uses ThemeContext to adjust its styles based on the current theme (dark or light mode).
 * The menu can be toggled open or closed.
 * 
 * Props:
 * - isOpen (boolean): Indicates whether the menu is open or closed.
 * - toggleMenu (function): A function to toggle the menu open or closed.
 */
const ScrollingMenu = ({ isOpen, toggleMenu }) => {
  const { darkMode } = useContext(ThemeContext);
  const navigate = useNavigate();
  const [loggedInuser, setLoggedInUser] = useState(null); // State to manage the logged-in user
  const { setPublisher } = useContext(CurrentPublisherContext);


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
    try {
      const response = await axios.delete(`http://localhost:80/api/users/${loggedInuser.username}`);
      if (response.status === 200) {
        alert('User deleted successfully');
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
    <>
      <div className={`overlay ${isOpen ? 'show' : ''}`} onClick={toggleMenu}></div>
      <div className={`scrolling-menu ${isOpen ? 'open' : ''} ${darkMode ? 'dark-mode' : ''}`}>
        <div className="logo-container-sidebar">
          <img src={darkMode ? techTitansLogoDM : techTitansLogo} alt="Logo" className='logo-sidebar-img'/>
        </div>
        <div className="scrollable-menu">
          <ul className="list-group list-group-flush">
          <li className="list-group-item" onClick={() =>handleHomeClick()}><img src={homeIcon} alt="Home" /> Home</li>
          <li className="list-group-item" onClick={() =>handleYourChannelClick()}><img src={yourChannel} alt="Your Channel" /> Your Channel</li>
          <li className="list-group-item" ><img src={forYouIcon} alt="For You" /> For You</li>
            <div className="divider"></div>
            <div className="categories-label">You</div>
            <li className="list-group-item"><img src={subscriptions} alt="Subscriptions" /> Subscriptions</li>
            <li className="list-group-item"><img src={history} alt="Home" />History</li>
            <li className="list-group-item"><img src={playlists} alt="Home" />Playlists</li>
            <li className="list-group-item"><img src={watchLater} alt="Home" />Watch later</li>
            <li className="list-group-item"><img src={likedVideos} alt="Home" />Liked videos</li>
            <li className="list-group-item" onClick={() =>handleDeleteUserClick()} ><img src={feedback} alt="Home" />Delete user </li>           
            <div className="divider"></div>
            <div className="categories-label">Subscriptions</div>
            <li className="list-group-item no-icon">Tech love</li>
            <li className="list-group-item no-icon">Food Porn</li>
            <li className="list-group-item no-icon">Niv Gilboa</li>
            <li className="list-group-item no-icon">Confusius</li>
            <li className="list-group-item no-icon">Lonley peleg</li>      
            <div className="divider"></div>
            <div className="categories-label">More</div>
            <li className="list-group-item"><img src={settings} alt="Home" />Settings</li>
            <li className="list-group-item"><img src={report} alt="Home" />Report history</li>
            <li className="list-group-item"><img src={help} alt="Home" />Help</li>
            <li className="list-group-item"><img src={feedback} alt="Home" />Feedback</li>
            </ul>
        </div>
      </div>
    </>
  );
};

export default ScrollingMenu;
