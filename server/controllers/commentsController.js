import { getCommentFromDB, getCommentsFromDB, deleteCommentFromDB, patchCommentinDB } from '../services/commentsServices.js';

export const getComment = async (req, res) => {
    try {
        const videoId = req.params.pid;
        const commentId = req.params.cid;
        const comment = await getCommentFromDB(videoId, commentId);

        res.status(200).json(comment); // Send the account object as JSON response
    } catch (error) {
        console.error('Error fetching comment:', error);
        res.status(500).json({ message: 'Internal server error' }); // Send 500 error response
    }
};

export const getComments = async (req, res) => {
    try {
        const videoId = req.params.pid;
        const comment = await getCommentsFromDB(videoId);

        res.status(200).json(comment); // Send the account object as JSON response
    } catch (error) {
        console.error('Error fetching comment:', error);
        res.status(500).json({ message: 'Internal server error' }); // Send 500 error response
    }
};

export const deleteComment = async (req, res) => {
    try {
        const videoId = req.params.pid;
        const commentId = req.params.cid;
        await deleteCommentFromDB(videoId, commentId);

        res.status(200).json({ message: 'comment deleted successfully' }); // Send success message
    } catch (error) {
        console.error('Error deleting comment:', error);
        res.status(500).json({ message: 'Internal server error' }); // Send 500 error response
    }
};

export const updateComment = async (req, res) => {
    try {
        const videoId = req.params.pid;
        const commentId = req.params.cid;

        const updatedparms = req.body;

        const result = await patchCommentinDB(videoId, commentId, updatedparms);

        res.status(200).json(result);

    }
    catch (error) {
        console.error('Error update comment:', error);
        res.status(500).json({ message: 'Internal server error' }); // Send 500 error response
    }
};