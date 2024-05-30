import React from 'react';
import Comment from './comment';
import './comments.css';


function Comments({ comments }) {
    return (
        <div className="comments">
            {/* <h1>Comments</h1>
            {comments.map((comment) => {
                return <Comment {...{Comment}} />
            })} */}
        </div>
    );
}

export default Comments;    
