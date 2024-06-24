import React, { useState, useContext } from 'react';
import './add-video.css';
import { ReactComponent as ImageIcon } from '../images/addimage.svg';
import { VideoContext } from '../contexts/videoContext'; // Import the VideoContext
import { VideoDataContext } from '../contexts/videoDataContext';
import { useNavigate } from 'react-router-dom';
import { LoginContext } from '../contexts/loginContext';
import { Link } from 'react-router-dom';
import { ThemeContext } from '../contexts/themeContext';

export const AddVideo = () => {
    const [image, setImage] = useState(null);
    const { videoList } = useContext(VideoContext);
    const mostRecentVideo = videoList.length > 0 ? videoList[videoList.length - 1] : null;
    const navigate = useNavigate();
    const { addVideoData } = useContext(VideoDataContext);
    const { login } = useContext(LoginContext);
    const { darkMode } = useContext(ThemeContext);

    const handleImageUpload = (event) => {
        setImage(URL.createObjectURL(event.target.files[0]));
    };

    const handleSubmit = (event) => {
        const newData = {
            id: videoList.length + 10,
            videoUploaded: mostRecentVideo.url,
            thumbnail: image,
            title: document.getElementById("title").value,
            publisher: login.nickname,
            publisherImage: login.image,
            views: 0,
            date: new Date().toLocaleDateString(),
            description: document.getElementById("description").value,
            relatedVideos: [{"id":1}, {"id":2}, {"id":3}, {"id":4},{"id":5}, {"id":6}, {"id":7}, {"id":8}, {"id":9}, {"id":10}],
            usersLikes: [],
            usersUnlikes: [],
            playlist: document.getElementById("category").value,
            comments: []
        };
        addVideoData(newData);
        alert("Upload successful");
        navigate('/mainPage');
    }


    return (
        <div className= {'addpage-warpper'+ (darkMode ? '-dark' : '')}>
        <div className={ "containerAVPAddpage"+ (darkMode ? '-dark' : '')}>
            <div>
                <h1>Details:</h1>
                <textarea 
                    className="title-container-addpage"
                    placeholder="Enter your title"
                    id="title"
                    name="videoTitle"
                ></textarea>

                <textarea 
                    className="description-container-addpage"
                    placeholder="Enter your description"
                    id="description"
                    name="videoDescription"
                ></textarea>

                <div className="category-container">
                    <h2>Playlist:</h2>
                    <select name="category" id="category">
                        <option value="category">Select playlist</option>
                        <option value="category">Music</option>
                        <option value="category">Sport</option>
                        <option value="category">Study</option>
                    </select>
                </div>
                <label htmlFor="image" className="thumbnail-label">
                    <div>
                    <h3>Thumbnail:</h3>
                        {!image && <ImageIcon className="ImageIcon" />}
                        <input type="file" onChange={handleImageUpload} id="image" name="image" accept="image/*" style={{ display: 'none' }} />
                        <div>{image && <img src={image} alt="User uploaded" className="image-preview" />}</div>
                    </div>
                </label>
            </div>
            
            <div className="media-container">
                <label htmlFor="videoUpload">
                    <div className="addVideo">
                        {mostRecentVideo ? (<div> <video src={mostRecentVideo.url} controls className='videoPreviewAddpage' /></div>) : (<p>No videos available</p>)}
                    </div>
                </label>
            </div>
            <button className="upload-button-addpage" onClick={handleSubmit}>Upload</button>
            <Link to="/uploadPage">
            <button className="back-button"type="button">Back</button>
            </Link>
        </div>
        </div>
    );
}

export default AddVideo;
