import './comment.css';
import { ReactComponent as Like } from './like.svg';
import { ReactComponent as Dislike } from './dislike.svg';
import { useState } from 'react';

function Comment({ username, date, comment, likes }) {

    const [likesNumber, setLikesNumber] = useState(likes);
    const [isLiked, setIsLiked] = useState(0);

    return (
        <div className="comment">

            <h2 className="username">{username}</h2>
            <h3 className="date">{date}</h3>
            <h3 className="content">{comment}</h3>

            <div className='container'>
                <button onClick={() => {
                    if (likesNumber == likes + 1) {
                        return
                    }
                    setLikesNumber(likesNumber + 1);
                    setIsLiked(1);
                }}
                    type="button" className="btn btn-outline-secondary like-icon-button">
                    <Like className='icon' style={{ margin: '0px', backgroundColor: isLiked == 1 ? 'green' : 'transparent' }} />
                </button>

                <h3 className="likesNumber">{likesNumber}</h3>

                <button onClick={() => {
                    setIsLiked(-1);
                    if (likesNumber == likes + 1) {
                        setLikesNumber(likesNumber - 1);
                    }
                }} type="button" className="btn btn-outline-secondary dislike-icon-button">
                    <Dislike className='icon' style={{ margin: '0px', backgroundColor: isLiked == -1 ? 'red' : 'transparent' }} />
                </button>

                <button type="button" className="btn btn-outline-secondary">reply</button>
            </div>
        </div>
    );

}

export default Comment;