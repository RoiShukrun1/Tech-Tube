import express from 'express';
import { getAccount } from '../controllers/usersController.js';
import { deleteAccount } from '../controllers/usersController.js';


const router = express.Router();

router.route('/:id')
  .get(getAccount)
  .delete(deleteAccount); 

export default router;
