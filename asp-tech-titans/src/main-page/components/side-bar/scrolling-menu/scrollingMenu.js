import React, { useState, useContext } from 'react';
import './scrollingMenu.css';
import homeIcon from '../../../../db/icons/home-icon.svg';
import forYouIcon from '../../../../db/icons/for-you-icon.svg';
import subscriptions from '../../../../db/icons/subscription-icon.svg';
import yourChannel from '../../../../db/icons/user-channel.svg';
import history from '../../../../db/icons/order-history-icon.svg';
import playlists from '../../../../db/icons/playlist-svgrepo-com.svg';
import watchLater from '../../../../db/icons/watch-later-icon.svg';
import likedVideos from '../../../../db/icons/like-icon.svg';
import music from '../../../../db/icons/headphone-icon.svg';
import trending from '../../../../db/icons/fast-speed-icon.svg';
import gaming from '../../../../db/icons/gamepad-icon.svg';
import news from '../../../../db/icons/newspaper-icon.svg';
import podcast from '../../../../db/icons/podcast-icon.svg';
import settings from '../../../../db/icons/setting-line-icon.svg';
import report from '../../../../db/icons/comment-blog-icon.svg';
import help from '../../../../db/icons/question-mark-circle-outline-icon.svg';
import feedback from '../../../../db/icons/pencil-icon.svg';
import techTitansLogo from '../../../../db/techTitansLogo.png';
import { Link, useNavigate } from 'react-router-dom';
import { ThemeContext } from '../../../../contexts/themeContext';


const ScrollingMenu = ({ isOpen, toggleMenu }) => {
  const { darkMode } = useContext(ThemeContext);
  return (
    <>
      <div className={`overlay ${isOpen ? 'show' : ''}`} onClick={toggleMenu}></div>
      <div className={`scrolling-menu ${isOpen ? 'open' : ''} ${darkMode ? 'dark-mode' : ''}`}>
        <div className="logo-container-sidebar">
          <img src={techTitansLogo} alt="Logo" className='logo-sidebar-img'/>
        </div>
        <div className="scrollable-menu">
          <ul className="list-group list-group-flush">
            <Link to="/mainPage"><li className="list-group-item"><img src={homeIcon} alt="Home" /> Home</li></Link>
            <li className="list-group-item"><img src={subscriptions} alt="Subscriptions" /> Subscriptions</li>
            <li className="list-group-item"><img src={forYouIcon} alt="For You" /> For you</li>
            <div className="divider"></div>
            <div className="categories-label">You</div>
            <li className="list-group-item"><img src={yourChannel} alt="Home" />Your channel</li>
            <li className="list-group-item"><img src={history} alt="Home" />History</li>
            <li className="list-group-item"><img src={playlists} alt="Home" />Playlists</li>
            <li className="list-group-item"><img src={watchLater} alt="Home" />Watch later</li>
            <li className="list-group-item"><img src={likedVideos} alt="Home" />Liked videos</li>
            <div className="divider"></div>
            <div className="categories-label">Subscriptions</div>
            <li className="list-group-item no-icon">Tech love</li>
            <li className="list-group-item no-icon">Food Porn</li>
            <li className="list-group-item no-icon">Niv Gilboa</li>
            <li className="list-group-item no-icon">Confusius Junior</li>
            <li className="list-group-item no-icon">Lonley peleg</li>
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
    </>
  );
};

export default ScrollingMenu;
