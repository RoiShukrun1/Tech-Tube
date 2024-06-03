import React from 'react';
import Comment from './comment';
import './comments.css';
import { useState } from 'react';

function Comments({ comments, currentUserPhoto }) {

    const [nuberOfComments, setNumberOfComments] = useState(comments.length);
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

    return (
        <div>

            <div className="row">
                <div className="col-1">
                    <img className="circle-image" src={currentUserPhoto} style={{ marginLeft: '35%' }} />
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
                    {isFocused && <button type="button" className="btn btn-light comment-add-button">comment</button>}
                </div>
            </div>


            <h1 className="headline">{nuberOfComments} Comments</h1>
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
