import './comment.css';
import { ReactComponent as Like } from './like.svg';
import { ReactComponent as Dislike } from './dislike.svg';
import { ReactComponent as LikeSelected } from './like-selected.svg';
import { ReactComponent as DislikeSelected } from './dislike-selected.svg';
import { useState } from 'react';
import { ReactComponent as Pencil } from './pencil.svg';
import axios from 'axios';
import { useEffect } from 'react';

function Comment({ commentObj, setVideos, currentVideoId, currentUser }) {

    // set variables derived from commentObj
    var username = commentObj.username
    var date = commentObj.date
    var comment = commentObj.comment
    var profilePicture = commentObj.image
    var likesNumber = commentObj.likes

    const baseServerUrl = 'http://localhost';

    // set states
    const [commentInputValue, setCommentInputValue] = useState(comment);
    const [isPencilClicked, setIsPencilClicked] = useState(false);

    // state to turn useEffect request to server
    const [putSendToSrv, setPutSendToSrv] = useState(false);
    const [delSendToSrv, setDelSendToSrv] = useState(false);

    // handle input change
    const handleCommentInputChange = (event) => {
        setCommentInputValue(event.target.value);
    };

    // handle key down
    const handleKeyDown = (event) => {
        if (event.key === 'Enter') {
            closeInput();
        }
    };

    // close input
    const closeInput = () => {
        setNewCommentContent(commentInputValue);
        setIsPencilClicked(false);
    };

    useEffect(() => {
        const updateComment = async () => {
            if (currentUser === null) return;
            if (currentVideoId === null) return;
            if (commentObj === null) return;
            if (putSendToSrv === false) {
                return;
            };
            
            // This code will execute after videos state has been updated and rendered
            const path = 'http://localhost/api/users/' + currentUser.username +
                '/videos/' + currentVideoId + '/comments/' + commentObj.id;
            await axios.put(path, commentObj);
            setPutSendToSrv(false);
        };
        updateComment();
    }, [putSendToSrv]);

    useEffect(() => {
        const delComment = async () => {
            if (currentUser === null) return;
            if (currentVideoId === null) return;
            if (commentObj === null) return;
            if (delSendToSrv === false) {
                return;
            };
            
            // This code will execute after videos state has been updated and rendered
            const path = 'http://localhost/api/users/' + currentUser.username +
                '/videos/' + currentVideoId + '/comments/' + commentObj.id;
            const res = await axios.delete(path);
            console.log(res);
            
            setDelSendToSrv(false);
        };
        delComment();
    }, [delSendToSrv]);

    // set new comment content
    const setNewCommentContent = async (newCommentContent) => {

        await setVideos(prevVideos => {
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

        setPutSendToSrv(true);

    };

    // user like function
    const userLike = () => {
        if (currentUser === null) return false;
        return commentObj.usersLikedId.some(user => user.id === currentUser.id);
    }

    // user unlike function
    const userUnLike = () => {
        if (currentUser === null) return false;
        return commentObj.usersUnLikedId.some(user => user.id === currentUser.id);
    }

    // variables to check if user liked or unliked
    var isLiked = userLike();
    var isUnLiked = userUnLike();

    // set the comment as liked
    const setIsLiked = async () => {
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

        setPutSendToSrv(true);
    };

    // set the comment as unliked
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

        setPutSendToSrv(true);

    };

    // increase like number
    const increaseLikeNumber = async () => {
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

        setPutSendToSrv(true);

    };

    // decrease like number
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

        setPutSendToSrv(true);
    };

    // check if current user is owner of comment
    const currentUserIsOwnerOfComment = () => {
        return currentUser && currentUser.username === commentObj.username;
    }

    // handle pencil clicked
    const handlePencilClicked = () => {
        setIsPencilClicked(true)
        setCommentInputValue(comment)
    }

    // delete comment
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

        setDelSendToSrv(true);
    };


    return (
        <div className="border">
            <div className="row">
                <div className="col">
                    <img className="circle-image" alt="" src={baseServerUrl + profilePicture} />
                </div>
                <div className="col-11" style={{ padding: 0 }}>

                    <div className="comment">

                        <span className="usernameAndclosebutton">
                            <h2 className="username">@{username}</h2>
                            {currentUser && currentUserIsOwnerOfComment() &&
                                <button type="button" className="close-button btn btn-secondary"
                                    onClick={deleteComment}>X</button>}
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