import express from 'express';
import { fileURLToPath } from 'url';
import path from 'path';

const router = express.Router();

// Get the directory name of the current module
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const routes = ['/', '/video', '/mainpage', '/login', '/registration', 
    '/uploadPage', '/addVideo', '/publisherChannel', '/search'];

// routes to serve the React app
routes.forEach(route => {
    router.get(route, (req, res) => {
        res.sendFile(path.join(__dirname, '../../asp-tech-titans/build', 'index.html'));
    });
});

export default router;
