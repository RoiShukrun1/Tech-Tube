import './comment.css';
import { ReactComponent as Like } from './like.svg';
import { ReactComponent as Dislike } from './dislike.svg';
import { ReactComponent as LikeSelected } from './like-selected.svg';
import { ReactComponent as DislikeSelected } from './dislike-selected.svg';
import { useState } from 'react';
import { ReactComponent as Pencil } from './pencil.svg';

function Comment({ commentObj, setVideos, currentVideoId, currentUser }) {

    var username = commentObj.username
    var date = commentObj.date
    var comment = commentObj.comment
    var profilePicture = commentObj.image
    var likesNumber = commentObj.likes

    const [commentInputValue, setCommentInputValue] = useState(comment);
    const [isPencilClicked, setIsPencilClicked] = useState(false);

    const handleCommentInputChange = (event) => {
        setCommentInputValue(event.target.value);
    };

    const handleKeyDown = (event) => {
        if (event.key === 'Enter') {
            closeInput();
        }
    };

    const closeInput = () => {
        setNewCommentContent(commentInputValue);
        setIsPencilClicked(false);
    };

    const setNewCommentContent = (newCommentContent) => {
        setVideos(prevVideos => {
            const updatedVideos = prevVideos.map(video => {
                if (video.id !== currentVideoId) return video;

                const updatedComments = video.comments.map(comment => {
                    if (comment.id !== commentObj.id) return comment;

                    return {
                        ...comment,
                        comment: newCommentContent
                    };
                });

                return {
                    ...video,
                    comments: updatedComments
                };
            });

            return updatedVideos;
        });
    };


    const userLike = () => {
        if (currentUser === null) return false;
        return commentObj.usersLikedId.some(user => user.id === currentUser.id);
    }

    const userUnLike = () => {
        if (currentUser === null) return false;
        return commentObj.usersUnLikedId.some(user => user.id === currentUser.id);
    }

    var isLiked = userLike();
    var isUnLiked = userUnLike();

    const setIsLiked = () => {
        setVideos(prevVideos => {
            const updatedVideos = prevVideos.map(video => {
                if (video.id !== currentVideoId) return video;

                return {
                    ...video,
                    comments: video.comments.map(comment => {
                        if (comment.id !== commentObj.id) return comment;

                        const userLikedIndex = comment.usersLikedId.findIndex(user => user.id === currentUser.id);

                        return {
                            ...comment,
                            usersLikedId: userLikedIndex !== -1
                                ? comment.usersLikedId.filter(user => user.id !== currentUser.id)
                                : [...comment.usersLikedId, { id: currentUser.id }]
                        };
                    })
                };
            });

            return updatedVideos;
        });
    };

    const setIsUnLiked = () => {
        setVideos(prevVideos => {
            const updatedVideos = prevVideos.map(video => {
                if (video.id !== currentVideoId) return video;

                return {
                    ...video,
                    comments: video.comments.map(comment => {
                        if (comment.id !== commentObj.id) return comment;

                        const userLikedIndex = comment.usersUnLikedId.findIndex(user => user.id === currentUser.id);

                        return {
                            ...comment,
                            usersUnLikedId: userLikedIndex !== -1
                                ? comment.usersUnLikedId.filter(user => user.id !== currentUser.id)
                                : [...comment.usersUnLikedId, { id: currentUser.id }]
                        };
                    })
                };
            });

            return updatedVideos;
        });
    };

    const increaseLikeNumber = () => {
        setVideos(prevVideos => {

            const updatedVideos = [...prevVideos];

            const video = updatedVideos.find(video => video.id === currentVideoId);

            if (video) {
                const commentIndex = video.comments.findIndex(comment => comment.id === commentObj.id);

                if (commentIndex !== -1) {

                    const updatedComment = { ...video.comments[commentIndex], likes: video.comments[commentIndex].likes + 1 };

                    const updatedComments = [...video.comments];
                    updatedComments[commentIndex] = updatedComment;

                    const updatedVideo = { ...video, comments: updatedComments };

                    const videoIndex = updatedVideos.findIndex(video => video.id === currentVideoId);

                    updatedVideos[videoIndex] = updatedVideo;
                }
            }

            return updatedVideos;
        });
    };

    const decreaseLikeNumber = () => {
        setVideos(prevVideos => {
            const updatedVideos = [...prevVideos];

            const video = updatedVideos.find(video => video.id === currentVideoId);

            if (video) {
                const commentIndex = video.comments.findIndex(comment => comment.id === commentObj.id);

                if (commentIndex !== -1) {
                    const updatedComment = {
                        ...video.comments[commentIndex],
                        likes: Math.max(video.comments[commentIndex].likes - 1, 0)
                    };

                    const updatedComments = [...video.comments];
                    updatedComments[commentIndex] = updatedComment;

                    const updatedVideo = { ...video, comments: updatedComments };
                    const videoIndex = updatedVideos.findIndex(video => video.id === currentVideoId);
                    updatedVideos[videoIndex] = updatedVideo;
                }
            }
            return updatedVideos;
        });
    };

    const currentUserIsOwnerOfComment = () => {
        return currentUser && currentUser.username === commentObj.username;
    }

    const handlePencilClicked = () => {
        setIsPencilClicked(true)
        setCommentInputValue(comment)
    }

    const deleteComment = () => {
        setVideos(prevVideos => {
            const updatedVideos = prevVideos.map(video => {
                if (video.id !== currentVideoId) return video;
    
                const updatedComments = video.comments.filter(comment => comment.id !== commentObj.id);
    
                return {
                    ...video,
                    comments: updatedComments
                };
            });
    
            return updatedVideos;
        });
    };
    

    return (
        <div className="border">
            <div className="row">
                <div className="col">
                    <img className="circle-image" alt="" src={profilePicture} />
                </div>
                <div className="col-11" style={{ padding: 0 }}>

                    <div className="comment">

                    <span className="usernameAndclosebutton">
                        <h2 className="username">@{username}</h2>
                        {currentUser && currentUserIsOwnerOfComment() && 
                        <button type="button" className="close-button btn btn-secondary" 
                        onClick={deleteComment}>X</button> }
                    </span>

                        <h3 className="date">{date}</h3>

                        <span className="inputWithComment">
                            <h3 className="content">{isPencilClicked ? <input
                                className='comment-input'
                                onChange={handleCommentInputChange}
                                placeholder='comment...'
                                onKeyDown={handleKeyDown}
                                value={commentInputValue}
                            /> : comment}</h3>

                            {currentUser && currentUserIsOwnerOfComment() &&
                                <button type="button" className='pencil-button' onClick={() => handlePencilClicked()}>
                                    <Pencil />
                                </button>
                            }

                        </span>

                        <div className="container">
                            <button onClick={() => {
                                if (currentUser === null) {
                                    alert('You must login to press like!');
                                    return
                                };
                                if (isUnLiked) {
                                    setIsUnLiked();
                                }
                                setIsLiked();
                                isLiked ? decreaseLikeNumber() : increaseLikeNumber();
                            }}
                                type="button"
                                className={isLiked ? "btn like-icon-button" :
                                    "btn btn-outline-secondary like-icon-button"}
                            >
                                {isLiked ? <LikeSelected className='icon' style={{ margin: 0 }} /> :
                                    <Like className='icon' style={{ margin: 0 }} />}
                            </button>

                            <h3 className="likesNumber">{likesNumber}</h3>

                            <button onClick={() => {
                                if (currentUser === null) {
                                    alert('You must login to press dislike!');
                                    return
                                };
                                if (isLiked) {
                                    setIsLiked();
                                }
                                setIsUnLiked();
                                if (isLiked) {
                                    decreaseLikeNumber();
                                }
                            }} type="button"

                                className={isUnLiked ? "btn like-icon-button" :
                                    "btn btn-outline-secondary like-icon-button"}>

                                {isUnLiked ? <DislikeSelected className='icon' style={{ margin: 0 }} /> :
                                    <Dislike className='icon' style={{ margin: 0 }} />}
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Comment;