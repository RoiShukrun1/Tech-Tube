import React from 'react';
import Comment from './comment';
import './comments.css';
import AddComment from './addComment';

function Comments({ comments, currentVideoId, currentUser,
    setVideos, inputValue, setInputValue, isFocused, setIsFocused, login }) {

    return (
        <div>
            {login && (
                <AddComment
                    inputValue={inputValue}
                    setInputValue={setInputValue}
                    isFocused={isFocused}
                    setIsFocused={setIsFocused}
                    currentUser={currentUser}
                    comments={comments}
                    setVideos={setVideos}
                    currentVideoId={currentVideoId}
                />
            )}
            <h1 className="headline">{comments.length} Comments</h1>
            {comments.map((comment) => (
                <Comment
                    commentObj={comment}
                    setVideos={setVideos}
                    currentVideoId={currentVideoId}
                    currentUser={currentUser}
                    key={comment.id}
                />
            ))}
        </div>
    );
}

export default Comments;
