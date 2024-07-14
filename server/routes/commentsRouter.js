import express from 'express';
import { getComment, getComments, addComment, deleteComment, 
    updateComment } from '../controllers/commentsController.js';

import { authenticate } from '../controllers/tokenController.js';

const commentsRouter = express.Router({ mergeParams: true });

commentsRouter.route('/:cid')
    .get(getComment)
    .delete(authenticate, deleteComment)
    .put(authenticate, updateComment);

    commentsRouter.route('/')
    .get(getComments)
    .post(authenticate, addComment)

export default commentsRouter;
