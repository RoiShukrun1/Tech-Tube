import React from 'react';
import Comment from './comment';
import './comments.css';
// import { useState } from 'react';

const handleInputChange = () => {

};

function Comments({ comments }) {

    // const [nuberOfComments, setNumberOfComments] = useState(comments.length);



    return (
        <div className="comments">

                <div class="row">
                    <div class="col">
                        add a comment
                    </div>
                    <div class="col-9">

                    <input type="text" className="form-control" id="searchInput" onChange={handleInputChange} />
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
