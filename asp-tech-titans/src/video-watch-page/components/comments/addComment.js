import axios from "axios";

// function to add a comment to the video
function AddComment({ comments, currentVideoId, currentUser,
    setVideos, inputValue, setInputValue, isFocused, setIsFocused }) {

    // Function to handle input change
    const handleInputChange = (event) => {
        setInputValue(event.target.value);
    };

    // Function to handle focus
    const handleFocus = () => {
        setIsFocused(true);
    };

    // Function to check if input is empty
    const inputIsEmpty = () => { return inputValue === '' ? true : false; };

    // Function to submit a comment
    const submitComment = async () => {

        const comments =
            await axios.get('http://localhost/api/users/' + currentUser.username
                + '/videos/' + currentVideoId + '/comments');


        const newComment = {
            id: comments.data[comments.data.length - 1].id + 1,
            username: currentUser.username,
            image: currentUser.image,
            date: new Date().toLocaleString(),
            comment: inputValue,
            likes: 0,
            usersLikedId: [],
            usersUnLikedId: []
        };

        const path = 'http://localhost/api/users/' + currentUser.username +
            '/videos/' + currentVideoId + '/comments';
        await axios.post(path, newComment);

        // Update the videos state
        setVideos(prevVideos => {
            const updatedVideos = [...prevVideos];

            const thisVideo = updatedVideos.find(video => video.id === currentVideoId);

            thisVideo.comments.unshift(newComment);

            return updatedVideos;

        });

        setIsFocused(false);
        setInputValue('');
    };

    return (
        <div className="row">
            <div className="col-1">
                <img className="circle-image" alt=""
                    src={currentUser.image} style={{ marginLeft: '35%' }} />
            </div>
            <div className="col">
                <input type="text"
                    className="addComment"
                    placeholder="Add a comment"
                    onFocus={handleFocus}
                    onChange={handleInputChange}
                    value={inputValue}
                />
                {isFocused && <button // If the input is focused, show the cancel button
                    onClick={() => setIsFocused(false)}
                    type="button"
                    className="btn btn-light comment-add-button">cancel
                </button>}
                {isFocused && // If the input is focused, show the comment button
                    <button type="button"
                        className="btn btn-light comment-add-button"
                        onClick={submitComment}
                        disabled={inputIsEmpty()}
                    >comment</button>}
            </div>
        </div>
    );
}

export default AddComment;