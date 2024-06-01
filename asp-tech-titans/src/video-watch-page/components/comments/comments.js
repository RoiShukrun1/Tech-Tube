import React from 'react';
import Comment from './comment';
import './comments.css';
// import { useState } from 'react';

function Comments({ comments }) { 

    // const [nuberOfComments, setNumberOfComments] = useState(comments.length);
    return (
        <div className="comments">
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
