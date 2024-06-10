import React, { useState } from 'react';
import './add-video.css';
import { ReactComponent as AddVideoIcon } from '../images/addVideo.svg';
import { ReactComponent as ImageIcon } from '../images/addimage.svg';

export const AddVideo = () => {
    const [image, setImage] = useState(null);
    const [video, setVideo] = useState(null);

    const handleImageUpload = (event) => {
        setImage(URL.createObjectURL(event.target.files[0]));
    };

    const handleVideoUpload = (event) => {
        setVideo(URL.createObjectURL(event.target.files[0]));
    };

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
                        {!video && <AddVideoIcon className="addicon" />}
                        <input type="file" onChange={handleVideoUpload} id="videoUpload" name="videoUpload" accept="video/mp4" style={{ display: 'none' }} />
                        <div>{video && <video src={video} controls className="video-preview" />}</div>
                    </div>
                </label>
            </div>
            <button className="Upload-button">Upload</button>
        </div>
        </div>
    );
}

export default AddVideo;
