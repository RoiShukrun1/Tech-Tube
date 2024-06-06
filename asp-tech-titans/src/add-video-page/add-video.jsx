import React, { useState } from 'react';
import './add-video.css';
import { ReactComponent as AddVideoIcon } from '../images/addVideo.svg';
import { ReactComponent as ImageIcon } from '../images/addimage.svg'; // Replace '../images/image.svg' with the path to your image icon
export const AddVideo = () => {

    const [image, setImage] = useState(null);
    const handleImageUpload = (event) => {
        setImage(URL.createObjectURL(event.target.files[0]));
      }
    const [video, setVideo] = useState(null);
    const handleVideoUpload = (event) => {
        setVideo(URL.createObjectURL(event.target.files[0]));
        }

    return (
        <div className="container">
            <form>
            <h1>Details:</h1>
            <textarea 
            className = "title-container"
            placeholder="Enter your title"
            id="title"
            name="videoTitle">
            </textarea>

            <textarea 
            className = "description-container"
            placeholder="Enter your description"
            id="descripton" 
            name="videoDescription">
            </textarea>

            <textarea 
            className = "tags-container"
            placeholder="Enter tags"
            id="tags"
            name="tags">
            </textarea>

            <label htmlFor="videoUpload">
            <div className="addVideo">
            {!video && <AddVideoIcon className="addicon" />}
            <input type="file" onChange={handleVideoUpload} id="videoUpload" name="videoUpload" accept="video/mp4" style={{display: 'none'}} />
            <div>{video && <video src={video} alt="User uploaded "style={{width: '900px', height: '500px'}} />}</div>
            </div>
            </label>

            <label htmlFor="image" className="thumbnail-label">
                <div className="thumbnail">
                    <p>Thumbnail:</p>
                    {!image && <ImageIcon className="ImageIcon" />}
                    <input type="file" onChange={handleImageUpload} id="image" name="image" accept="image/*" style={{display: 'none'}} />
                    <div>{image && <img src={image} alt="User uploaded "style={{width: '150px', height: '100px'}} />}</div>
                </div>
            </label>
            </form>
        </div>

    );
}
export default AddVideo;