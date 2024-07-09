import React, { useState, useEffect } from 'react';
import './publisherInfo.css';
import SubscribeButton from '../../../video-watch-page/components/video-info/subscribeButton';
import addBannerIcon from '../../../images/addimage.svg';

const PublisherInfo = ({
  nickname,
  username,
  subscribers,
  videos,
  banner,
  image,
  currentUser,
  setUsers
}) => {
  const [imageState, setImageState] = useState(null);
  const [base64Image, setBase64Image] = useState(null);

  useEffect(() => {
    fetch(addBannerIcon)
      .then((response) => response.blob())
      .then((blob) => {
        const reader = new FileReader();
        reader.onloadend = () => {
          setBase64Image(reader.result);
        };
        reader.readAsDataURL(blob);
      })
      .catch((error) => console.error('Error converting image to base64:', error));
  }, []);

  const handleImageUpload = async (event) => {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.onloadend = async () => {
      const base64String = reader.result;
      setImageState(base64String);

      // Send the banner to the server
      const response = await fetch(`/users/${username}`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ banner: base64String })
      });

      if (response.ok) {
        const updatedUser = await response.json();
        // Update the user's banner in the state or handle the response as needed
        console.log('Banner updated successfully:', updatedUser);
      } else {
        console.error('Failed to update banner');
      }
    };
    reader.readAsDataURL(file);
  };

  const publisherImage = image;
  const publisherBanner = banner || imageState || base64Image;

  return (
    <div className="publisher-info-div-pcp">
      <div className="publisher-banner-pcp">
        <img className="publisher-banner-image-pcp" src={publisherBanner} alt="publisherBanner" />
        {currentUser?.username === username && (
          <label htmlFor="banner-upload" className="upload-banner-label">
            <input 
              type="file" 
              id="banner-upload" 
              name="banner-upload" 
              accept="image/*" 
              onChange={handleImageUpload} 
            />
            <span className="upload-banner-text">Upload Banner</span>
          </label>
        )}
      </div>
      <div className="publisher-content">
        <div className="publisher-image-div-pcp">
          <img className="publisher-image-pcp" src={publisherImage} alt="publisherImage" />
        </div>
        <div className="publisher-info">
          <p className='publisher-info-p1'>{username || "Not Available"}</p>
          <p className='publisher-info-p2'>@{nickname || "Not Available"} • {subscribers} subscribers • {videos} videos</p>
          <div className="sub-pcp">
            <SubscribeButton setUsers={setUsers} currentUser={currentUser} publisher={username} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default PublisherInfo;
