import React from 'react';
import Comment from './comment'; // Make sure this path is correct
import './comments.css';

function Comments({ comments }) { // Default to an empty array if comments is undefined
    return (
        <div className="comments">
            <h1>Comments</h1>
            {comments.map((comment) => (
                <Comment 
                    username={comment.username}
                    date={comment.date}
                    comment={comment.comment}
                    likes={comment.likes}
                />
            ))}
        </div>
    );
}

export default Comments;
