import { NativeModules } from 'react-native';
const { SnapImageModule } = NativeModules;

/**
 * TODO: Clean this up and make one function that caches the images and return both a URI array and a regular one.
 */

export const fetchImagesPath = async (uri) => {
	const images = [];

	let imgs = await SnapImageModule.getImagesFromPath(uri);

	for (let i = 0; i < imgs.length; i++) {
		images.push(imgs[i]);
	}

	return images
}

export const fetchUriImagesPath = async (uri) => {
	const images = [];

	let imgs = await SnapImageModule.getImagesFromPath(uri);

	for (let i = 0; i < imgs.length; i++) {
		images.push({ uri: imgs[i] });
	}

	return images
}