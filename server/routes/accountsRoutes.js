import express from 'express';
import { registerAccount } from '../controllers/accountsController.js';
import uploadFile from '../multerConfig.js'; // Import uploadFile from the new module

const router = express.Router();

// Define a POST route for user registration and map it to the registerAccount function in the controller
router.post('/register', uploadFile, registerAccount);

// Export the router so it can be used in the server setup
export default router;
