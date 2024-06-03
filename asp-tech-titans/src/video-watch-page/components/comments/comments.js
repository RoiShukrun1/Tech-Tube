import React from 'react';
import Comment from './comment';
import './comments.css';
import { useState } from 'react';



function Comments({ comments, currentUserPhoto }) {

    const [isFocused, setIsFocused] = useState(false);

    const handleFocus = () => {
        setIsFocused(true);
    };

    // const [nuberOfComments, setNumberOfComments] = useState(comments.length);
    return (
        <div>

            <div className="row">
                <div className="col-1">
                <img className="circle-image" src={currentUserPhoto} style={{ marginLeft: '35%' }} />
                </div>
                <div className="col">
                <input type="text" className="addComment" placeholder="Add a comment" onFocus={handleFocus} />
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
