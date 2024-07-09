import React, { useState, useContext } from 'react';
import './add-video.css';
import { ReactComponent as ImageIcon } from '../images/addimage.svg';
import { VideoContext } from '../contexts/videoContext';
import { VideoDataContext } from '../contexts/videoDataContext';
import { useNavigate } from 'react-router-dom';
import { LoginContext } from '../contexts/loginContext';
import { Link } from 'react-router-dom';
import { ThemeContext } from '../contexts/themeContext';
import axios from 'axios';

export const AddVideo = () => {
    const [image, setImage] = useState(null);
    const { videoList } = useContext(VideoContext);
    const [base64Image, setBase64Image] = useState(null);
    const mostRecentVideo = videoList.length > 0 ? videoList[videoList.length - 1] : null;
    const navigate = useNavigate();
    const { login } = useContext(LoginContext);
    const { darkMode } = useContext(ThemeContext);

    const handleImageUpload = (event) => {
        setImage(URL.createObjectURL(event.target.files[0]));
        const file = event.target.files[0];
          const reader = new FileReader();
          reader.onloadend = () => {
              setBase64Image(reader.result); // Store the Base64 string
          };
          reader.readAsDataURL(file);
      }

    const handleSubmit = async (event) => {
        event.preventDefault();
        const title = document.getElementById("title").value;
        const description = document.getElementById("description").value;
        const playlist = document.getElementById("category").value;

        if (!title || !description || !playlist || !image) {
            alert("All fields are required.");
            return;
        }
        const response = await axios.get('http://localhost/api/videos/all');
        const formData = new FormData();
        formData.append('id', parseInt(response.data[response.data.length-1].id, 10) + 1);
        formData.append('videoUploaded', mostRecentVideo.file);
        formData.append('thumbnail', base64Image);
        formData.append('title', title);
        formData.append('publisher', login.username);
        formData.append('publisherImage', login.image);
        formData.append('views', 0);
        formData.append('date', new Date().toLocaleDateString());
        formData.append('description', description);
        formData.append('usersLikes', JSON.stringify([]));
        formData.append('usersUnlikes', JSON.stringify([]));
        formData.append('playlist', playlist);
        formData.append('comments', JSON.stringify([]));
        try {
            const response = await fetch('http://localhost/api/users/:id/videos', {
                method: 'POST',
                body: formData
            });
    
            if (response.ok) {
                const data = await response.json();
                console.log('Video uploaded successfully:', data);
            } else {
                const error = await response.json();
                console.error('Error uploading video:', error);
            }
        } catch (error) {
            console.error('Error uploading video:', error);
        }

        alert("Upload successful");
        navigate('/mainPage');
    }

    return (
        <div className={'addpage-warpper' + (darkMode ? '-dark' : '')}>
            <div className={"containerAVPAddpage" + (darkMode ? '-dark' : '')}>
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
                        <h2>Catgory:</h2>
                        <select name="category" id="category">
                            <option value="" disabled selected>Select video catagory</option>
                            <option value="music">Consoles & PC</option>
                            <option value="sport">Software and Apps</option>
                            <option value="study">Gadgets</option>
                            <option value="study">Any</option>

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
                    <button className="back-button" type="button">Back</button>
                </Link>
            </div>
        </div>
    );
}

export default AddVideo;
