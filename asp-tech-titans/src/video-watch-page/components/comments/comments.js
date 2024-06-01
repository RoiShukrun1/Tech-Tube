import React from 'react';
import Comment from './comment'; // Make sure this path is correct
import './comments.css';
import { useState } from 'react';

function getNumberOfComments(comments) {
    return comments.length;
}

function Comments({ comments }) { // Default to an empty array if comments is undefined

    const [nuberOfComments, setNumberOfComments] = useState(getNumberOfComments(comments));

    return (
        <div className="comments">
            <h1>{nuberOfComments} Comments</h1>
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
