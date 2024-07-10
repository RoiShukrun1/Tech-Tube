import fs from 'fs';
import path from 'path';

export const saveBase64Image = async (base64Image, filePath) => {
    const matches = base64Image.match(/^data:([A-Za-z-+\/]+);base64,(.+)$/);
    if (!matches || matches.length !== 3) {
        throw new Error('Invalid base64 image');
    }
    const imageBuffer = Buffer.from(matches[2], 'base64');
    await ensureDirectoryExistence(filePath);
    await fs.promises.writeFile(filePath, imageBuffer);
};

const ensureDirectoryExistence = async (filePath) => {
    const dirname = path.dirname(filePath);
    try {
        await fs.promises.access(dirname);
    } catch (e) {
        await ensureDirectoryExistence(dirname);
        await fs.promises.mkdir(dirname);
    }
};