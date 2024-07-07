import express from 'express';
import { getComment, getComments, deleteComment, updateComment } from '../controllers/commentsController.js';


const commentsRouter = express.Router({ mergeParams: true });

commentsRouter.route('/:cid')
    .get(getComment)
    .delete(deleteComment)
    .patch(updateComment);

    commentsRouter.route('/')
    .get(getComments)

export default commentsRouter;
