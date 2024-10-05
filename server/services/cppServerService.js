import net from 'net';
export function sendWatchEvent(userId, videoId) {
    console.log('Connected to C++ server');
    return new Promise((resolve, reject) => {
        const client = new net.Socket();
        client.connect(5555, '127.0.0.1', () => {
            console.log('Connected to C++ server');
            // Format the message as "WATCH userId,videoId"
            const message = `WATCH ${userId},${videoId}`;
            client.write(message);
        });

        client.on('data', (data) => {
            console.log('Received from C++ server:', data.toString());
            resolve(data.toString().split(','));  // Split the response into an array of video IDs
            client.destroy();  // Close the connection after receiving the response
        });

        client.on('error', (err) => {
            console.error('Error connecting to C++ server:', err);
            reject(err);
        });
    });
}
