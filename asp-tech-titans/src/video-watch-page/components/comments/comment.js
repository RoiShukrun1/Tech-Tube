import './comment.css';
import { ReactComponent as Like } from './like.svg';
import { ReactComponent as Dislike } from './dislike.svg';
import { ReactComponent as LikeSelected } from './like-selected.svg';
import { ReactComponent as DislikeSelected } from './dislike-selected.svg';
import { useState } from 'react';

function Comment({ username, date, comment, likes, profilePicture }) {

    const [likesNumber, setLikesNumber] = useState(likes);
    const [isLiked, setIsLiked] = useState(false);
    const [isUnLiked, setUNIsLiked] = useState(false);

    return (

        <div className="container text-center border">
            <div className="row">
                <div className="col">
                    <img className="circle-image" src={profilePicture} alt="profile picture" />
                </div>
                <div className="col">

                    <div className="comment">
                        <h2 className="username">@{username}</h2>
                        <h3 className="date">{date}</h3>
                        <h3 className="content">{comment}</h3>

                        <div className='container'>
                            <button onClick={() => {
                                setIsLiked(!isLiked);
                                setUNIsLiked(false);
                                isLiked ? setLikesNumber(likesNumber - 1) : setLikesNumber(likesNumber + 1);
                            }}
                                type="button" className="btn btn-outline-secondary like-icon-button">
                                {isLiked ? <LikeSelected className='icon' /> : <Like className='icon'/>}
                            </button>

                            <h3 className="likesNumber">{likesNumber}</h3>

                            <button onClick={() => {
                                setIsLiked(false);
                                setUNIsLiked(!isUnLiked);
                                if (likesNumber == likes + 1) {
                                    setLikesNumber(likesNumber - 1);
                                }
                            }} type="button" className="btn btn-outline-secondary dislike-icon-button">
                                {isUnLiked ? <DislikeSelected className='icon' /> : <Dislike className='icon'/>}
                            </button>

                            <button type="button" className="btn btn-outline-secondary reply-button">Reply</button>
                        </div>
                    </div>

                </div>
            </div>


        </div>

    );

}

export default Comment;