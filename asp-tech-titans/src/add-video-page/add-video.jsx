import React, { useState, useContext } from 'react';
import './add-video.css';
import { ReactComponent as ImageIcon } from '../images/addimage.svg';
import { VideoContext } from '../contexts/videoContext'; // Import the VideoContext
import { VideoDataContext } from '../contexts/videoDataContext';
import { useNavigate } from 'react-router-dom';

export const AddVideo = () => {
    const [image, setImage] = useState(null);
    const { videos } = useContext(VideoContext);
    const mostRecentVideo = videos.length > 0 ? videos[videos.length - 1] : null;
    const navigate = useNavigate();
    const { addVideoData } = useContext(VideoDataContext);

    const handleImageUpload = (event) => {
        setImage(URL.createObjectURL(event.target.files[0]));
    };

    


    const handleSubmit = (event) => {
        const newData = {
            title: document.getElementById("title").value,
            description: document.getElementById("description").value,
            tags: document.getElementById("tags").value,
            playlist: document.getElementById("category").value,
            thumbnail: image
        };
        addVideoData(newData);
        alert("Upload successful");
        navigate('/mainPage');
    }


    return (
        <div className='addpage-warpper'>
        <div className="containerAVPAddpage">
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

                <textarea 
                    className="tags-container-addpage"
                    placeholder="Enter tags"
                    id="tags"
                    name="tags"
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
                    <div className="thumbnail">
                    <h3>thumbnail:</h3>
                        {!image && <ImageIcon className="ImageIcon" />}
                        <input type="file" onChange={handleImageUpload} id="image" name="image" accept="image/*" style={{ display: 'none' }} />
                        <div>{image && <img src={image} alt="User uploaded" className="image-preview" />}</div>
                    </div>
                </label>
            </div>
            
            <div className="media-container">
                <label htmlFor="videoUpload">
                    <div className="addVideo">
                        {mostRecentVideo ? (<div> <video src={mostRecentVideo.url} controls width="300" /></div>) : (<p>No videos available</p>)}
                    </div>
                </label>
            </div>
            <button className="Upload-button" onClick={handleSubmit}>Upload</button>
        </div>
        </div>
    );
}

export default AddVideo;
