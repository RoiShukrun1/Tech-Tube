import { getAccountFromDB } from '../services/usersServices.js';

export const getAccount = async (req, res) => {
    try {
        const accountUsername = req.params.id;
        const account = await getAccountFromDB(accountUsername);

        if (!account) {
            return res.status(404).json({ message: 'Account not found' });
        }

        res.status(200).json(account); // Send the account object as JSON response
    } catch (error) {
        console.error('Error fetching account:', error);
        res.status(500).json({ message: 'Internal server error' }); // Send 500 error response
    }
};
