import React, { useState, useEffect } from 'react';
import './publisherInfo.css';
import SubscribeButton from '../../../video-watch-page/components/video-info/subscribeButton';
import addBannerIcon from './defaultBanner.png';
import axios from 'axios';

const serverBaseUrl = 'http://localhost:80';

const PublisherInfo = ({ nickname, username, subscribers, videos, banner, image, setUsers }) => {
    const [base64Image, setBase64Image] = useState(null);
    const [currentUser, setLogin] = useState(null);
    const [uploadedImage, setUploadedImage] = useState(null);
    const [bannerImageUrl, setBannerImageUrl] = useState(null);
    const publisherBanner = uploadedImage || bannerImageUrl || base64Image;

    // Check authentication and set login state
    useEffect(() => {
        const checkAuth = async () => {
            try {
                const response = await axios.get('http://localhost/api/token/checkAuth', { withCredentials: true });
                if (response.data.isAuthenticated) {
                    setLogin(response.data.user);
                }
            } catch (error) {
                alert("Error checking authentication.");
            }
        };

        checkAuth();
    }, []);

    useEffect(() => {
        const checkBannerImage = async () => {
            try {
                const response = await axios.get(`${serverBaseUrl}/uploads/bannerPictures/${username}.png`);
                if (response.status === 200) {
                    setBannerImageUrl(`${serverBaseUrl}/uploads/bannerPictures/${username}.png`);
                } else {
                    throw new Error('Banner image not found');
                }
            } catch (error) {
                fetch(addBannerIcon)
                    .then((response) => response.blob())
                    .then((blob) => {
                        const reader = new FileReader();
                        reader.onloadend = () => {
                            setBase64Image(reader.result);
                        };
                        if (blob) {
                        reader.readAsDataURL(blob); 
                    }
                    })
                    .catch((error) => console.error('Error converting image to base64:', error));
            }
        };

        checkBannerImage();
    }, [username]);

    const handleImageUpload = (event) => {
        const file = event.target.files[0];
        const reader = new FileReader();
        reader.onloadend = () => {
            setUploadedImage(reader.result); // Store the uploaded image
        };
        if (file) {
        reader.readAsDataURL(file);
        }
    };

    const handleApplyBanner = async () => {
        try {
            const bannerPath = await axios.patch(`${serverBaseUrl}/api/users/users/banner`, { base64Image: uploadedImage, username });
            const response = await axios.patch(`http://localhost:80/api/users/${username}`, { banner: bannerPath.data.imagePath });
            if (response.status === 200) {
                setBannerImageUrl(`${serverBaseUrl}/uploads/bannerPictures/${username}.png`);
                setUploadedImage(null); // Clear uploaded image after applying
                // window.location.reload();
                alert('Banner uploaded successfully');
            }
        } catch (error) {
            alert('Uploading banner failed');
        }
    };
    const publisherImage = image;
    return (
        <div className="publisher-info-div-pcp">
            <div className="publisher-banner-pcp">
                <img className="publisher-banner-image-pcp" src={publisherBanner} alt="publisherBanner" />
                {currentUser && currentUser.username === username && (
                    <>
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
                        {uploadedImage && (
                            <button className="upload-banner-button" onClick={handleApplyBanner}>
                                Apply
                            </button>
                        )}
                    </>
                )}
            </div>
            <div className="publisher-content">
                <div className="publisher-image-div-pcp">
                    <img className="publisher-image-pcp" src={publisherImage} alt="publisherImage" />
                </div>
                <div className="publisher-info">
                    <p className='publisher-info-p1'>{username}</p>
                    <p className='publisher-info-p2'>@{nickname} • {subscribers} subscribers • {videos} videos</p>
                    <div className="sub-pcp">
                        <SubscribeButton
                            setUsers={setUsers} currentUser={currentUser} publisher={username} />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default PublisherInfo;
