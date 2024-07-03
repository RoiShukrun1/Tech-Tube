import express from 'express';
import { getAccount } from '../controllers/usersController.js';

const router = express.Router();

router.route('/:id')
  .get(getAccount);

export default router;
