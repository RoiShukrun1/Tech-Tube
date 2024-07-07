import express from 'express';
import { getComment, getComments, addComment, deleteComment, 
    updateComment } from '../controllers/commentsController.js';


const commentsRouter = express.Router({ mergeParams: true });

commentsRouter.route('/:cid')
    .get(getComment)
    .delete(deleteComment)
    .put(updateComment);

    commentsRouter.route('/')
    .get(getComments)
    .post(addComment)

export default commentsRouter;
