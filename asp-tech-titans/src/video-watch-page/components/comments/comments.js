import React from 'react';
import Comment from './comment';
import './comments.css';
import { useState } from 'react';

function Comments({ comments, currentVideoId, currentUser, setVideos }) {

    const [isFocused, setIsFocused] = useState(false);
    const [inputValue, setInputValue] = useState('');

    // Function to handle changes in the input field
    const handleInputChange = (event) => {
        setInputValue(event.target.value);
        console.log(inputValue);
    };

    const handleFocus = () => {
        setIsFocused(true);
    };

    const inputIsEmpty = () => { return inputValue === '' ? true : false; };

    const submitComment = () => {
        const newComment = {
            id: comments.length + 1,
            username: currentUser.username,
            image: currentUser.profilePicture,
            date: new Date().toLocaleString(),
            comment: inputValue,
            likes: 0,
        };

        setVideos(prevVideos => {
            const updatedVideos = [...prevVideos];

            const thisVideo = updatedVideos.find(video => video.id === currentVideoId);

            thisVideo.comments.unshift(newComment);

            return updatedVideos;
        });
    };

    return (
        <div>

            <div className="row">
                <div className="col-1">
                    <img className="circle-image" src={currentUser.profilePicture} style={{ marginLeft: '35%' }} />
                </div>
                <div className="col">
                    <input type="text"
                        className="addComment"
                        placeholder="Add a comment"
                        onFocus={handleFocus}
                        onChange={handleInputChange}
                    />
                    {isFocused && <button
                        onClick={() => setIsFocused(false)}
                        type="button"
                        className="btn btn-light comment-add-button">cancel
                    </button>}
                    {isFocused &&
                        <button type="button"
                            className="btn btn-light comment-add-button"
                            onClick={submitComment}
                        >comment</button>}
                </div>
            </div>


            <h1 className="headline">{comments.length} Comments</h1>
            {comments.map((comment) => (
                <Comment
                    username={comment.username}
                    date={comment.date}
                    comment={comment.comment}
                    likes={comment.likes}
                    profilePicture={comment.image}
                    key={comment.id}
                />
            ))}
        </div>
    );
}

export default Comments;
