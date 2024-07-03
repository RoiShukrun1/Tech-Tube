import multer from 'multer';

// Helper function to get the file extension
const getFileExtension = (filename) => {
    return filename.substring(filename.lastIndexOf('.'));
};

// Configure storage for different file types
const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        let folder = '';
        if (file.mimetype.startsWith('image/')) {
            folder = 'uploads/profilePictures/';
        } else {
            folder = 'uploads/others/';
        }
        cb(null, folder);
    },
    filename: (req, file, cb) => {
        const fileExtension = getFileExtension(file.originalname);
        cb(null, file.fieldname + '-' + Date.now() + fileExtension);
    }
});

// Init upload
const uploadFile = multer({
    storage: storage,
    limits: { fileSize: 10 * 1024 * 1024 } // Limit set to 10MB
}).single('file'); // The field name in the form should be 'file'

export default uploadFile;
