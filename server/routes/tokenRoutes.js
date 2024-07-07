// routes/tokenRoutes.js
import express from 'express';
import { login } from '../controllers/tokenController.js';
import { checkAuth } from '../controllers/tokenController.js';
import { logout } from '../controllers/tokenController.js';


const router = express.Router();
router.post('/', login);
router.get('/checkAuth', checkAuth); // Define GET route for authentication check
router.post('/logout', logout); // Define POST route for logout

export default router;
