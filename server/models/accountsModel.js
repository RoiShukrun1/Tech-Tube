import mongoose from 'mongoose'; // Use import for mongoose

// Define a schema for the user
const accountSchema = new mongoose.Schema({
    nickname: { type: String, required: true ,unique: true }, // Define the nickname field as a string and required
    username: { type: String, required: true ,unique: true }, // Define the username field as a string and required
    password: { type: String, required: true }, // Define the password field as a string and required
    image: { type: String, required: false }, // Define the image field as a string (URL) and not required
    subscriptions: { type: [String], required: false } // Define the subscriptions field as an array of strings and not required
});

// Export the User model based on the userSchema
export default mongoose.model('Account', accountSchema);
